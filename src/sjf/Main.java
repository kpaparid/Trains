package sjf;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	static ProcessGenerator processGenerator;
	static Clock clock;
	static ReadyProcessesList readyProcList;
	static NewProcessTemporaryList newProcTempList;
	static CPU cpu;
	static SJFScheduler sjf;
	static Statistics stats;
	static boolean readfile; // false for first execution of simulation and true for secondary(read processes from file)

	public static void main(String[] args) throws FileNotFoundException,IOException, ClassNotFoundException {
		
		Scanner scanner = new Scanner(System.in);
		int x, y, choice = 0;
		
		while (choice != 3) { // simulation continues until user chooses to exit
		
			int counter = 0; // number of processes created during simulation
			String str = null;
			boolean isPreemptive = false;
			
			// ask for type of SJF or exit
			System.out.println("What would you like to do?(Give number) \n"
					+ " 1. Test Non-Preemptive SJF \n 2. Test Preemptive SJF \n 3. Exit simulation");
			do {
				choice = scanner.nextInt();
			}
			while (choice != 1 && choice != 2 && choice != 3);  
			if (choice != 3) { // if user chooses not to exit
				if (choice == 1) {
					isPreemptive = false;
				}
				else if (choice == 2) {
					isPreemptive = true;
				}

				readfile = false;
			
				// create new processes or load existing ones from file
				if (processGenerator != null && !processGenerator.fileIsEmpty()) {
					System.out.println("Load existing processes from file? [y/n]");
					do {
						str = scanner.nextLine();
					}
					while (!str.equals("y") && !str.equals("n"));
					if (str.equals("y")) {
						readfile = true;
					}
				}
		
				processGenerator = new ProcessGenerator("processes.txt", readfile);
				clock = new Clock();
				readyProcList = new ReadyProcessesList();
				newProcTempList = new NewProcessTemporaryList();
				cpu = new CPU();
				sjf = new SJFScheduler(isPreemptive);
				stats = new Statistics("Statistics.txt");
			
				if (!readfile) { // create 3 initial processes
					newProcTempList.addNewProcess(processGenerator.createProcess());
					stats.updateTotalNumberOfProcesses();
					newProcTempList.addNewProcess(processGenerator.createProcess());
					stats.updateTotalNumberOfProcesses();
					newProcTempList.addNewProcess(processGenerator.createProcess());
					stats.updateTotalNumberOfProcesses();
				} else { // read processes from file
					for (Process process : processGenerator.parseProcessFile()) {
						newProcTempList.addNewProcess(process);
						stats.updateTotalNumberOfProcesses();
					}
				}

				// simulation continues until clock = 40 ticks or Ready list and CPU are empty
				while (clock.showTime() <= 40 || !readyProcList.getList().isEmpty()|| cpu.getRunningProcess() != null) { 
				
					if (!readfile) {
						// create 10 new processes every two ticks of the clock
						if (clock.showTime() % 2 == 0 && counter < 10) {
							newProcTempList.addNewProcess(processGenerator.createProcess());
							stats.updateTotalNumberOfProcesses();
							counter++;
						}
					}
				
					// print contents of New list, Ready list and CPU for every tick of simulation
					System.out.println("TICK " + clock.showTime() + "\n");
					System.out.println("<---------New List------------------> \n");
					newProcTempList.printList();
					System.out.println("<---------Ready List------------------> \n");
					readyProcList.printList();
					if (cpu.getRunningProcess() == null) {
						x = 0;
						y = 0;
					} else {
						x = cpu.getRunningProcess().getPid();
						y = cpu.getRunningProcess().getCpuRemainingTime();
					}
					System.out.println("<-------------CPU--------------> \n");
					System.out.println("PID: " + x + " Remaining Time: " + y);
				
					// call scheduler
					sjf.SJF();
				
					// increment clock
					clock.Time_Run();
				}

				// at end of simulation write statistics to file
				stats.writeStatistics2File();
			}
			System.out.println("---------------------------------------------------------- \n");
		}
	
	}

}

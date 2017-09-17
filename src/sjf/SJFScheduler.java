package sjf;


public class SJFScheduler {

	private boolean isPreemptive;

	public SJFScheduler(boolean isPreemptive) {
		
		this.isPreemptive = isPreemptive;
		
	}

	public void addProcessToReadyList(Process process) {
		
		Main.readyProcList.addProcess(process);
		
	}
	
	public boolean getIfIsPreemptive() {
		
		return isPreemptive;
		
	}

	public void SJF() {

		// for each process in New list check arrival time and if it matches the current clock value,
		// transfer it to Ready list
		while (Main.newProcTempList.getFirstWithoutRemove() != null) {
			if (Main.newProcTempList.getFirstWithoutRemove().getArrival() == Main.clock
					.showTime()) {
				addProcessToReadyList(Main.newProcTempList.getFirst());
			} else {
				break;
			}
		}
		
		// increment maximum length of Ready list
		Main.stats.updateMaximumListLength();

		
		if (!Main.readyProcList.isEmpty()) { // if Ready list is not empty
			if (!Main.cpu.containsProcess()) { // and cpu is inactive
				Main.cpu.addProcess(Main.readyProcList.getProcessToRunInCPU(), // add appropriate process to cpu
						Main.clock.showTime());
			} 
			else if (isPreemptive) { // check if pre-emptive algorithm is running
				if (Main.cpu.getRunningProcess().getCpuRemainingTime() > Main.readyProcList.getList().
						get(0).getCpuRemainingTime()) { // if currently running process requires more burst time than others from Ready list then
					Main.readyProcList.addProcess(Main.cpu 
							.removeProcessFromCpu()); // add previously running process to Ready list and
					Main.cpu.addProcess( // transfer appropriate process from Ready list to CPU for execution
							Main.readyProcList.getProcessToRunInCPU(),
							Main.clock.showTime());
				}

			}
			
			// if first response of CPU to process now running
			if (Main.cpu.getRunningProcess().cpuFirstTime) {
				Main.stats.updateResponseTime(Main.clock.showTime() // increment total response time
						- Main.cpu.getRunningProcess().getArrival());
				Main.cpu.getRunningProcess().cpuFirstTime = false;
			}
		}

		
		// increment total waiting time for each process in Ready list
		Main.stats.updateTotalWaitingTime(Main.readyProcList.getSize()); 

		// execute process in cpu
		Main.cpu.execute();

		// if process in cpu is terminated, remove it
		if (Main.cpu.containsProcess()) {
			if (Main.cpu.getRunningProcess().getCpuRemainingTime() == 0) {
				Process temp = Main.cpu.removeProcessFromCpu();
				Main.stats.updateTotalBurstTime(temp.getCpuTotal()); // increment total burst time for process in cpu
			}
		}

	}
}

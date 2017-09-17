package sjf;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessGenerator {

	private File inputFile;
	private int counter; // counts the number of processes created by generator(pids)
	private Random random;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ProcessGenerator(String filename,boolean readFile) throws FileNotFoundException, IOException {
		
		counter = 0;
		random = new Random();
		inputFile = new File(filename);
		if (readFile==false) { // write processes in file if first execution of simulation
			out = new ObjectOutputStream(new FileOutputStream(inputFile));
		}
		else { // read processes from file is secondary execution of simulation
			in = new ObjectInputStream(new FileInputStream(inputFile));
		}
		
	}
	
	public boolean fileIsEmpty() {
		if (inputFile.lastModified() == 0) {
			return true;
		}
		else {
			return false;
		}
	}


	public Process createProcess() {
		
		counter++;
		int arrivalTime = random.nextInt(10) + Main.clock.showTime(); // random and appropriate arrival time
		int burstTime = random.nextInt(10); // random burst time(max 10)
		while (burstTime == 0) {
			burstTime = random.nextInt(10);
		}
		Process pro = new Process(counter,arrivalTime,burstTime);
		storeProcessToFile(pro); // add process to file
		return pro;
		
	}

	public void storeProcessToFile(Process process) {
		
		try {
			out.writeObject(process); // writes the process as an object in file(process implementes serializable)
		}catch (IOException e) {}
		
	}

	public List<Process> parseProcessFile() throws ClassNotFoundException, FileNotFoundException, IOException {
		
		List<Process> list = new ArrayList<>(); // list to contain processes in file
		try {
			while (true) {
				Process process = new Process(0,0,0);
				process = (Process) in.readObject(); // read process object from file
				list.add(process);
			}
		}catch (EOFException e) {
			in.close();
		}
		return list;
		
	}

}
package sjf;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class NewProcessTemporaryList { 
	
	private List<Process> processList;
	
	public NewProcessTemporaryList() {
		
		processList = new ArrayList<Process>();
		
	}
	
	public void addNewProcess(Process process) {
		
		processList.add(process);
		Collections.sort(processList, new SJFComparator()); 
		
	}
	
	// removes first process of New list and returns it
	public Process getFirst() {
		
		if (!processList.isEmpty()) {
			Process temp = processList.get(0);
			processList.remove(0);
			return temp;
		}
		else {
			return null;
		}
		
	}
	
	public void printList() {
		
		for (Process pro : processList) {
			System.out.println("PID: " + pro.getPid() + " Arrival Time: " + pro.getArrival() + " Total Time: " + pro.getCpuTotal() + "\n");
		}
		
	}
	
	// returns first process of New list(without removing)
	public Process getFirstWithoutRemove() {
		
		if (!processList.isEmpty()) {
			return processList.get(0);
		}
		else {
			return null;
		}
		
	}
	
	static class SJFComparator implements Comparator<Process> {
		
		public int compare(Process p1, Process p2) {
			int result = p1.getArrival() - p2.getArrival();
		
			if (result == 0) {
			    return (p1.getPid() < p2.getPid()) ? -1 : 1;
			}
			else {
			    return result;
			}
		}
		
	}
}

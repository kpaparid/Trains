package sjf;


import java.io.Serializable;

public class Process implements Serializable {
	
	private static final long serialVersionUID = 4934777600375656961L;
	private int pid;
	private int arrivalTime;
	private int cpuTotalTime;
	private int cpuRemainingTime;
	@SuppressWarnings("unused")
	private int currentState;
	public boolean cpuFirstTime; // indicates if process is being run by cpu for the first time
	
	
	public Process(int pid, int arrivalTime, int cpuBurstTime) {
		
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		cpuFirstTime = true;
		currentState = 0;
		cpuTotalTime = cpuBurstTime;
		cpuRemainingTime = cpuBurstTime;
		
	}
	
	public int setProcessState(int newState) {
		
		return currentState = newState;
		
	}
	
	public void changeCpuRemainingTime(int newCpuRemainingTime) {
		
		cpuRemainingTime = newCpuRemainingTime;
		
	}
	
	public int getPid() {
		
		return pid;
		
	}
	
	public int getArrival() {
		
		return arrivalTime;
		
	}
	
	public int getCpuTotal() {
		
		return cpuTotalTime;
		
	}
	
	public int getCpuRemainingTime() {
		
		return cpuRemainingTime;
		
	}
	
}

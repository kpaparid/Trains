package sjf;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {

	private float averageWaitingTime;
	private float totalWaitingTime;
	private float responseTime;
	private int maximumLengthOfReadyProcessesList;
	public int totalNumberOfProcesses;
	private float totalBurstTime;
	private BufferedWriter out;

	public Statistics(String filename) throws IOException {
		
		out = new BufferedWriter(new FileWriter(filename,true));
		
		averageWaitingTime = 0;
		totalWaitingTime = 0;
		responseTime = 0;
		maximumLengthOfReadyProcessesList = 0;
		totalNumberOfProcesses = 0;
		totalBurstTime = 0;
		
	}

	public float calculateAverageTurnaround() {
		
		return (totalBurstTime + totalWaitingTime)/totalNumberOfProcesses;
		
	}

	public void updateTotalBurstTime(float update) {
		
		totalBurstTime += update;
		
	}

	public void updateTotalNumberOfProcesses() {
		
		totalNumberOfProcesses++;
		
	}

	public void updateResponseTime(float update) {
		
		responseTime += update;
		
	}

	public void updateTotalWaitingTime(float update) {
		
		totalWaitingTime += update;
		
	}

	public void updateMaximumListLength() {
		
		if (Main.readyProcList.getList().size() > maximumLengthOfReadyProcessesList) {
			maximumLengthOfReadyProcessesList = Main.readyProcList.getList()
					.size();
		}
		
	}

	public float calculateAverageWaitingTime() {
		
		averageWaitingTime =  totalWaitingTime / totalNumberOfProcesses;
		return averageWaitingTime;
		
	}

	public void writeStatistics2File() throws IOException {
		
		if (Main.sjf.getIfIsPreemptive()){ out.write("Preemptive: "); out.newLine();}
		else { out.write("Non Preemptive: "); out.newLine(); }
		out.write("Average Waiting Time: " + calculateAverageWaitingTime()
					+ " Total Waiting Time: " + totalWaitingTime
					+ " Maximum Length of Ready Processes List: "
					+ maximumLengthOfReadyProcessesList
					+ " Average Response Time: " + responseTime
					/ totalNumberOfProcesses + " Average Turnaround Time: "
					+ calculateAverageTurnaround());
		out.newLine();
		out.close();
		
	}
}

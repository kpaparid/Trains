package sjf;


public class Clock {
	
	protected static int ticks; // clock value
	
	public Clock() {
		
		ticks = 0;	
		
	}

	// increments the clock
	public void Time_Run() {
		
		ticks++;
		
	}
	
	// returns the current clock value
	public int showTime() {
		
		return ticks;
		
	}
}

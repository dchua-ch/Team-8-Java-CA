package sg.edu.iss.team8.leaveApp.helpers;

public class LeaveBag {
	
	public LeaveBag()
	{}
	
	
	
	public LeaveBag(int duration) {
		super();
		this.duration = duration;
	}



	private int duration;

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	

}

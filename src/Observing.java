import java.lang.reflect.Method;

public class Observing implements Observer{

	String status;
    boolean should;
	
	public Observing() {
		this.status = "Observer";
	}

	
	public void update() {
	  this.should = true;
	  System.out.println("updated");
	}
	
	public boolean shouldUpdate(){
		return should;
	}


	@Override
	public void setSubject(Subject sub) {
		// TODO Auto-generated method stub
		
	}

}

package nogbeter.paths;



public class BestProject<A>{

	
	public double distSquared;
	public A t;
	
	public BestProject(){
		this.distSquared = Double.POSITIVE_INFINITY;
	}
	
	public BestProject(double distSquared, A t) {
		this.distSquared = distSquared;
		this.t = t;
	}
	
	public void update(double distSquared, A t){
		if(distSquared < this.distSquared){
			this.t = t;
		}
	}
}
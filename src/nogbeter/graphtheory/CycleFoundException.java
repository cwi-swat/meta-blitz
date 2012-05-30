package nogbeter.graphtheory;

import java.util.List;

public class CycleFoundException extends Exception{
	
	private static final long serialVersionUID = -4653460558487171780L;
	final List<Integer> cycle;
	
	CycleFoundException(List<Integer> cycle){
		this.cycle = cycle;
	}

}

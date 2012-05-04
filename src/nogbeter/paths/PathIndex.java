package nogbeter.paths;

public abstract class PathIndex {
	
	public final PathIndex next;
	
	public PathIndex(PathIndex next) {
		this.next = next;
	}


}

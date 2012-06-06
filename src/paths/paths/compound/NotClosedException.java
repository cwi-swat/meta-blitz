package nogbeter.paths.compound;

import nogbeter.paths.Path;

public class NotClosedException extends Exception{

	public final Path notClosed;

	public NotClosedException(Path notClosed) {
		this.notClosed = notClosed;
	}
	
	
	
}

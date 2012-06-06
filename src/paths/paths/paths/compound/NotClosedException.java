package paths.paths.paths.compound;

import paths.paths.paths.Path;

public class NotClosedException extends Exception{

	public final Path notClosed;

	public NotClosedException(Path notClosed) {
		this.notClosed = notClosed;
	}
	
	
	
}

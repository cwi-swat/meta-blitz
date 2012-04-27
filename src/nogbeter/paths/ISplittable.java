package nogbeter.paths;

public interface ISplittable<PI extends PathIndex> {
	
	PI prependLeft(PathIndex tail);
	PI prependRight(PathIndex tail);
}

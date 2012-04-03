package bezier.paths.awt;


public interface IAWTPath{
	boolean isLeaf();
	IAWTLeafPath getLeaf();
	IAWTNodePath getNode();
}
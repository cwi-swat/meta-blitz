package bezier.graphtheory;

public final class Tree {

	public final int root;
	public final Tree[] children;
	public Tree(int root, Tree ... children) {
		this.root = root;
		this.children = children;
	}
	
}

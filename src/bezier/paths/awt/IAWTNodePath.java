package bezier.paths.awt;

import java.util.Stack;

public interface IAWTNodePath {
	// add the children to the stack in reverse order
	void pushChildren(Stack<IAWTPath> stack);
}
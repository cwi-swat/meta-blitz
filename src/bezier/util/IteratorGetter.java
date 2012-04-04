package bezier.util;

import java.util.Iterator;

public interface IteratorGetter<A,B> {

	Iterator<B> getIterator(A a);
}

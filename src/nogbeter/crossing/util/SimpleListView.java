package nogbeter.crossing.util;

public class SimpleListView<A,B> implements ISimpleList<B>{

	final ISimpleList<A> real;
	final IMap<A,B> map;
	
	public SimpleListView(IMap<A,B> map, ISimpleList<A> real) {
		this.map = map;
		this.real = real;
	}

	@Override
	public int size() {
		return real.size();
	}

	@Override
	public B get(int i) {
		return map.apply(real.get(i));
	}
	
}

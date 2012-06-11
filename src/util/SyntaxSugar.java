package util;

import java.util.ArrayList;
import java.util.List;

public class SyntaxSugar {

	public static <L,R> Tuple<L,R> tup(L l , R r){
		return new Tuple<L, R>(l, r);
	}
	
	public static <R> Tuple<Double,R> tupdl(double l , R r){
		return new Tuple<Double, R>(l, r);
	}
	
	public static <L,R> List<Tuple<L,R>> tupleList(Tuple<L,R>...tuples){
		List<Tuple<L,R>> res = new ArrayList<Tuple<L,R>>();
		for(int i = 0 ; i < tuples.length ; i++){
			res.add(new Tuple<L,R>(tuples[i].l,tuples[i].r));
		}
		return res;
	}
	
	
}

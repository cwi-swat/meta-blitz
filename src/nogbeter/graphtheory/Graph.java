package nogbeter.graphtheory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bezier.util.Tuple;

public final class Graph {
	
	public final boolean[][] edge;
	
	public Graph(boolean[][] edge){
		this.edge = edge;
	}
	
	// given a graph which consist of a number of
	// disjoint cycles, return the cycles
	public static <A> List<List<A>> findCycles(Map<A,A> graph){
		List<List<A>> result = new ArrayList<List<A>>();
		
		while(!graph.isEmpty()){
			A start = graph.keySet().iterator().next();
			graph.remove(start);
			List<A> currentCycle = new ArrayList<A>();
			currentCycle.add(start);
			A next = graph.get(start);
			while(next != start){
				currentCycle.add(next);
				graph.remove(next);
				next = graph.get(next);
			}
			graph.remove(next);
			result.add(currentCycle);
		}
		return result;
		
	}
	
	public boolean isRoot(int i){
		for(int j = 0; j < edge[i].length ; j++){
			if(edge[j][i]){
				return false;
			}
		}
		return true;
	}
	
	public Set<Integer> getChildren(int root){
		return getChildren(root, new HashSet<Integer>());
	}
	
	public Set<Integer> getChildren(int root, Set<Integer> alreadySeen){
		for(int i = 0 ; i < edge.length; i++){
			if(!alreadySeen.contains(i) && edge[root][i]){
				alreadySeen.add(i);
				getChildren(i,alreadySeen);
			}
		}
		return alreadySeen;
	}
	
	public List<Tuple<Integer, Set<Integer>>> getSubGraphs(){
		List<Tuple<Integer, Set<Integer>>> result = new ArrayList<Tuple<Integer,Set<Integer>>>();
		for(int i = 0 ; i < edge.length ; i++){
				result.add(new Tuple<Integer,Set<Integer>>(i,getChildren(i)));
		}
		return result;
	}
	
	public Graph getSubGraph(Set<Integer> keepNodes){
		List<Integer> nodesSorted = new ArrayList<Integer>(keepNodes);
		Collections.sort(nodesSorted);
		boolean[][] result = new boolean[nodesSorted.size()][nodesSorted.size()];
		for(int i = 0 ; i < nodesSorted.size() ; i++){
			for(int j = 0 ; j < nodesSorted.size() ; j++){
				result[i][j] = edge[nodesSorted.get(i)][nodesSorted.get(j)];
			}
		}
		return new Graph(result);
	}
	
	public Tree getTree(int root, List<Integer> prev) throws CycleFoundException{
		List<Tree> subtrees = new ArrayList<Tree>();
		if(prev.contains(root)){
			List<Integer> cycle = prev.subList(prev.indexOf(root), prev.size());
			throw new CycleFoundException(cycle);
		}
		for(int i = 0; i < edge.length ; i++){
			if(edge[root][i]){
				List<Integer> newPrev = new LinkedList<Integer>(prev);
				newPrev.add(root);
				subtrees.add(getTree(i,newPrev));
			}
		}
		return new Tree(root,subtrees.toArray(new Tree[]{}));
	}
	
	public List<Tree> getForrest() throws CycleFoundException{
		List<Tree> forrest = new ArrayList<Tree>();
		for(int i = 0; i < edge.length ; i++){
			if(isRoot(i)){
				forrest.add(getTree(i,Collections.EMPTY_LIST));
			}
		}
		return forrest;
	}

}

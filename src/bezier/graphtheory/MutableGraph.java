package bezier.graphtheory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bezier.util.STuple;

public class MutableGraph<A> {
	
	Set<A> nodes;
	Set<STuple<A>> edges;
	
	public MutableGraph() {
		edges = new HashSet<STuple<A>>();
		nodes = new HashSet<A>();
	}
	
	public boolean hasCycles(){
		MutableGraph<A> tmp = new MutableGraph<A>();
		tmp.nodes = nodes;
		tmp.edges = new HashSet<STuple<A>>(edges);
		tmp.transitiveClosureInPlace();
		for(A a : nodes){
			if(edges.contains(new STuple<A>(a, a))){
				return true;
			}
		} 
		return false;
	}
	
	public boolean edge(A from, A to){
		return edges.contains(new STuple<A>(from, to));
	}
	
	public boolean contains(A n){
		return nodes.add(n);
	}
	
	public void addNode(A n){
		nodes.add(n);
	}
	

	public void addEdge(STuple<A> edge) {
		addNode(edge.l);
		addNode(edge.r);
		edges.add(edge);
	}
	
	public void addEdge(A from, A to){
		addEdge(new STuple<A>(from, to));
	}
	
	public void removeEdge(A from, A to){
		edges.remove(new STuple<A>(from, to));
	}
	
	public void removeNode(A n){
		nodes.remove(n);
		Set<STuple<A>> toRemove = new HashSet<STuple<A>>();
		for(STuple<A> a : edges){
			if(a.l.equals(n) || a.r.equals(n)){
				toRemove.add(a);
			}
		}
		edges.removeAll(toRemove);
	}
	
	public void merge(MutableGraph<A> other){
		nodes.addAll(other.nodes);
		edges.addAll(other.edges);
	}
	
	public Set<A> in(A node){
		Set<A> result = new HashSet<A>();
		for(A v : nodes){
			if(edges.contains(new STuple<A>(v,node))){
				result.add(v);
			}
		}
		return result;
	}
	
	public Set<A> out(A node){
		Set<A> result = new HashSet<A>();
		for(A v : nodes){
			if(edges.contains(new STuple<A>(node,v))){
				result.add(v);
			}
		}
		return result;
	}
	
	public MutableGraph<A> subGraph(Set<A> elements){
		MutableGraph<A> result = new MutableGraph<A>();
		for(A a : elements){
			for(A b : elements){
				STuple<A> edge = new STuple<A>(a, b);
				if(edges.contains(edge)){
					result.addEdge(a,b);
				}
			}
		}
		return result;
	}
	
	public MutableGraph<A> subGraphWithout(Set<A> remElements){
		Set<A> elements = new HashSet<A>(nodes);
		elements.removeAll(remElements);
		return subGraph(elements);
	}
	

	public MutableGraph<A> subGraphWithout(A remElements){
		Set<A> elements = new HashSet<A>(nodes);
		elements.remove(remElements);
		return subGraph(elements);
	}
	
	
	public A getRoot(){
		for(A a : nodes){
			if(in(a).isEmpty()){
				return a;
			}
		}
		return null;
	}

	public void transitiveClosureInPlace(){
		for(A k : nodes){
			for(A i : nodes){
				for(A j : nodes){
					if(edge(i,k) && edge(j,k)){
						addEdge(i, j);
					}
				}
			}
		}
	}
	
	public Set<MutableGraph<A>> subGraphs(){
		Set<MutableGraph<A>> result = new HashSet<MutableGraph<A>>();
		Map<A,MutableGraph<A>> seenNodes = new HashMap<A,MutableGraph<A>>();
		for(STuple<A> edge : edges){
			MutableGraph<A> graph;
			if(seenNodes.containsKey(edge.l) && seenNodes.containsKey(edge.r)){
				graph = seenNodes.get(edge.l);
				MutableGraph<A> graphr = seenNodes.get(edge.r);
				if(graph != graphr){
					graph.merge(graphr);
					result.remove(graphr);
				}
				graph.addEdge(edge);
			} else if(seenNodes.containsKey(edge.l)){
				graph = seenNodes.get(edge.l);
				graph.addEdge(edge);
			} else if(seenNodes.containsKey(edge.r)){
				graph = seenNodes.get(edge.r);
				graph.addEdge(edge);
			} else {
				graph = new MutableGraph<A>();
				graph.addEdge(edge);
				result.add(graph);
			}
			seenNodes.put(edge.l, graph);
			seenNodes.put(edge.r, graph);
		}
		Set<A> allNodes = new HashSet<A>(nodes);
		allNodes.removeAll(seenNodes.keySet());
		for(A n : allNodes){
			MutableGraph<A> newGraph = new MutableGraph<A>();
			newGraph.addNode(n);
			result.add(newGraph);
		}
		return result;
	}

	public Object nrEdges() {
		return edges.size();
	}

	

}

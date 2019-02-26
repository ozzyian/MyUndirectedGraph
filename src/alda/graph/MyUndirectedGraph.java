package alda.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	
	HashMap<T, List<GraphEdge<T>>> nodes = new HashMap<T, List<GraphEdge<T>>>();
	
	class GraphEdge<T>{
		int weight;
		T arrivalNode;
		public GraphEdge(int weight, T node){
			if(weight<0) {
				throw new IllegalArgumentException("Weight must not be below 0");
			}
			this.weight = weight;
			arrivalNode = node;
		}
		public boolean equals(GraphEdge<T> other) {
			if(this.equals(other.arrivalNode)) {
				return true;
			}
			return false;
			
		}
	}

	@Override
	public int getNumberOfNodes() {
		
		return nodes.size();
	}

	@Override
	public int getNumberOfEdges() {
		int totalEdges = 0;
		for(T node: nodes.keySet()) {
			totalEdges+= nodes.get(node).size();
		}
		return totalEdges/2;
	}

	@Override
	public boolean add(T newNode) {
		if(nodes.containsKey(newNode))
			return false;
		else {
			nodes.put(newNode, new ArrayList<GraphEdge<T>>());
			return true;
		}
			
			
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		List<GraphEdge<T>> fromEdges = nodes.get(node1);
		List<GraphEdge<T>> toEdges = nodes.get(node2);
		
		GraphEdge<T> from = new GraphEdge<T>(cost, node2);
		GraphEdge<T> to = new GraphEdge<T>(cost, node1);
		
		int index = fromEdges.indexOf(from);
		
		if(index>0) {
			GraphEdge<T> existingEdge = fromEdges.get(index);
			if(from.weight != existingEdge.weight) {
				
				int indexTo = toEdges.indexOf(to);
				toEdges.get(indexTo).weight = cost;
				existingEdge.weight = cost;
				return true;
			}
			return false;
		}else {
			fromEdges.add(from);
			toEdges.add(to);
			return true;
		}
		
		
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		
		List<GraphEdge<T>> edges = nodes.get(node1);
		for (GraphEdge<T> e : edges)
			if(e.arrivalNode.equals(node2))
				return true;
		
		return false;
	}


	@Override
	public int getCost(T node1, T node2) {
		if(!this.isConnected(node1, node2)) {
			return -1;
		}
		List<GraphEdge<T>> fromEdges = nodes.get(node1);
		for(GraphEdge<T> e : fromEdges) 
			if(e.arrivalNode.equals(node2)) {
				return e.weight;
			}
		return -1;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}

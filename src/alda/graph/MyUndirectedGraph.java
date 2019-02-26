package alda.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	
	HashMap<T, LinkedList<GraphEdge<T>>> nodes = new HashMap<T, LinkedList<GraphEdge<T>>>();
	
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
			nodes.put(newNode, null);
			return true;
		}
			
			
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		
		GraphEdge<T> from = new GraphEdge<T>(cost, node2);
		GraphEdge<T> to = new GraphEdge<T>(cost, node1);
		
		
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		// TODO Auto-generated method stub
		return 0;
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

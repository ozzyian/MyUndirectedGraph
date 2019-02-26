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
			nodes.put(newNode, new ArrayList<GraphEdge<T>>());
			return true;
		}
			
			
	}
	private GraphEdge<T> getExistingEdge(T from, T to){
		List<GraphEdge<T>> fromEdges = nodes.get(from);
		for(GraphEdge<T> e : fromEdges) {
			if(e.arrivalNode.equals(to)) {
				return e;
			}
		}
		return null;
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(cost<=0 || !nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		
		GraphEdge<T> from = new GraphEdge<T>(cost, node2);
		GraphEdge<T> to = new GraphEdge<T>(cost, node1);
		
		GraphEdge<T> existingEdge = getExistingEdge(node1, node2);
		if(existingEdge!=null) {
			existingEdge.weight = cost;
			existingEdge = getExistingEdge(node2, node1);
			existingEdge.weight = cost;
		}else {
			nodes.get(node1).add(from);
			nodes.get(node2).add(to);
		}
		
		return true;
		
		
		
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		GraphEdge<T> existingEdge = getExistingEdge(node1, node2);
		if(existingEdge!=null) {
			return true;
		}
		return false;
	}


	@Override
	public int getCost(T node1, T node2) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return -1;
		
		GraphEdge<T> existingEdge = getExistingEdge(node1, node2);
		if(existingEdge!=null) {
			return existingEdge.weight;
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

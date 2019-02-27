package alda.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	
	HashMap<T, GraphNode<T>> nodes = new HashMap<T, GraphNode<T>>();
	
	class GraphNode<T>{
		boolean visited;
		T value;
		List<GraphEdge<T>> adj;
		public GraphNode(T value) {
			this.value=value;
			adj = new ArrayList<GraphEdge<T>>();
		}
		public GraphEdge<T> getExistingEdge(T from, T to){
			for(GraphEdge<T> e : adj) {
				if(e.arrivalNode.equals(to)) {
					return e;
				}
			}
			return null;
		}
		public int numberOfEdges() {
			return adj.size();
		}
		
		public void addEdge(GraphEdge<T> e) {
			adj.add(e);
		}
	}
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
		for(GraphNode<T> node: nodes.values()) {
			totalEdges+= node.numberOfEdges();
		}
		return totalEdges/2;
	}

	@Override
	public boolean add(T newNode) {
		if(nodes.containsKey(newNode))
			return false;
		else {
			nodes.put(newNode, new GraphNode<T>(newNode));
			return true;
		}
			
			
	}


	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(cost<=0 || !nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		
		GraphEdge<T> from = new GraphEdge<T>(cost, node2);
		GraphEdge<T> to = new GraphEdge<T>(cost, node1);
		
		
		
		GraphEdge<T> existingEdge2 = nodes.get(node1).getExistingEdge(node1, node2);
		//GraphEdge<T> existingEdge = getExistingEdge(node1, node2);
		if(existingEdge2!=null) {
			existingEdge2.weight = cost;
			existingEdge2 = nodes.get(node2).getExistingEdge(node2, node1);
			//existingEdge = getExistingEdge(node2, node1);
			existingEdge2.weight = cost;
		}else {
			nodes.get(node1).addEdge(from);
			nodes.get(node2).addEdge(to);
		}
		
		return true;
		
		
		
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return false;
		GraphEdge<T> existingEdge = nodes.get(node1).getExistingEdge(node1, node2);
		if(existingEdge!=null) {
			return true;
		}
		return false;
	}


	@Override
	public int getCost(T node1, T node2) {
		if(!nodes.containsKey(node1) || !nodes.containsKey(node2))
			return -1;
		
		GraphEdge<T> existingEdge = nodes.get(node1).getExistingEdge(node1, node2);
		if(existingEdge!=null) {
			return existingEdge.weight;
		}
		return -1;

	}
	
	

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		LinkedList<T> nodesToTravserse = new LinkedList<T>();
		
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

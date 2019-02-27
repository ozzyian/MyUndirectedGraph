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
		
		public boolean isVisited() {
			return visited;
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
		GraphNode<T> n1 = nodes.get(node1);
		GraphNode<T> n2 = nodes.get(node2);
		
		GraphEdge<T> existingEdge = n1.getExistingEdge(node1, node2);
		if(existingEdge!=null) {
			existingEdge.weight = cost;
			existingEdge = n2.getExistingEdge(node2, node1);
			existingEdge.weight = cost;
		}else {
			GraphEdge<T> g1 = new GraphEdge<T>(cost, node2);
			GraphEdge<T> g2 = new GraphEdge<T>(cost, node1);
			n1.addEdge(g1);
			n2.addEdge(g2);
		}
		
		return true;
		
		
		
	}
	private void unVisit() {
		for(GraphNode<T> n : nodes.values()) {
			if(n.isVisited()) {
				n.visited=false;
			}
		}
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
		unVisit();
		
		LinkedList<T> stack = new LinkedList<T>();
		LinkedList<T> traversed = new LinkedList<T>();
		if(start.equals(end)) {
			traversed.add(start);
			return traversed;
		}
		
		stack.push(start);
		nodes.get(start).visited = true;
		
		while(!stack.isEmpty()) {
			GraphNode<T> current = nodes.get(stack.pop());
			System.out.print(current.value);
			traversed.add(current.value);
			
			for(GraphEdge<T> edge : current.adj) {
				GraphNode<T> g = nodes.get(edge.arrivalNode);
				
				if( !g.isVisited()) {
					g.visited = true;
					stack.push(g.value);
				}
				
				if (g.value.equals(end)) {
					traversed.add(g.value);
					System.out.println(traversed);
					return traversed;
				}
			}
		}
		
		
		
		System.out.println(stack);
		return stack;
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

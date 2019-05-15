//Rebecka Skareng, resk5800
//Ossian Gustafsson, osgu7991

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	
	private HashMap<T, GraphNode<T>> nodes = new HashMap<T, GraphNode<T>>();
	private int totalEdges;
	private GraphNode<T> startNode;
	
	public MyUndirectedGraph() {
		totalEdges = 0;
	}
	
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
				if(e.endNode.equals(to)) {
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
	class GraphEdge<T> implements Comparable<GraphEdge<T>>{
		int weight;
		T startNode;
		T endNode;
		public GraphEdge(int weight, T startNode, T endNode){
			this.weight = weight;
			this.startNode = startNode;
			this.endNode = endNode;
		}

		public int compareTo(GraphEdge<T> other) {
			return weight - other.getWeight();
		}
		
		public int getWeight() {
			return weight;
		}


	}

	@Override
	public int getNumberOfNodes() {
		
		return nodes.size();
	}

	@Override
	public int getNumberOfEdges() {
		return totalEdges;
	}

	@Override
	public boolean add(T newNode) {
		if(nodes.containsKey(newNode))
			return false;
		else {
			GraphNode<T> node = new GraphNode<T>(newNode);
			if(startNode == null)
				startNode = node;
			nodes.put(newNode, node);
			
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
			GraphEdge<T> g1 = new GraphEdge<T>(cost, node1,node2);
			GraphEdge<T> g2 = new GraphEdge<T>(cost, node2, node1);
			n1.addEdge(g1);
			n2.addEdge(g2);
			
		}
		totalEdges++;
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
	
	private List<T> getPath(T start, T end, HashMap<T, T> parents){
		LinkedList<T> path = new LinkedList<>();
		LinkedList<T> stack = new LinkedList<>();
		stack.push(end);
		T current = end;
		while(current!=start) {
			current = parents.get(current);
			stack.push(current);
		}
		while (!stack.isEmpty()) {
			current = stack.pop();
			path.add(current);
		}
		
		return path;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		unVisit();
		

		LinkedList<T> stack = new LinkedList<T>();
		HashMap<T, T> parents = new HashMap<>();
		if(start.equals(end)) {
			return new LinkedList<T>(Arrays.asList(start));
		}
		
		stack.push(start);
		nodes.get(start).visited = true;
		
		while(!stack.isEmpty()) {
			GraphNode<T> current = nodes.get(stack.pop());
			if (!current.value.equals(end)) {
				for(GraphEdge<T> edge : current.adj) {
					GraphNode<T> edgeNode = nodes.get(edge.endNode);
					if(!edgeNode.isVisited()){
						edgeNode.visited=true;
						stack.push(edgeNode.value);
						parents.put(edgeNode.value, current.value);
					}
					
				}
			}else {
				return getPath(start, current.value, parents);
			}
		}
		
		
		return stack;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		unVisit();
		HashMap<T, T> parents = new HashMap<>();
		LinkedList<T> queue = new LinkedList<T>();
		
		queue.add(start);
		nodes.get(start).visited=true;
		
		while(!queue.isEmpty()) {
			GraphNode<T> current = nodes.get(queue.poll());
			if(!current.value.equals(end)) {
				for(GraphEdge<T> edge : current.adj) {
					GraphNode<T> edgeNode = nodes.get(edge.endNode);
					if(!edgeNode.isVisited()) {
						edgeNode.visited = true;
						queue.add(edgeNode.value);
						parents.put(edgeNode.value, current.value);
					}
			
				}
			}else {
				return getPath(start, current.value, parents);
			}
			
			
		}
		
			
		return queue;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		unVisit();
		
		UndirectedGraph<T> mst = new MyUndirectedGraph<>();
		PriorityQueue<GraphEdge<T>> edges = new PriorityQueue<>();
		
		for(GraphEdge<T> e : startNode.adj) {
			edges.add(e);
			
		}
		
		startNode.visited = true;
		
		while(!edges.isEmpty()) {
			GraphEdge<T> e = edges.poll();
			
			if(!nodes.get(e.endNode).isVisited()) {
				mst.add(e.startNode);
				mst.add(e.endNode);
				mst.connect(e.startNode, e.endNode, e.getWeight());
				
			} 
			
			GraphNode<T> n = nodes.get(e.endNode);
			
			for(GraphEdge<T> nextE : n.adj) {
				if(!n.isVisited())
					edges.add(nextE);
			}
			n.visited = true;
		
		}
		return mst;
	}

}

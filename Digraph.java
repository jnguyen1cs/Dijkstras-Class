import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;


public class Digraph<T extends Comparable<T>> {
	public ArrayList<Vertex> vertices;
	public ArrayList<Edge> edges;

	public Digraph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}

	public boolean add(T from, T to, int cost) {
		Edge temp = findEdge(from, to);
		if (temp != null) {
			if (cost == 0) {
				temp.from = null;
				//temp.cost = (Integer) null;
				return true;
			}
			System.out.println("Edge " + from + "," + to + " already exists.");
			return false;
		}
		else {
			Edge e = new Edge(from, to, cost);
			edges.add(e);
			return true;
		}
	}
	public boolean remove(T from, T to) {
		boolean re = true;
		if (findEdge(from, to) == null) {
			System.out.println("The road from " + from + " to " + to + " doesn't exist");
			return false;
		}
	    else {
			re = add(from, to, 0);
			return re;
		}
	}
	public Vertex findVertex(T v) {
		for (Vertex each : vertices) {
			if (each.val.compareTo(v)==0)
				return each;
		}
		return null;
	}

	public Edge findEdge(Vertex v1, Vertex v2) {
		if (v1 == null)
			return null;
		for (Edge each : edges)	{
			if (each.from.equals(v1) && each.to.equals(v2))	{
				return each;
			}
		}
		return null;
	}

	public Edge findEdge(T from, T to) {
		if (from == null)
			return null;
		for (Edge each : edges)	{
			if (each.from.val.equals(from) && each.to.val.equals(to)) {
				return each;
			}
		}
		return null;
	}


	public boolean Dijkstra(T v1) {
		if (vertices.isEmpty()) return false;
		resetDists();
		Vertex s = findVertex(v1);
		if (s==null) 
			return false;
		s.minDist = 0;
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		pq.add(s);

		while (!pq.isEmpty()) {
			Vertex u = pq.poll();
			for (Vertex v : u.out)	{
				Edge e = findEdge(u, v);
				if (e==null) 
					return false;
				int totalDist = u.minDist + e.cost;
				if (totalDist < v.minDist) {
					pq.remove(v);
					v.minDist = totalDist;
					v.previous = u;
					pq.add(v);
				}
			}
		}
		return true;
	}
	
	public List<String> getShortestPath(Vertex target) {
		List<String> path = new ArrayList<String>();
		for (Vertex v = target; v !=null; v = v.previous) {
			path.add(v.val + " with total cost(" + v.minDist +")");
		}

		Collections.reverse(path);
		return path;
	}

	public void resetDists() {
		for (Vertex each : vertices) {
			each.minDist = Integer.MAX_VALUE;
			each.previous = null;
		}
	}

	public List<String> getPath(T from, T to){
		boolean test = Dijkstra(from);
		if (test==false) return null;
		List<String> path = getShortestPath(findVertex(to));
		return path;
	}
	class Vertex implements Comparable<Vertex> {
		T val;
		Vertex previous = null;
		int minDist = Integer.MAX_VALUE;
		List<Vertex> inc;
		List<Vertex> out;
		
		public Vertex(T val) {
			this.val = val;
			inc = new ArrayList<>();
			out = new ArrayList<>();
		}

		public int compareTo(Vertex other)	{
			return Integer.compare(minDist, other.minDist);
		}

		public void addIncoming(Vertex vert) {
			inc.add(vert);
		}
		public void addOutgoing(Vertex vert) {
			out.add(vert);
		}
	
	}
	class Edge {
		Vertex from;
		Vertex to;
		int cost;

		public Edge(T v1, T v2, int cost) {
			from = findVertex(v1);
			if (from == null) {
				from = new Vertex(v1);
				vertices.add(from);
			}
			to = findVertex(v2);
			if (to == null) {
				to = new Vertex(v2);
				vertices.add(to);
			}
			this.cost = cost;

			from.addOutgoing(to);
			to.addIncoming(from);
		}
	}

}
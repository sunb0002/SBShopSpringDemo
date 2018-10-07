/**
 * 
 * - Dijkstra Java implementation for Dijkstra's single source shortest path algorithm.<br>
 * - Dijkstra is a Greedy algorithm, but it guarantees global minimum, proved in Intro to Algo Chap 24.3. <br>
 * - I improved the code based on https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/ <br>
 * - The graph input is adjacency matrix representation of the graph.<br>
 * 
 */
package com.madoka.sunb0002.webapi;

/**
 * 
 * @author sunbo
 *
 */
public class Dijkstra {

	// Number of Vertices
	private static final int V = 9;

	// Visited source vertices, i.e the "S" group.
	private boolean[] visitedVertices = new boolean[V];

	// Result distances and their parents
	private int[] minDistances = new int[V];
	private int[] minParents = new int[V];

	public Dijkstra() {
		log("Initialzing helper variables...");
		for (int i = 0; i < V; i++) {
			visitedVertices[i] = false;
			minDistances[i] = Integer.MAX_VALUE;
			minParents[i] = -1;
		}
	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		// The Adjacency Matrix Representation of the graph
		// 1st row: Dis(0,0)=0, Dis(0,1)=4, Dis(0,7)=8
		int graph[][] = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
				{ 0, 8, 0, 7, 0, 4, 0, 0, 2 }, { 0, 0, 7, 0, 9, 14, 0, 0, 0 }, { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
				{ 0, 0, 4, 14, 10, 0, 2, 0, 0 }, { 0, 0, 0, 0, 0, 2, 0, 1, 6 }, { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
				{ 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
		Dijkstra algo = new Dijkstra();
		algo.process(graph, 0);
		algo.printSolution();

	}

	public void printSolution() {
		log("Vertex \t Distance from Source \t Parent");
		for (int i = 0; i < V; i++) {
			log(i + "\t\t" + minDistances[i] + "\t\t" + minParents[i]);
		}
	}

	public void log(String msg) {
		System.out.println(msg);
	}

	/**
	 * Dijkstra loop
	 */
	private void process(int graph[][], int src) {

		// Distance of source vertex from itself is always 0
		minDistances[src] = 0;
		minParents[src] = src;

		// Find shortest path for all vertices by repeating relaxation for V-1 times
		for (int count = 0; count < V - 1; count++) {
			int u = getNextNearestVertex();
			relaxiation(u, graph);
		}

	}

	/**
	 * 
	 * @param vertex
	 * @param graph
	 */
	private void relaxiation(int vertex, int[][] graph) {

		// Mark the picked vertex as processed
		visitedVertices[vertex] = true;

		// Update distance value of all the other vertices to the picked vertex, if
		// (1) The other vertex (v) has not be visited. (Greedy: no regret update)
		// (2) There is an edge from picked vertex to v. (weight not 0)
		// (3) Update v's distance and parent if better path is found via this picked
		// vertex
		for (int v = 0; v < V; v++) {
			int weight = graph[vertex][v];
			if (!visitedVertices[v] && weight != 0 && minDistances[vertex] + weight < minDistances[v]) {
				minDistances[v] = minDistances[vertex] + weight;
				minParents[v] = vertex;
			}
		}

	}

	/**
	 * - Find the vertex with minimum distance value, from the set of vertices not
	 * yet included in shortest path tree. Basically, the next lowest hanging fruit
	 * to be processed. <br>
	 * - Obviously it always returns src in first iteration, because the distance
	 * from src to src is 0.<br>
	 * 
	 */
	private int getNextNearestVertex() {
		int min = Integer.MAX_VALUE, minIndex = -1;
		for (int v = 0; v < V; v++)
			if (visitedVertices[v] == false && minDistances[v] <= min) {
				min = minDistances[v];
				minIndex = v;
			}
		return minIndex;
	}

}

/**
 * 
 * - Bellman-Ford Java implementation for single source shortest path algorithm.<br>
 * - I improved the code based on https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/ <br>
 * 
 */
package com.madoka.sunb0002.webapi;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @author sunbo
 *
 */
public class BellmanFord {

	// Number of Vertices and Edges
	private int countV;
	private int countE;
	public Edge[] edges;

	// Result distances and their parents
	private int[] minDistances = new int[countV];
	private int[] minParents = new int[countV];

	// A class to represent a weighted edge in graph
	public class Edge {
		int src, dest, weight;

		public Edge() {
			src = dest = weight = 0;
		}

		@Override
		public String toString() {
			return "Edge [src=" + src + ", dest=" + dest + ", weight=" + weight + "]";
		}

	};

	public BellmanFord(int countV, int countE) {
		this.countV = countV;
		this.countE = countE;

		// Step1: Initailzation
		edges = Stream.generate(Edge::new).limit(countE).toArray(Edge[]::new);
		minDistances = IntStream.rangeClosed(1, countV).map(x -> Integer.MAX_VALUE).toArray();
		minParents = IntStream.rangeClosed(1, countV).map(x -> -1).toArray();
	}

	/**
	 * 
	 * @param src
	 * @throws Exception
	 */
	private void process(int src) throws Exception {

		// Distance of source vertex from itself is always 0
		minDistances[src] = 0;
		minParents[src] = src;

		// Step2: Find shortest path by relaxing all edges by V - 1 times.
		// Because A simple shortest path has at most V-1 edges.
		for (int i = 0; i < countV - 1; i++) {
			relaxiation(false);
		}

		// Step3: Check for negative-weight cycles. Because Step2 guarantees shortest
		// path already. If we still get shorter distance by one more relaxation, that
		// means there is negative weight cycle.
		relaxiation(true);

	}

	/**
	 * @throws Exception
	 * 
	 */
	private void relaxiation(boolean checkNegative) throws Exception {

		for (int i = 0; i < countE; i++) {
			Edge e = edges[i];
			int fromVertex = e.src;
			int toVertex = e.dest;

			if (minDistances[fromVertex] == Integer.MAX_VALUE) {
				return;
			}

			if (minDistances[fromVertex] + e.weight < minDistances[toVertex]) {

				if (checkNegative) {
					log("Graph contains negative weight cycle!");
					throw new Exception();
				}

				minDistances[toVertex] = minDistances[fromVertex] + e.weight;
				minParents[toVertex] = fromVertex;
			}

		}

	}

	/**
	 * Main method.
	 */
	public static void main(String[] args) {

		BellmanFord algo = new BellmanFord(5, 8);

		// add edge 0-1 (or A-B in above figure)
		algo.edges[0].src = 0;
		algo.edges[0].dest = 1;
		algo.edges[0].weight = -1;

		// add edge 0-2 (or A-C in above figure)
		algo.edges[1].src = 0;
		algo.edges[1].dest = 2;
		algo.edges[1].weight = 4;

		// add edge 1-2 (or B-C in above figure)
		algo.edges[2].src = 1;
		algo.edges[2].dest = 2;
		algo.edges[2].weight = 3;

		// add edge 1-3 (or B-D in above figure)
		algo.edges[3].src = 1;
		algo.edges[3].dest = 3;
		algo.edges[3].weight = 2;

		// add edge 1-4 (or A-E in above figure)
		algo.edges[4].src = 1;
		algo.edges[4].dest = 4;
		algo.edges[4].weight = 2;

		// add edge 3-2 (or D-C in above figure)
		algo.edges[5].src = 3;
		algo.edges[5].dest = 2;
		algo.edges[5].weight = 5;

		// add edge 3-1 (or D-B in above figure)
		algo.edges[6].src = 3;
		algo.edges[6].dest = 1;
		algo.edges[6].weight = 1;

		// add edge 4-3 (or E-D in above figure)
		algo.edges[7].src = 4;
		algo.edges[7].dest = 3;
		algo.edges[7].weight = -3;

		try {
			algo.process(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		algo.printResult();

	}

	public void log(String msg) {
		System.out.println(msg);
	}

	public void printResult() {
		log("Vertex \t Distance from Source \t Parent");
		for (int i = 0; i < countV; ++i)
			log(i + "\t\t" + minDistances[i] + "\t\t" + minParents[i]);
	}

}

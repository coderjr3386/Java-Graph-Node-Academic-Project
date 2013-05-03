public class BasicGraphDriver {

	public static void main(String[] args) {
		
		BasicGraph test = new BasicGraph();
		
		test.addNode("a");
		
		test.addNode("b");
		
		test.addNode("c");
		
		test.addNode("d");
		
		test.addNode("e");
		
		test.addNode("f");
		
		test.addNode("z");
		
		
		
		test.addEdge("a", "b");
		
		test.addEdge("a", "c");
		
		test.addEdge("b", "e");
		
		test.addEdge("b", "d");
		
		test.addEdge("e", "f");
		
		test.addEdge("f", "a");
		
		
		
		test.addEdge("z", "b");
		
		System.out.println(test.cyclicPath("z"));

	}

}

import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        studentExample();
    }

    public static void toFile(MazeGenerator mg, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName+".txt");
            myWriter.write(mg.toString());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(fileName+".md");
            myWriter.write(mg.toMarkDown());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void studentExample() {
        Maze m = new Maze("studentMaze.txt.txt");
        System.out.println(m.latexCode());
        m.stage1Reducing();
        System.out.println("After stage1");
        System.out.println(m.latexCode());
        m.stage2Reducing();
        System.out.println("After stage2");
        System.out.println(m.latexCode());
        m.stage3Reducing();
        System.out.println("After stage3");
        System.out.println(m.latexCode());
        System.out.println(m.isReachAble(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.isReachAble(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        Vertex[] path = m.isReachAblePath(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1')));
        for(Vertex v: path){
            System.out.println(v);
        }
        System.out.println(m.isReachAbleThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.isReachAbleThroughDoorPath(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        path = m.isReachAbleThroughDoorPath(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T')));
        for(Vertex v: path){
            System.out.println(v);
        }
        System.out.println(m.shortestPathDistanceNoDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.shortestPathDistanceNoDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        System.out.println(m.shortestPathThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(4,1, 'T'))));
        System.out.println(m.shortestPathThroughDoor(m.getVertex(new Vertex(8,9, 'S')), m.getVertex(new Vertex(2,6, '1'))));
        System.out.println(m.getRatio(m.getVertex(new Vertex(2,6, '1'))));

        Vertex v1 = new Vertex(0, 0, 'S'); // Start vertex
        Vertex v2 = new Vertex(0, 1, ' '); // Empty space
        Vertex v3 = new Vertex(1, 0, 'K'); // Key
        Vertex v4 = new Vertex(1, 1, 'D'); // Door
        Vertex v5 = new Vertex(2, 0, 'G'); // Goal

        
        VertexList vertexList = new VertexList();
        vertexList.add(v1);
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        vertexList.add(v5);

        
        Edge e1 = new Edge(v1, v2, 1);
        Edge e2 = new Edge(v2, v4, 1);
        Edge e3 = new Edge(v2, v3, 1);
        Edge e4 = new Edge(v3, v5, 1);

        
        EdgeList edgeList = new EdgeList();
        edgeList.insert(e1);
        edgeList.insert(e2);
        edgeList.insert(e3);
        edgeList.insert(e4);

        
        v1.addEdge(e1);
        v2.addEdge(e1);
        v2.addEdge(e2);
        v2.addEdge(e3);
        v3.addEdge(e3);
        v3.addEdge(e4);
        v4.addEdge(e2);
        v5.addEdge(e4);



    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    private VertexList vertexes;
    private EdgeList edges;
    Vertex start;

    Maze() {
        this.vertexes = new VertexList();
        this.edges = new EdgeList();
        this.start = null;
    }

    Maze(String fileName) {
        this();
        try {
            String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(fileName)));
            initializeMaze(content);
        } catch (java.io.IOException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    private void initializeMaze(String content) {
        String[] lines = content.split("\n");
        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                char symbol = line.charAt(x);
                if (symbol != '#') {
                    Vertex vertex = new Vertex(x, y, symbol);
                    vertexes.add(vertex);
                    if (start == null && symbol != ' ') {
                        start = vertex;
                    }
                    connectAdjacentVertices(vertex);
                }
            }
        }
    }

    private void connectAdjacentVertices(Vertex vertex) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, down, left, right
        for (int[] dir : directions) {
            int newX = vertex.getX() + dir[0];
            int newY = vertex.getY() + dir[1];
            for (Vertex v : vertexes.toArray()) {
                if (v.getX() == newX && v.getY() == newY) {
                    Edge edge = new Edge(vertex, v, 1);
                    if (!edges.contains(edge)) {
                        edges.insert(edge);
                        v.addEdge(edge);
                        vertex.addEdge(edge);
                    }
                }
            }
        }
    }

    static Maze createMaze(String mazeString) {
        Maze maze = new Maze();
        maze.initializeMaze(mazeString);
        return maze;
    }

    String latexCode(){
        String result = "\\documentclass[hidelinks, 12pt, a4paper]{article}\r\n" + //
        "\\usepackage{tikz}\n" + //
        "\n" + //
        "\\begin{document}\n" + //
        "\n" + //
        "\\begin{tikzpicture}[node/.style={circle, draw, minimum size=1.2em},yscale=-1]\n";
        for(Vertex v: getVertices()){
            result += v.latexCode() + "\n";
        }
        result += "\n";
        for(Edge e: getEdges()){
            result += e.latexCode() + "\n";
        }
        result += "\\end{tikzpicture}\r\n" + //
                        "\r\n" + //
                        "\\end{document}";
        return result;
    }

    Vertex[] getVertices() {
       return vertexes.toArray() ;
    }

    Edge[] getEdges() {
        return edges.getEdges();
    }

    public void stage1Reducing() {
        boolean changesMade;
        do {
            changesMade = false;
            Vertex[] verticesArray = vertexes.toArray();
            for (Vertex v : verticesArray) {
                Edge[] edgesArray = v.getEdges();
                if (v.getSymbol() == ' ' && edgesArray.length == 2) {
                    Edge edge1 = edgesArray[0];
                    Edge edge2 = edgesArray[1];

                    Vertex neighbor1 = edge1.v1 == v ? edge1.v2 : edge1.v1;
                    Vertex neighbor2 = edge2.v1 == v ? edge2.v2 : edge2.v1;

                    double newWeight = edge1.weight + edge2.weight;

                    Edge newEdge = new Edge(neighbor1, neighbor2, newWeight);
                    neighbor1.addEdge(newEdge);
                    neighbor2.addEdge(newEdge);

                    edges.insert(newEdge);

                    neighbor1.removeEdge(edge1);
                    neighbor1.removeEdge(edge2);
                    neighbor2.removeEdge(edge1);
                    neighbor2.removeEdge(edge2);

                    edges.remove(edge1);
                    edges.remove(edge2);

                    vertexes.remove(v);
                    changesMade = true;
                }
            }
        } while (changesMade);
    }


    public void stage2Reducing() {
        boolean changesMade;
        do {
            changesMade = false;
            Vertex[] verticesArray = vertexes.toArray();
            for (Vertex v : verticesArray) {
                Edge[] edgesArray = v.getEdges();
                if ((v.getSymbol() == ' ' && edgesArray.length == 1) || edgesArray.length == 0) {
                    if (edgesArray.length == 1) {
                        Edge edge = edgesArray[0];
                        Vertex neighbor = edge.v1 == v ? edge.v2 : edge.v1;
                        neighbor.removeEdge(edge);
                        edges.remove(edge);
                    }
                    vertexes.remove(v);
                    changesMade = true;
                }
            }
        } while (changesMade);
    }

    void stage3Reducing() {
        Vertex[] verticesArray = vertexes.toArray();
        for (Vertex v : verticesArray) {
            if (v.getSymbol() == 'T') {
                Edge[] edgesArray = v.getEdges();
                for (Edge edge : edgesArray) {
                    edge.weight *= 2; 
                }
            }
        }
    }

   public Vertex[] getAllDoors() {
        Vertex[] verticesArray = vertexes.toArray();
        Vertex[] doors = new Vertex[verticesArray.length]; // Initialize doors array
        int i = 0;
        for (Vertex vertex : verticesArray) {
            if (vertex.symbol == 'D' && vertex != null) {
                doors[i] = vertex;
                i++;
            }
        }

        Vertex[] trimmedDoors = new Vertex[i];
        for(int j = 0 ; j < i ; j++){
            trimmedDoors[j] = doors[j];
        }
        return trimmedDoors;
}

public Vertex[] getAllGoals() {
    Vertex[] verticesArray = vertexes.toArray();
    Vertex[] goals = new Vertex[verticesArray.length]; 
    int i = 0;
    for (Vertex vertex : verticesArray) {
        if (Character.isDigit(vertex.symbol) && vertex.symbol >= '0' && vertex.symbol <= '9' && vertex != null) {
            goals[i] = vertex;
            i++;
        }
    }

    Vertex[] trimmedGoals = new Vertex[i];
    System.arraycopy(goals, 0, trimmedGoals, 0, i);
    return trimmedGoals;
}

    public Vertex[] getAllKeys() {
        Vertex[] verticesArray = vertexes.toArray();
        Vertex[] Keys = new Vertex[verticesArray.length]; // Initialize doors array
        int i = 0;
        for (Vertex vertex : verticesArray) {
            if (vertex.symbol == 'K' && vertex != null) {
                Keys[i] = vertex;
                i++;
            }
        }

        Vertex[] trimmedKeys = new Vertex[i];
        for(int j = 0 ; j < i ; j++){
            trimmedKeys[j] = Keys[j];
        }

        
        return trimmedKeys;
    }

    boolean isReachAble(Vertex start, Vertex goal) {
        int[] num = new int[vertexes.size()];
    
        for (int j = 0; j < vertexes.size(); j++) {
            num[j] = 0;
        }
    
        dfs(start, goal, num, 1);
    
        return num[goal.counter] != 0;
    }
    
    private void dfs(Vertex v, Vertex goal, int[] num, int i) {
        num[v.counter] = i; 
    
        if (v.equals(goal)) {
            return;
        }
    
        for (Edge edge : v.getEdges()) {
            Vertex u = (edge.v1.equals(v)) ? edge.v2 : edge.v1; 
            if (num[u.counter] == 0 && v.symbol != 'D' ) { 
                dfs(u, goal, num, i + 1); 
            }
        }
    }


    Vertex[] isReachAblePath(Vertex start, Vertex goal) {
        int[] num = new int[vertexes.size()];
        Vertex[] path = new Vertex[vertexes.size()];
        Vertex current = start ;
        int endIndex = dfsWithPath(start ,current, goal, num, path, 1 , null);
    
        
        if (endIndex == 0) {
            return new Vertex[0];
        }
    
        Vertex[] result = copyNonNullElements(path);
        
    
        return result;
    }
    
    private int dfsWithPath(Vertex start , Vertex v, Vertex goal, int[] num, Vertex[] path, int i, Vertex previous) {
        if (num[v.counter] == 0) {
            path[i - 1] = v;
        }
        num[v.counter] = i;
    
        if (v.equals(goal)) {
            return i;
        }
    
        for (Edge edge : v.getEdges()) {
            Vertex u = (edge.v1.equals(v)) ? edge.v2 : edge.v1;
            if (num[u.counter] == 0) {
                if (v.symbol != 'D' || v == start) {
                    int newIndex = dfsWithPath(start , u, goal, num, path, i + 1, v);
                    if (newIndex > i) {
                        return newIndex;
                    }
                } else if (previous != null && !v.equals(previous)) {
                    int newIndex = dfsWithPath(start , previous, goal, num, path, i + 1, v);
                    if (newIndex > i - 1) {
                        return newIndex;
                    }
                }
            }
        }
    
        return 0;
    }
    
    private Vertex[] copyNonNullElements(Vertex[] array) {
        int count = 0;
        for (Vertex vertex : array) {
            if (vertex != null) {
                count++;
            }
        }
    
        Vertex[] result = new Vertex[count];
        int index = 0;
        for (Vertex vertex : array) {
            if (vertex != null) {
                result[index++] = vertex;
            }
        }
    
        return result;
    }
    double shortestPathDistanceNoDoor(Vertex start, Vertex goal) {
        return 0 ;
    }

    Vertex[] shortestPathPathNoDoor(Vertex start, Vertex goal) {
        return null ;
    }

    double shortestPathDistanceDoor(Vertex start, Vertex goal, boolean goUp) {
        return 0;
    }

    Vertex[] shortestPathPathDoor(Vertex start, Vertex goal, boolean goUp) {
        return null ;
    }

    Vertex getVertex(Vertex v) {
        Vertex[] verticesArray = vertexes.toArray();
        for (Vertex vertex : verticesArray) {
            if (vertex.equals(v)) {
                return vertex;
            }
        }
        return null;
    }

boolean isReachAbleThroughDoor(Vertex start, Vertex goal) {
    Vertex[] keys = getAllKeys(); 
    Vertex[] doors = getAllDoors(); 

    for (Vertex key : keys) {
        if (isReachAbledoor(start, key)) {
            for (Vertex door : doors) {
                if (isReachAbledoor(key, door)) {
                    if (isReachAbledoor(door, goal)) {
                        return true;
                    }
                }
            }
        }
    }
    return false;
}

boolean isReachAbledoor(Vertex start, Vertex goal) {
    int[] num = new int[vertexes.size()];

    for (int j = 0; j < vertexes.size(); j++) {
        num[j] = 0;
    }

    dfsdoor(start, goal, num, 1);

    return num[goal.counter] != 0;
}

private void dfsdoor(Vertex v, Vertex goal, int[] num, int i) {
    num[v.counter] = i; 

    if (v.equals(goal)) {
        return;
    }

    for (Edge edge : v.getEdges()) {
        Vertex u = (edge.v1.equals(v)) ? edge.v2 : edge.v1; 
        if (num[u.counter] == 0 ) { 
            dfs(u, goal, num, i + 1); 
        }
    }
}


private Vertex[] getVerticesWithSymbol(char symbol) {
    int count = 0;
    for (Vertex v : vertexes) {
        if (v.symbol == symbol) {
            count++;
        }
    }

    Vertex[] result = new Vertex[count];
    int index = 0;
    for (Vertex v : vertexes) {
        if (v.symbol == symbol) {
            result[index++] = v;
        }
    }
    return result;
}

private Vertex[] concatenatePaths(Vertex[] path1, Vertex[] path2, Vertex[] path3) {
    int length = path1.length + path2.length - 1 + path3.length - 1; 
    Vertex[] result = new Vertex[length];
    int index = 0;

    for (Vertex v : path1) {
        result[index++] = v;
    }

    for (int i = 1; i < path2.length; i++) {
        result[index++] = path2[i];
    }

    for (int i = 1; i < path3.length; i++) {
        result[index++] = path3[i];
    }

    return result;
}


private void sortVertices(Vertex[] vertices) {
    
    int n = vertices.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (vertices[j].toString().compareTo(vertices[j + 1].toString()) > 0) {
                
                Vertex temp = vertices[j];
                vertices[j] = vertices[j + 1];
                vertices[j + 1] = temp;
            }
        }
    }
}

Vertex[] isReachAbleThroughDoorPath(Vertex start, Vertex goal) {
    Vertex[] keys = getAllKeys();
    Vertex[] doors = getAllDoors();

    sortVertices(keys);
    sortVertices(doors);

    for (Vertex key : keys) {
        if (isReachAbleThroughDoor(start, key)) {
            for (Vertex door : doors) {
                if (isReachAbleThroughDoor(key, door)) {
                    if (isReachAbleThroughDoor(door, goal)) {
                        Vertex[] pathToKey = isReachAblePath(start, key);
                        Vertex[] pathToDoor = isReachAblePath(key, door);
                        Vertex[] pathToGoal = isReachAblePath(door, goal);
                        return concatenatePaths(pathToKey, pathToDoor, pathToGoal);
                    }
                }
            }
        }
    }
    return new Vertex[0];
}

    double shortestPathThroughDoor(Vertex start, Vertex goal) {
        return 0 ;
    }

    Vertex[] shortestPathThroughDoorPath(Vertex start, Vertex goal) {
        Vertex[] list = new Vertex[1] ;
        list[1] = goal ;
        return list ;
    }

    boolean canReachGoal(char targetGoal){
        return false ;
    }

    Vertex[] canReachGoalPath(char targetGoal){
        Vertex[] list = new Vertex[1] ;
        list[1] = new Vertex(1, 5, targetGoal);
        return list ;
    }

    double getRatio(Vertex goal){
        return 0 ;
    }

    Vertex getRecommendedGoal(){
       Vertex list = new Vertex(1, 5, 'T');
        return list ;
    }

    double getRecommendedRatio(){
        return 0 ;
    }

    Vertex[] getRecommendedPath(){
        Vertex[] list = new Vertex[1] ;
        list[1] = new Vertex(1, 5, 'T');
        return list ;
    }

}

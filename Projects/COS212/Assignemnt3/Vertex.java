public class Vertex {
    private EdgeList edges;
    int xPos;
    int yPos;
    char symbol;
    static int globalCounter = 0;
    int counter = globalCounter++;

    Vertex(int x, int y, char sym) {
        this.xPos = x;
        this.yPos = y;
        this.symbol = sym;
        this.edges = new EdgeList();
    }

    @Override
    public String toString() {
        return "(" + xPos + ":" + yPos + ")[" + symbol + "]";
    }

    String latexCode(){
        return "\\node[node] (" +counter + ") at (" + xPos + "," + (yPos) + ") {" + symbol + "};";
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        try {
            Vertex vertex = (Vertex) obj;
            if (vertex.xPos == xPos && vertex.yPos == yPos) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void addEdge(Edge edge) {
        edges.insert(edge);
    }

    Edge[] getEdges() {
        return edges.getEdges();
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getSymbol(){
        return symbol;
    }

    void removeEdge(Edge edge) {
        edges.remove(edge);
    }
}

import java.util.Iterator;

public class EdgeList implements Iterable<Edge> {
    private Edge[] edges;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public EdgeList() {
        edges = new Edge[INITIAL_CAPACITY];
        size = 0;
    }

    public void insert(Edge edge) {
        if (size == edges.length) {
            resize();
        }
        edges[size++] = edge;
    }

    public void remove(Edge edge) {
        int index = indexOf(edge);
        if (index != -1) {
            
            for (int i = index; i < size - 1; i++) {
                edges[i] = edges[i + 1];
            }
            edges[--size] = null; 
        }
    }

    public boolean contains(Edge edge) {
        return indexOf(edge) != -1;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newSize = edges.length * 2;
        Edge[] newEdges = new Edge[newSize];
        System.arraycopy(edges, 0, newEdges, 0, size);
        edges = newEdges;
    }

    private int indexOf(Edge edge) {
        for (int i = 0; i < size; i++) {
            if (edges[i].equals(edge)) {
                return i;
            }
        }
        return -1;
    }

    public Edge[] getEdges() {
        Edge[] returnedges = new Edge[size]; 
        for (int i = 0; i < size; i++) {
            if (edges[i] != null) {
                returnedges[i] = edges[i];
            }
        }
        return returnedges;
    }

    @Override
    public Iterator<Edge> iterator() {
        return new Iterator<Edge>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && edges[currentIndex] != null;
            }

            @Override
            public Edge next() {
                return edges[currentIndex++];
            }
        };
    }
}


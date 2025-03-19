import java.util.Iterator;

public class VertexList implements Iterable<Vertex> {
    private Vertex[] vertices;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public VertexList() {
        vertices = new Vertex[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(Vertex vertex) {
        if (size == vertices.length) {
            expandCapacity();
        }
        vertices[size++] = vertex;
    }

    private void expandCapacity() {
        Vertex[] newVertices = new Vertex[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i];
        }
        vertices = newVertices;
    }

    public boolean remove(Vertex vertex) {
        for (int i = 0; i < size; i++) {
            if (vertices[i].equals(vertex)) {
                for (int j = i; j < size - 1; j++) {
                    vertices[j] = vertices[j + 1];
                    vertices[j].counter--;
                }
                vertices[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    public Vertex[] toArray() {
        Vertex[] array = new Vertex[size];
        for (int i = 0; i < size; i++) {
            array[i] = vertices[i];
        }
        return array;
    }

    public boolean contains(Vertex vertex) {
        for (int i = 0; i < size; i++) {
            if (vertices[i].equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new Iterator<Vertex>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Vertex next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return vertices[currentIndex++];
            }
        };
    }
}

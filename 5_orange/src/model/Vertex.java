import java.util.ArrayList;

public class Vertex {
    char name;
    Edge cameFrom;
    ArrayList<Edge> neighbours;
    boolean visited;

    Vertex(char name) {
        this.name = name;
        neighbours = new ArrayList();
        visited = false;
        cameFrom = null;
    }
}

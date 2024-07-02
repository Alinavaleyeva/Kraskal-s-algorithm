
public class Edge {
    private Node start;
    private Node end;
    private int weight; // Добавление веса ребра

    public Edge(Node start, Node end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }
    public int getWeight() {
        return weight;
    }
}


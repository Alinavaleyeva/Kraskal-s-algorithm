public class Edge1 {
    private Node1 start;
    private Node1 end;
    private int weight; // Добавление веса ребра

    public Edge1(Node1 start, Node1 end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Node1 getStart() {
        return start;
    }

    public Node1 getEnd() {
        return end;
    }
    public int getWeight() {
        return weight;
    }
}




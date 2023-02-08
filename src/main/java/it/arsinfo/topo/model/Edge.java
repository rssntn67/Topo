package it.arsinfo.topo.model;

public class Edge {
    private final Node from;
    private final Node to;
    private final boolean directed;

    public Edge(Node a, Node b, boolean directed) {
        this.directed = directed;
        if (a.hashCode() < b.hashCode()) {
            from = a;
            to = b;
        } else {
            from = b;
            to = a;
        }
    }


    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public boolean connected(Node node) {
        return node.getId() == from.getId() || node.getId() == to.getId();
    }

    public boolean isDirected() {
        return directed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (directed != edge.directed) return false;
            if (!from.equals(edge.from)) return false;
            return to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + (directed ? 1 : 0);
        return result;
    }
}

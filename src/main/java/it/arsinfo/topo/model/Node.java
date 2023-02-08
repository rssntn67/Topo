package it.arsinfo.topo.model;

public class Node {

    private final Long id;
    private final String name;

    private Node parent = null;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node(Long id, String name) {
        this.id = id;
        this.name= name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node vertice = (Node) o;

        return id == vertice.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

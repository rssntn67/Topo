package it.arsinfo.topo.builder;

import java.util.HashMap;
import java.util.Map;

import it.arsinfo.topo.model.Edge;
import it.arsinfo.topo.model.Topology;
import it.arsinfo.topo.model.Node;

public class TopologyBuilder {
    private final NodeBuilder verticeBuilder= new NodeBuilder();
    private final EdgeBuilder collegamentoBuilder = new EdgeBuilder();

    private final Map<Long, Node> vertici = new HashMap<>();

    private final Map<Integer , Edge> collegamenti = new HashMap<>();

    public TopologyBuilder addNode(Long vertex) {
        Node v = verticeBuilder.setId(vertex).setName("v:"+vertex).createNode();
        if (!vertici.containsKey(vertex)) {
            vertici.put(vertex, v);
        }
        return this;
    }

    public TopologyBuilder addEdge(Long vertexA, Long vertexB) {
        Node A = verticeBuilder.setId(vertexA).setName("v:"+vertexA).createNode();
        if (vertici.containsKey(vertexA)) {
            A = vertici.get(vertexA);
        } else {
            vertici.put(vertexA, A);
        }
        Node B = verticeBuilder.setId(vertexB).setName("v:"+vertexB).createNode();
        if (vertici.containsKey(vertexB)) {
            B = vertici.get(vertexB);
        } else {
            vertici.put(vertexB, B);
        }
        Edge AB = collegamentoBuilder.setTo(B).setFrom(A).createEdge();
        if (!collegamenti.containsKey(AB.hashCode())) {
            collegamenti.put(AB.hashCode(),AB);
        }
        return this;
    }

    public Topology getTopology() {
        Topology g = new Topology();
        vertici.values().forEach(v -> g.getNodes().add(v));
        collegamenti.values().forEach(c -> g.getEdges().add(c));
        return g;
    }


    public static class NodeBuilder {
        private Long id;
        private String name;

        public NodeBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public NodeBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Node createNode() {
            return new Node(id, name);
        }
    }

    public static class EdgeBuilder {
        private Node from;
        private Node to;
        private boolean directed = true;

        public EdgeBuilder setDirected(boolean directed) {
            this.directed = directed;
            return this;
        }

        public EdgeBuilder setFrom(Node from) {
            this.from = from;
            return this;
        }

        public EdgeBuilder setTo(Node to) {
            this.to = to;
            return this;
        }

        public Edge createEdge() {
            return new Edge(from, to, directed);
        }
    }
}

package it.arsinfo.topo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Topology {

    private final Set<Node> nodes = new HashSet<>();

    private final Set<Edge> edges = new HashSet<>();

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public boolean dijkstra = false;

    public Node getbyNodeId(Integer nodeid) {
        for (Node node: nodes) {
            if (node.getId() == nodeid) {
                return node;
            }
        }
        return null;
    }

    public int getAverageDistance() {
        int L = 0;
        // L = 2/N(N-1) * Somma d(i,j)

        return 0;
    }

    public int getDiameter() {
        // d(i,j)
        return 0;
    }

    public int getDistance(Node from, Node to) {
        Path path = getShortestPath(from, to);
        return from.getDijkstra();
    }

    public Path getShortestPath(Node from, Node to) {
        computeDijkstra(to);
        Path path = new Path(this);
        Node child = from;
        //no connection
        if (from.getParent() == null) {
            return null;
        }
        while (child.getParent() != null) {
            path.add(child);
            child = child.getParent();
        }
        path.add(to);
        return path;
    }

    public void computeDijkstra(Node startVertex) {
        final Set<Node> nn = new HashSet<>();
        nodes.forEach(node -> {
            node.setParent(null);
            node.setDijkstra(-1);
            nn.add(node);
        });
        if (!nn.remove(startVertex)) {
            return;
        }
        startVertex.setDijkstra(0);
        final List<Edge> edgeToParse = new ArrayList<>(edges);
        computeDijkstra(nn, edgeToParse, startVertex);
        dijkstra = true;
    }

    private void computeDijkstra(Set<Node> nodeToParse, List<Edge> edgesToParse, Node parent) {
        System.out.println("computeDijkstra: parent:"+parent);
        List<Edge> connectedEdges = new ArrayList<>();
        Set<Node> connectedNodes = new HashSet<>();
        for (Edge edge : edgesToParse) {
            if (edge.connected(parent)) {
                System.out.println("computeDijkstra: connected:"+edge);
                connectedEdges.add(edge);
                Node to = edge.getTo();
                if (to.getDijkstra() == -1 || to.getDijkstra() > edge.getWeight() + parent.getDijkstra()) {
                    to.setDijkstra(edge.getWeight() + parent.getDijkstra());
                    connectedNodes.add(to);
                    to.setParent(parent);
                    System.out.println("computeDijkstra: to:"+to);
                }
                Node from = edge.getFrom();
                if (from.getDijkstra() == -1 || from.getDijkstra() > edge.getWeight() + parent.getDijkstra()) {
                    from.setDijkstra(edge.getWeight() + parent.getDijkstra());
                    connectedNodes.add(from);
                    from.setParent(parent);
                    System.out.println("stepForward: from:"+from);
                }
            }
        }
        edgesToParse.removeAll(connectedEdges);
        for (Node connected: connectedNodes) {
            if (edgesToParse.isEmpty()) {
                break;
            }
            nodeToParse.remove(connected);
            computeDijkstra(nodeToParse, edgesToParse, connected);
        }
    }

    public Node getDefaultVertex() {
        Node candidateDV = null;
        for (Node node : nodes) {
            if (candidateDV == null) {
                candidateDV = node;
                continue;
            }
            if (candidateDV.getDegree() > node.getDegree()) {
                continue;
            }
            candidateDV = node;
        }
        return candidateDV;
    }

    @Override
    public String toString() {
        return "Graph{\n\t" +
                "Nodes=" + nodes +
                "\n\tEdges=" + edges +
                "\n}";
    }
}

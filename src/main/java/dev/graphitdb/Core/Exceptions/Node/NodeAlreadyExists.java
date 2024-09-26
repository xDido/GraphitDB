package dev.graphitdb.Core.Exceptions.Node;

public class NodeAlreadyExists extends RuntimeException {
    public NodeAlreadyExists(String message) {
        super(message);
    }
}

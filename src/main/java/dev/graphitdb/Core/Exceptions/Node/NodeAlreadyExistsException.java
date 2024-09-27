package dev.graphitdb.Core.Exceptions.Node;

public class NodeAlreadyExistsException extends RuntimeException {
    public NodeAlreadyExistsException(String message) {
        super(message);
    }
}

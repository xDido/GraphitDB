package dev.graphitdb.Core.Exceptions.Node;

/**
 * Exception thrown when a requested node cannot be found in the graph database.
 */
public class NodeNotFoundException extends Exception {
    /**
     * Constructs a new NodeNotFoundException with a default message.
     */
    public NodeNotFoundException() {
        super("The requested node could not be found.");
    }
}

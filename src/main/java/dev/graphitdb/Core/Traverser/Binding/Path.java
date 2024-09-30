package dev.graphitdb.Core.Traverser.Binding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a traversal path consisting of node bindings and edge bindings.
 * The path starts with a specific node binding and progresses through
 * additional nodes and edges.
 */
public class Path {
    private NodeBinding startNodeBinding;
    private List<NodeBinding> nodeBindings;
    private List<EdgeBinding> edgeBindings;

    /**
     * Constructor to initialize the path with a start node.
     *
     * @param startNodeBinding The starting node of the path.
     */
    public Path(NodeBinding startNodeBinding) {
        this.startNodeBinding = startNodeBinding;
        this.nodeBindings = new ArrayList<>();
        this.edgeBindings = new ArrayList<>();
    }

    /**
     * Default constructor to create an empty path.
     */
    public Path() {
        this.nodeBindings = new ArrayList<>();
        this.edgeBindings = new ArrayList<>();
    }

    /**
     * Adds a node binding to the path.
     *
     * @param nodeBinding The node binding to be added.
     */
    public void addNodeBinding(NodeBinding nodeBinding) {
        nodeBindings.add(nodeBinding);
    }

    /**
     * Adds an edge binding to the path.
     *
     * @param edgeBinding The edge binding to be added.
     */
    public void addEdgeBinding(EdgeBinding edgeBinding) {
        edgeBindings.add(edgeBinding);
    }

    /**
     * Gets the starting node binding of the path.
     *
     * @return The starting node binding.
     */
    public NodeBinding getStartNodeBinding() {
        return startNodeBinding;
    }

    /**
     * Gets the list of node bindings in the path.
     *
     * @return A list of node bindings.
     */
    public List<NodeBinding> getNodeBindings() {
        return nodeBindings;
    }

    /**
     * Gets the list of edge bindings in the path.
     *
     * @return A list of edge bindings.
     */
    public List<EdgeBinding> getEdgeBindings() {
        return edgeBindings;
    }

    /**
     * Gets the total length of the path, defined by the number of node bindings.
     *
     * @return The length of the path (number of node bindings).
     */
    public int getLength() {
        return nodeBindings.size();
    }

    /**
     * Retrieves all unique aliases used in the path.
     * Checks for duplicate aliases and throws an exception if any are found.
     *
     * @return A set of unique aliases.
     * @throws Exception If duplicate aliases are detected.
     */
    public Set<String> getAliases() throws Exception {
        Set<String> aliases = new HashSet<>();

        // Check and add alias for the start node, if present
        startNodeBinding.getAlias().ifPresent(alias -> {
            if (!aliases.add(alias)) {
                throw new RuntimeException("Duplicate alias: " + alias);
            }
        });

        // Check and add aliases for node bindings
        for (NodeBinding nodeBinding : nodeBindings) {
            nodeBinding.getAlias().ifPresent(alias -> {
                if (!aliases.add(alias)) {
                    throw new RuntimeException("Duplicate alias: " + alias);
                }
            });
        }

        // Check and add aliases for edge bindings
        for (EdgeBinding edgeBinding : edgeBindings) {
            edgeBinding.getAlias().ifPresent(alias -> {
                if (!aliases.add(alias)) {
                    throw new RuntimeException("Duplicate alias: " + alias);
                }
            });
        }

        return aliases;
    }
}

package dev.graphitdb.Core.Operators.Select;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.DataStructure.Node.Node;

public abstract class SelectOperator {
    protected String fieldName;
    protected String fieldValue;

    public abstract void build(String fieldName, String fieldValue);

    public abstract Iterable<String> execute() throws Exception;

    public abstract Iterable<Node> executeWithNodes() throws Exception;

    public abstract Iterable<String> filterNodes(Iterable<String> verticesIds) throws Exception;

    public abstract boolean isVertexValid(Node node);

    public abstract boolean isEdgeValid(Edge edge);

    public abstract Iterable<Node> executeWithVertices();

    public abstract String getOperator();

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}

package dev.graphitdb.Core.Operators.Select;

import dev.graphitdb.Core.DataStructure.Node.LocalNodeService;
import dev.graphitdb.Core.DataStructure.Node.Node;
import dev.graphitdb.Redis.Indices.RedisIndexManager;
import dev.graphitdb.Core.DataStructure.Edge.Edge;

import java.util.ArrayList;
import java.util.List;

public class EqualsSelectOperator extends SelectOperator {

    private final RedisIndexManager redisIndexManager;
    private final LocalNodeService nodeService;

    public EqualsSelectOperator(RedisIndexManager redisIndexManager, LocalNodeService nodeService) {
        this.redisIndexManager = redisIndexManager;
        this.nodeService = nodeService;
    }

    @Override
    public void build(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public Iterable<String> execute() {
        if (redisIndexManager.isIndexExist(fieldName)) {
            return redisIndexManager.getNodeIdsFromIndex(fieldName, fieldValue);
        }

        List<String> nodesIds = new ArrayList<>();
        Iterable<Node> nodes = nodeService.getAllNodes();
        for (Node node : nodes) {
            if (node.isPropertyExist(fieldName) && node.getProperty(fieldName).equals(fieldValue)) {
                nodesIds.add(node.getId());
            }
        }

        return nodesIds;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public Iterable<Node> executeWithNodes() throws Exception {
        return null;
    }

    /**
     * @param verticesIds
     * @return
     * @throws Exception
     */
    @Override
    public Iterable<String> filterNodes(Iterable<String> verticesIds) throws Exception {
        return null;
    }

//    @Override
//    public Iterable<Node> executeWithVertices() {
//        if (redisIndexDataManager.isIndexExist(fieldName)) {
//            return nodeService.getNode(redisIndexDataManager.getVerticesIdsFromIndex(fieldName, fieldValue));
//        }
//
//        List<Node> vertices = new ArrayList<>();
//        Iterable<Node> allVertices = nodeService.getAllNodes();
//        for (Node node : allVertices) {
//            if (node.isPropertyExist(fieldName) && node.getProperty(fieldName).equals(fieldValue)) {
//                vertices.add(node);
//            }
//        }
//
//        return vertices;
//    }

//    @Override
//    public Iterable<String> filterVertices(Iterable<String> verticesIds) throws Exception {
//        if (redisIndexDataManager.isIndexExist(fieldName)) {
//            List<String> intersection = new ArrayList<>();
//            for (String vertexId : verticesIds) {
//                if (redisIndexDataManager.isIndexContainsVertex(fieldName, fieldValue, vertexId)) {
//                    intersection.add(vertexId);
//                }
//            }
//            return intersection;
//        }
//
//        List<String> answer = new ArrayList<>();
//        Iterable<Node> vertices = nodeService.getNode(verticesIds);
//        for (Node node : vertices) {
//            if (node.isPropertyExist(fieldName) && node.getProperty(fieldName).equals(fieldValue)) {
//                answer.add(node.getId());
//            }
//        }
//
//        return answer;
//    }

    @Override
    public boolean isVertexValid(Node node) {
        return node.isPropertyExist(fieldName) && node.getProperty(fieldName).equals(fieldValue);
    }

    @Override
    public boolean isEdgeValid(Edge edge) {
        return edge.isPropertyExist(fieldName) && edge.getProperty(fieldName).equals(fieldValue);
    }

    /**
     * @return
     */
    @Override
    public Iterable<Node> executeWithVertices() {
        return null;
    }

    @Override
    public String getOperator() {
        return "=";
    }
}

package dev.graphitdb.Core.DataStructure.Edge.EdgeStorage;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.Exceptions.Redis.RedisEdgeException;
import dev.graphitdb.Redis.Data.Edge.RedisEdgeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EdgeStorage implements EdgeRepository {

    @Autowired
    private RedisEdgeManager redisEdgeManager;

    public EdgeStorage(RedisEdgeManager redisEdgeManager) {
        this.redisEdgeManager = redisEdgeManager;
    }

    @Override
    public <S extends Edge> S save(S entity, boolean isOutgoing) {
        try {
            System.out.println("Saving edge: " + entity.getId());
            redisEdgeManager.saveEdge(entity, isOutgoing);
            return entity;
        } catch (Exception e) {
            System.err.println("Failed to save edge: " + e.getMessage());
            throw new RedisEdgeException("Failed to save edge", e);
        }
    }

    public Iterable<Edge> findAllByNodeId(String nodeId, boolean isOutgoing) throws Exception {
        return redisEdgeManager.getEdges(nodeId, isOutgoing, 5, 10);
    }


    @Override
    public <S extends Edge> Iterable<S> saveAll(Iterable<S> entities) {
        for (Edge entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public Optional<Edge> findById(String id) {
        try {
            Edge edge = redisEdgeManager.getEdgeById(id);
            return Optional.ofNullable(edge);
        } catch (Exception e) {
            System.err.println("Failed to find edge by ID: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(String id) {
        try {
            return redisEdgeManager.getEdgeById(id) != null;
        } catch (Exception e) {
            System.err.println("Failed to check if edge exists by ID: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Iterable<Edge> findAllById(Iterable<String> ids) {
        List<Edge> edges = new ArrayList<>();
        for (String id : ids) {
            findById(id).ifPresent(edges::add);
        }
        return edges;
    }

    @Override
    public long count() {
        return 0;
    }

    public void deleteById(String id) {
        try {
            redisEdgeManager.deleteEdgeById(id);
        } catch (Exception e) {
            System.err.println("Failed to delete edge by ID: " + e.getMessage());
            throw new RedisEdgeException("Failed to delete edge", e);
        }
    }


    public void delete(Edge edge, boolean isOutgoing) {
        try {
            redisEdgeManager.deleteEdgeById(edge.getId());
        } catch (Exception e) {
            System.err.println("Failed to delete edge: " + e.getMessage());
            throw new RedisEdgeException("Failed to delete edge", e);
        }
    }

    @Override
    public void deleteAllBySourceNodeId(String sourceNodeId) throws Exception {

    }

    @Override
    public <S extends Edge> S save(S entity) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Iterable<Edge> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void delete(Edge entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Edge> entities) {

    }

    @Override
    public void deleteAll() {

    }

}

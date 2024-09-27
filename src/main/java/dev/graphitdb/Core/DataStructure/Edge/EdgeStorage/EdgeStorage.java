package dev.graphitdb.Core.DataStructure.Edge.EdgeStorage;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import dev.graphitdb.Core.Exceptions.Redis.RedisEdgeException;
import dev.graphitdb.Redis.Interfaces.RedisDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EdgeStorage implements EdgeRepository {

    @Autowired
    private RedisDataManager redisDataManager;

    public EdgeStorage(RedisDataManager redisDataManager) {
        this.redisDataManager = redisDataManager;
    }

    /**
     * Save an edge entity.
     *
     * @param entity the edge entity to save
     * @return the saved edge entity
     * @throws RedisEdgeException if the save operation fails
     */
    @Override
    public <S extends Edge> S save(S entity, boolean isOutgoing) {
        try {
            System.out.println(STR."Saving edge: \{entity.getId()}");
            redisDataManager.saveEdge(entity, isOutgoing);
            return entity;
        } catch (Exception e) {
            System.err.println(STR."Failed to save edge: \{e.getMessage()}");
        }
        return entity;
    }

    /**
     * @param entity the edge entity to save
     * @param <S>    the edge entity type
     * @return the saved edge entity
     */
    @Override
    public <S extends Edge> S save(S entity) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
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
            Edge edge = redisDataManager.getEdgeById(id);
            return Optional.ofNullable(edge);
        } catch (Exception e) {
            System.err.println(STR."Failed to find edge by ID: \{e.getMessage()}");
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        try {
            return redisDataManager.getEdgeById(id) != null;
        } catch (Exception e) {

            System.err.println(STR."Failed to check if edge exists by ID: \{e.getMessage()}");
            return false;
        }
    }

    @Override
    public Iterable<Edge> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Iterable<Edge> findAllById(Iterable<String> ids) {
        throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
    }

    public Iterable<Edge> findAllByNodeId(String id, boolean isOutgoing) throws Exception {
        List<Edge> result = new ArrayList<>();
        Iterable<Edge> Outedge = redisDataManager.getEdges(id, true, 5, 20);
        Iterable<Edge> Inedge = redisDataManager.getEdges(id, false, 5, 20);
        if (isOutgoing) {
            if (Outedge != null) {
                result.add((Edge) Outedge);
            }
        } else {
            if (Inedge != null) {
                result.add((Edge) Inedge);
            }
        }
        return result;
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public void deleteById(String edgeId) {
        redisDataManager.deleteEdgeById(edgeId);
    }

    /**
     * @param entity
     */
    @Override
    public void delete(Edge entity) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void delete(Edge entity, boolean isOutgoing) throws Exception {
        redisDataManager.deleteEdge(entity.getSourceNodeId(), entity.getDestinationNodeId(), entity.getLabel(), isOutgoing);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            redisDataManager.deleteEdgeById(id);
        }
    }
    @Override
    public void deleteAllBySourceNodeId(String sourceNodeId) throws Exception {
        redisDataManager.deleteEdges(sourceNodeId, true);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }

    @Override
    public void deleteAll(Iterable<? extends Edge> entities) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }
}

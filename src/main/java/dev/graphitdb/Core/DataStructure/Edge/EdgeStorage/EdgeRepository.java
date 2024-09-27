package dev.graphitdb.Core.DataStructure.Edge.EdgeStorage;

import dev.graphitdb.Core.DataStructure.Edge.Edge;
import org.springframework.data.repository.CrudRepository;

public interface EdgeRepository extends CrudRepository<Edge, String> {
    <S extends Edge> S save(S entity, boolean isOutgoing);

    void delete(Edge entity, boolean isOutgoing) throws Exception;

    void deleteAllBySourceNodeId(String sourceNodeId) throws Exception;
}

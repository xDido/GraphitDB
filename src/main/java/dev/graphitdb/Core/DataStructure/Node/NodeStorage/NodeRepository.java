package dev.graphitdb.Core.DataStructure.Node.NodeStorage;

import dev.graphitdb.Core.DataStructure.Node.Node;
import org.springframework.data.repository.CrudRepository;

public interface NodeRepository extends CrudRepository<Node, String> {
}

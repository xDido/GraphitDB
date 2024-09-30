package dev.graphitdb.Core.DataStructure.Node;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RedisHash("Node")
public class Node implements Serializable {


    @Serial
    private static final long serialVersionUID = 8442048367609690781L;

    private String id;
    private String label;
    private Map<String, String> properties;


    public Node() {
        this.id = UUID.randomUUID().toString();
        this.label = "";
        this.properties = new HashMap<>();
    }

    public Node(String id) {
        this.id = id;
        this.label = "";
        this.properties = new HashMap<>();
    }

    // Constructor for JSON deserialization
    @JsonCreator
    public Node(@JsonProperty("id") String id,
                @JsonProperty("label") String label,
                @JsonProperty("properties") HashMap<String, String> properties) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.label = label;
        this.properties = properties != null ? properties : new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            setProperty(entry.getKey(), entry.getValue());
        }
    }


    public boolean isPropertyExist(String key) {
        return properties.containsKey(key);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String toString() {
        return String.format("Node [id=%s, label=%s, properties=%s]", id, label, properties);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!id.equals(node.id)) return false;
        if (!label.equals(node.label)) return false;
        return properties.equals(node.properties);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + properties.hashCode();
        return result;
    }
}

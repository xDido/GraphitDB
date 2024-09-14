package dev.graphitdb.Core.DataStructure.Node;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTests {

    private Node node;
    private final String testId = UUID.randomUUID().toString();

    @BeforeEach
    public void setUp() {
        node = new Node(testId, "testLabel", new HashMap<>());
    }

    @Test
    public void testDefaultConstructor() {
        Node defaultNode = new Node();
        assertNotNull(defaultNode.getId());
        UUID.fromString(defaultNode.getId());
        assertTrue(true);
        assertEquals("", defaultNode.getLabel());
        assertTrue(defaultNode.getProperties().isEmpty());
    }
    @Test
    public void testConstructorWithId() {
        Node nodeWithId = new Node(testId);
        assertEquals(testId, nodeWithId.getId());
        assertEquals("", nodeWithId.getLabel());
        assertTrue(nodeWithId.getProperties().isEmpty());
    }


    @Test
    public void testConstructorWithJsonCreator() {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("key1", "value1");

        Node nodeWithJson = new Node(testId, "testLabel", properties);
        assertEquals(testId, nodeWithJson.getId()); // Use testId here
        assertEquals("testLabel", nodeWithJson.getLabel());
        assertEquals("value1", nodeWithJson.getProperty("key1"));
    }

    @Test
    public void testGettersAndSetters() {
        node.setId("newId");
        node.setLabel("newLabel");

        assertEquals("newId", node.getId());
        assertEquals("newLabel", node.getLabel());
    }

    @Test
    public void testSetProperties() {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("key1", "value1");
        properties.put("key2", "value2");

        node.setProperties(properties);
        assertEquals("value1", node.getProperty("key1"));
        assertEquals("value2", node.getProperty("key2"));
    }

    @Test
    public void testIsPropertyExist() {
        node.setProperty("key1", "value1");
        assertTrue(node.isPropertyExist("key1"));
        assertFalse(node.isPropertyExist("key2"));
    }

    @Test
    public void testPropertyManagement() {
        node.setProperty("key1", "value1");
        assertEquals("value1", node.getProperty("key1"));

        node.setProperty("key1", "value2");
        assertEquals("value2", node.getProperty("key1"));
    }

    @Test
    public void testEqualsAndHashCode() {
        Node anotherNode = new Node(testId, "testLabel", new HashMap<>());

        // Check equality and hashCode before modifying the id
        assertEquals(node, anotherNode);
        assertEquals(node.hashCode(), anotherNode.hashCode());

        // Change the id of anotherNode and check inequality
        anotherNode.setId("differentId");
        assertNotEquals(node, anotherNode);

        // Test equality and hashCode with nodes having different labels
        anotherNode.setId(testId);
        anotherNode.setLabel("differentLabel");
        assertNotEquals(node, anotherNode);
    }

    @Test
    public void testToString() {
        node.setLabel("testLabel");
        String expectedString = "Node [id=" + node.getId() + ", label=testLabel, properties={}]";
        assertEquals(expectedString, node.toString());
    }

    @Test
    public void testConstructorWithNullProperties() {
        Node nodeWithNullProperties = new Node(testId, "testLabel", null);
        assertEquals(testId, nodeWithNullProperties.getId());
        assertEquals("testLabel", nodeWithNullProperties.getLabel());
        assertTrue(nodeWithNullProperties.getProperties().isEmpty());
    }

    @Test
    public void testSetPropertiesWithEmptyMap() {
        HashMap<String, String> emptyProperties = new HashMap<>();
        node.setProperties(emptyProperties);
        assertTrue(node.getProperties().isEmpty());
    }
    @Test
    public void testDefaultConstructorProperties() {
        Node defaultNode = new Node();
        assertNotNull(defaultNode.getId());
        assertEquals("", defaultNode.getLabel());
        assertTrue(defaultNode.getProperties().isEmpty());
    }

}

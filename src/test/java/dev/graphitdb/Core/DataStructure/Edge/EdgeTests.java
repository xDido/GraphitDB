package dev.graphitdb.Core.DataStructure.Edge;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import dev.graphitdb.Core.DataStructure.Edge.Edge;
public class EdgeTests {

    @Test
    public void testConstructorWithValidParams() {
        Edge edge = new Edge("source1", "destination1");
        assertEquals("source1", edge.getSourceNodeId());
        assertEquals("destination1", edge.getDestinationNodeId());
        assertEquals("", edge.getLabel());
        assertTrue(edge.getProperties().isEmpty());
    }

    @Test
    public void testConstructorWithJsonCreator() {
        Map<String, String> properties = new HashMap<>();
        properties.put("key1", "value1");
        Edge edge = new Edge("source1", "destination1", "label1", properties);
        assertEquals("source1", edge.getSourceNodeId());
        assertEquals("destination1", edge.getDestinationNodeId());
        assertEquals("label1", edge.getLabel());
        assertEquals("value1", edge.getProperty("key1"));
    }

    @Test
    public void testAddProperty() {
        Edge edge = new Edge("source1", "destination1");
        edge.addProperty("key1", "value1");
        assertEquals("value1", edge.getProperty("key1"));
    }

    @Test
    public void testSetProperty() {
        Edge edge = new Edge("source1", "destination1");
        edge.setProperty("key2", "value2");
        assertEquals("value2", edge.getProperty("key2"));
    }

    @Test
    public void testSetProperties() {
        Edge edge = new Edge("source1", "destination1");
        Map<String, String> properties = new HashMap<>();
        properties.put("key3", "value3");
        properties.put("key4", "value4");
        edge.setProperties(properties);
        assertEquals("value3", edge.getProperty("key3"));
        assertEquals("value4", edge.getProperty("key4"));
    }

    @Test
    public void testGetProperty() {
        Edge edge = new Edge("source1", "destination1");
        edge.addProperty("key5", "value5");
        assertEquals("value5", edge.getProperty("key5"));
    }

    @Test
    public void testIsPropertyExist() {
        Edge edge = new Edge("source1", "destination1");
        edge.addProperty("key6", "value6");
        assertTrue(edge.isPropertyExist("key6"));
        assertFalse(edge.isPropertyExist("key7"));
    }

    @Test
    public void testSetLabel() {
        Edge edge = new Edge("source1", "destination1");
        edge.setLabel("newLabel");
        assertEquals("newLabel", edge.getLabel());
    }

    @Test
    public void testEquals() {
        Edge edge1 = new Edge("source1", "destination1", "label1", new HashMap<>());
        Edge edge2 = new Edge("source1", "destination1", "label1", new HashMap<>());
        Edge edge3 = new Edge("source2", "destination1", "label1", new HashMap<>());

        assertEquals(edge1, edge2);
        assertNotEquals(edge1, edge3);
        assertNotEquals(edge1, null);
        assertNotEquals(edge1, new Object());
    }

    @Test
    public void testHashCode() {
        Edge edge1 = new Edge("source1", "destination1", "label1", new HashMap<>());
        Edge edge2 = new Edge("source1", "destination1", "label1", new HashMap<>());
        Edge edge3 = new Edge("source2", "destination1", "label1", new HashMap<>());

        assertEquals(edge1.hashCode(), edge2.hashCode());
        assertNotEquals(edge1.hashCode(), edge3.hashCode());
    }

    @Test
    public void testToString() {
        Map<String, String> properties = new HashMap<>();
        properties.put("key8", "value8");
        Edge edge = new Edge("source1", "destination1", "label2", properties);
        String expected = "source1 --label2--> destination1 {key8=value8}";
        assertEquals(expected, edge.toString());
    }

    @Test
    public void testNullValuesInJsonCreator() {
        assertThrows(IllegalArgumentException.class, () -> new Edge(null, "destination1"));
        assertThrows(IllegalArgumentException.class, () -> new Edge("source1", null));
        assertThrows(IllegalArgumentException.class, () -> new Edge(null, null));
    }
}

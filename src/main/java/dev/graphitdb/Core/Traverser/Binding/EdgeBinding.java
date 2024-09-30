package dev.graphitdb.Core.Traverser.Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dev.graphitdb.Core.Operators.Select.SelectOperator;

/**
 * This class represents an edge binding in a graph traversal.
 * It defines the properties of an edge, including its label, alias,
 * select operators, and direction (outgoing or incoming).
 */
public class EdgeBinding {

    /** The label of the edge */
    private final String label;

    /** The alias for the edge, which can be used in queries */
    private final String alias;

    /** List of select operators applied to this edge */
    private final List<SelectOperator> selectOperators;

    /** Specifies if the edge is outgoing */
    private final boolean isOutgoing;

    /**
     * Private constructor to create an instance of EdgeBinding using the Builder.
     *
     * @param builder The builder instance containing the edge attributes
     */
    private EdgeBinding(Builder builder) {
        this.label = builder.label;
        this.alias = builder.alias;
        this.selectOperators = Collections.unmodifiableList(builder.selectOperators);
        this.isOutgoing = builder.isOutgoing;
    }

    /**
     * Checks if the edge has an alias.
     *
     * @return true if the alias is not null, false otherwise
     */
    public boolean hasAlias() {
        return alias != null;
    }

    /**
     * Returns the alias of the edge, wrapped in an Optional.
     *
     * @return an Optional containing the alias if present, or empty if no alias exists
     */
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
    }

    /**
     * Returns the label of the edge.
     *
     * @return the label of the edge, or null if none is set
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the list of select operators applied to the edge.
     *
     * @return an unmodifiable list of SelectOperator objects
     */
    public List<SelectOperator> getSelectOperators() {
        return selectOperators;
    }

    /**
     * Checks if the edge is anonymous, meaning it has no label and no select operators.
     *
     * @return true if the edge has no label and no select operators, false otherwise
     */
    public boolean isAnonymous() {
        return label == null && selectOperators.isEmpty();
    }

    /**
     * Checks if the edge is outgoing.
     *
     * @return true if the edge is outgoing, false if it is incoming
     */
    public boolean isOutgoing() {
        return isOutgoing;
    }

    /**
     * Returns a string representation of the EdgeBinding object.
     *
     * @return a string containing the label, alias, select operators, and direction of the edge
     */
    @Override
    public String toString() {
        return "EdgeBinding{" +
                "label='" + label + '\'' +
                ", alias='" + alias + '\'' +
                ", selectOperators=" + selectOperators +
                ", isOutgoing=" + isOutgoing +
                '}';
    }

    /**
     * Checks if this EdgeBinding is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdgeBinding)) return false;
        EdgeBinding that = (EdgeBinding) o;
        return isOutgoing == that.isOutgoing &&
                Objects.equals(label, that.label) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(selectOperators, that.selectOperators);
    }

    /**
     * Generates a hash code for the EdgeBinding object based on its properties.
     *
     * @return the hash code for the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(label, alias, selectOperators, isOutgoing);
    }

    /**
     * Builder class for constructing EdgeBinding instances.
     */
    public static class Builder {
        private String label = null;
        private String alias = null;
        private List<SelectOperator> selectOperators = new ArrayList<>();
        private final boolean isOutgoing;

        /**
         * Constructor for the Builder class.
         *
         * @param isOutgoing A boolean specifying whether the edge is outgoing
         */
        public Builder(boolean isOutgoing) {
            this.isOutgoing = isOutgoing;
        }

        /**
         * Sets the label for the edge.
         *
         * @param label The label to assign to the edge
         * @return the current Builder instance for method chaining
         * @throws IllegalArgumentException if the label is null or empty
         */
        public Builder setLabel(String label) {
            if (label == null || label.trim().isEmpty()) {
                throw new IllegalArgumentException("Label cannot be null or empty");
            }
            this.label = label;
            return this;
        }

        /**
         * Sets the alias for the edge.
         *
         * @param alias The alias to assign to the edge
         * @return the current Builder instance for method chaining
         */
        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        /**
         * Sets the list of select operators for the edge.
         *
         * @param selectOperators The list of select operators to assign
         * @return the current Builder instance for method chaining
         */
        public Builder setSelectOperators(List<SelectOperator> selectOperators) {
            if (selectOperators != null) {
                this.selectOperators = new ArrayList<>(selectOperators);
            }
            return this;
        }

        /**
         * Builds the EdgeBinding instance with the provided properties.
         *
         * @return a new EdgeBinding object
         */
        public EdgeBinding build() {
            return new EdgeBinding(this);
        }
    }
}

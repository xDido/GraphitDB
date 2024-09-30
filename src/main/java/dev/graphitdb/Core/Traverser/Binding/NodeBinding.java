package dev.graphitdb.Core.Traverser.Binding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dev.graphitdb.Core.Operators.Select.SelectOperator;

/**
 * Represents a binding for a node with specific conditions (select operators),
 * label, alias, and ID.
 */
public class NodeBinding {
    private String id;
    private String label;
    private List<SelectOperator> selectOperators;
    private String alias;

    /**
     * Private constructor for NodeBinding.
     * Use the Builder to create instances of NodeBinding.
     *
     * @param builder The builder used to construct this object.
     */
    private NodeBinding(Builder builder) {
        this.id = builder.id;
        this.label = builder.label;
        this.selectOperators = builder.selectOperators;
        this.alias = builder.alias;
    }

    /**
     * Checks if the node has an alias.
     *
     * @return true if alias exists, false otherwise.
     */
    public boolean hasAlias() {
        return alias != null;
    }

    /**
     * Gets the alias for the node.
     *
     * @return the alias string.
     */
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
    }

    /**
     * Gets the label for the node.
     *
     * @return the label string.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the node ID.
     *
     * @return the ID string.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the list of select operators applied to this node.
     *
     * @return the list of SelectOperator objects.
     */
    public List<SelectOperator> getSelectOperators() {
        return selectOperators;
    }

    /**
     * Checks if the node is anonymous, meaning it has no ID, label, or select operators.
     *
     * @return true if the node is anonymous, false otherwise.
     */
    public boolean isAnonymous() {
        return id == null && label == null && selectOperators == null;
    }

    /**
     * Checks if the node has an ID.
     *
     * @return true if the node has an ID, false otherwise.
     */
    public boolean hasId() {
        return id != null;
    }

    /**
     * Builder class for constructing NodeBinding objects.
     */
    public static class Builder {
        private String id = null;
        private String label = null;
        private List<SelectOperator> selectOperators = new ArrayList<>();
        private String alias = null;

        /**
         * Sets the node ID.
         *
         * @param id The node ID.
         * @return The current Builder instance.
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the node label.
         *
         * @param label The node label.
         * @return The current Builder instance.
         */
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        /**
         * Sets the list of select operators to be applied to this node.
         *
         * @param selectOperators The list of SelectOperator objects.
         * @return The current Builder instance.
         */
        public Builder setSelectOperators(List<SelectOperator> selectOperators) {
            this.selectOperators = selectOperators;
            return this;
        }

        /**
         * Sets the alias for the node.
         *
         * @param alias The node alias.
         * @return The current Builder instance.
         */
        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        /**
         * Builds and returns a new NodeBinding instance.
         *
         * @return The constructed NodeBinding object.
         */
        public NodeBinding build() {
            return new NodeBinding(this);
        }
    }
}

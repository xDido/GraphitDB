package dev.graphitdb.Core.Operators.Select;

import dev.graphitdb.Core.DataStructure.Node.LocalNodeService;
import dev.graphitdb.Redis.Indices.RedisIndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for handling different types of SelectOperators.
 */
@Component
public class SelectOperatorManager {

    @Autowired
    private RedisIndexManager redisIndexManager;

    @Autowired
    private LocalNodeService nodeService;

    private Map<String, SelectOperator> operatorMap;

    /**
     * Initializes the operator map with available SelectOperators.
     */
    @PostConstruct
    public void init() {
        operatorMap = new HashMap<>();
        operatorMap.put("=", new EqualsSelectOperator(redisIndexManager, nodeService));
    }

    /**
     * Retrieves a SelectOperator based on the operator name, field name, and field value.
     *
     * @param operatorName the name of the operator (e.g. "=").
     * @param fieldName    the name of the field.
     * @param fieldValue   the value of the field.
     * @return the configured SelectOperator.
     * @throws IllegalArgumentException if the operator is not supported.
     */
    public SelectOperator getSelectOperator(String operatorName, String fieldName, String fieldValue) {
        SelectOperator operator = operatorMap.get(operatorName);
        if (operator != null) {
            operator.build(fieldName, fieldValue);
            return operator;
        }
        throw new IllegalArgumentException("Unsupported operator: " + operatorName);
    }
}

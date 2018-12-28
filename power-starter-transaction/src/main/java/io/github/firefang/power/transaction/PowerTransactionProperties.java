package io.github.firefang.power.transaction;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xinufo
 *
 */
@ConfigurationProperties(prefix = PowerTransactionProperties.PREFIX)
public class PowerTransactionProperties {
    public static final String PREFIX = "power.transaction";
    public static final String SWITCH = PREFIX + ".enabled";

    private boolean enabled;
    /**
     * 只读事务方法名，可以使用通配符(*)
     */
    private String[] readOnlyTransactionAttributes = {};
    /**
     * 传播事务方法名，可以使用通配符(*)
     */
    private String[] requiredTransactionAttributes = {};
    /**
     * 拦截事务Bean的名称
     */
    private String[] txBeanNames = {};

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getReadOnlyTransactionAttributes() {
        return readOnlyTransactionAttributes;
    }

    public void setReadOnlyTransactionAttributes(String[] readOnlyTransactionAttributes) {
        this.readOnlyTransactionAttributes = readOnlyTransactionAttributes;
    }

    public String[] getRequiredTransactionAttributes() {
        return requiredTransactionAttributes;
    }

    public void setRequiredTransactionAttributes(String[] requiredTransactionAttributes) {
        this.requiredTransactionAttributes = requiredTransactionAttributes;
    }

    public String[] getTxBeanNames() {
        return txBeanNames;
    }

    public void setTxBeanNames(String[] txBeanNames) {
        this.txBeanNames = txBeanNames;
    }

}

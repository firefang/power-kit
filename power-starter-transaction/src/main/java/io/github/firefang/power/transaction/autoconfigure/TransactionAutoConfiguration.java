package io.github.firefang.power.transaction.autoconfigure;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import io.github.firefang.power.transaction.PowerTransactionProperties;

/**
 * Auto-configuration for transaction
 * 
 * @author xinufo
 *
 */
@Configuration
@EnableConfigurationProperties(PowerTransactionProperties.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({ DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
@ConditionalOnProperty(value = PowerTransactionProperties.SWITCH, matchIfMissing = true)
public class TransactionAutoConfiguration {
    private static final String TX_INTERCEPTOR_NAME = "powerTxInterceptor";
    private static final String[] DEFAULT_TX_BEAN_NAMES = { "*Service", "*ServiceImpl" };
    // @formatter:off
    private static final String[] DEFAULT_REQUIRED_METHOD_RULE_TX_ATTRIBUTES = {
            "add*",
            "save*",
            "insert*",
            "create*",
            "delete*",
            "remove*",
            "update*",
            "edit*",
            "batch*"
    };
    private static final String[] DEFAULT_READ_ONLY_METHOD_RULE_TX_ATTRIBUTES = {
            "select*",
            "find*",
            "count*",
            "list*",
            "query*",
            "get*",
            "check*",
            "*"
    };
    // @formatter:on

    private final PowerTransactionProperties properties;

    public TransactionAutoConfiguration(PowerTransactionProperties properties) {
        this.properties = properties;
    }

    @Bean(TX_INTERCEPTOR_NAME)
    public TransactionInterceptor powerTransactionInterceptor(PlatformTransactionManager manager) {
        NameMatchTransactionAttributeSource nta = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute readOnly = readOnlyTransactionRule();
        RuleBasedTransactionAttribute required = transactionRule(TransactionDefinition.PROPAGATION_REQUIRED);

        // 默认配置
        for (String methodName : DEFAULT_READ_ONLY_METHOD_RULE_TX_ATTRIBUTES) {
            nta.addTransactionalMethod(methodName, readOnly);
        }
        for (String methodName : DEFAULT_REQUIRED_METHOD_RULE_TX_ATTRIBUTES) {
            nta.addTransactionalMethod(methodName, required);
        }

        // 自定义配置
        for (String methodName : properties.getReadOnlyTransactionAttributes()) {
            nta.addTransactionalMethod(methodName, readOnly);
        }
        for (String methodName : properties.getRequiredTransactionAttributes()) {
            nta.addTransactionalMethod(methodName, required);
        }
        return new TransactionInterceptor(manager, nta);
    }

    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator txAutoProxy = new BeanNameAutoProxyCreator();
        txAutoProxy.setInterceptorNames(TX_INTERCEPTOR_NAME);

        String[] customNames = properties.getTxBeanNames();
        if (customNames.length == 0) {
            txAutoProxy.setBeanNames(DEFAULT_TX_BEAN_NAMES);
        } else {
            String[] names = new String[DEFAULT_TX_BEAN_NAMES.length + customNames.length];
            System.arraycopy(DEFAULT_TX_BEAN_NAMES, 0, names, 0, DEFAULT_TX_BEAN_NAMES.length);
            System.arraycopy(customNames, 0, names, DEFAULT_TX_BEAN_NAMES.length, customNames.length);
            txAutoProxy.setBeanNames(names);
        }

        txAutoProxy.setProxyTargetClass(true);
        return txAutoProxy;
    }

    private RuleBasedTransactionAttribute readOnlyTransactionRule() {
        RuleBasedTransactionAttribute rta = new RuleBasedTransactionAttribute();
        rta.setReadOnly(true);
        return rta;
    }

    private RuleBasedTransactionAttribute transactionRule(int propagationBehavior) {
        RuleBasedTransactionAttribute rta = new RuleBasedTransactionAttribute();
        rta.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Throwable.class)));
        rta.setPropagationBehavior(propagationBehavior);
        rta.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
        rta.setIsolationLevel(Isolation.DEFAULT.value());
        return rta;
    }

}

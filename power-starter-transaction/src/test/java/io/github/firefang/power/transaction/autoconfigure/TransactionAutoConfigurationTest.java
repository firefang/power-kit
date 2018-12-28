package io.github.firefang.power.transaction.autoconfigure;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.firefang.power.transaction.test.PowerTxTestBean;
import io.github.firefang.power.transaction.test.PowerTxTestConfiguration;
import io.github.firefang.power.transaction.test.PowerTxTestService;
import io.github.firefang.power.transaction.test.PowerTxTestServiceImpl;
import io.github.firefang.power.transaction.test.PowerTxTestSrv;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerTxTestConfiguration.class)
public class TransactionAutoConfigurationTest {
    @Autowired
    private PowerTxTestService s1;

    @Autowired
    private PowerTxTestServiceImpl s2;

    @Autowired
    private PowerTxTestSrv s3;

    @Autowired
    private PowerTxTestBean s4;

    @Test
    public void beanProxy_Success() {
        assertTrue(s1.getClass().getName().contains("EnhancerBySpringCGLIB"));
        assertTrue(s2.getClass().getName().contains("EnhancerBySpringCGLIB"));
        assertTrue(s3.getClass().getName().contains("EnhancerBySpringCGLIB"));
        assertFalse(s4.getClass().getName().contains("EnhancerBySpringCGLIB"));
    }

}

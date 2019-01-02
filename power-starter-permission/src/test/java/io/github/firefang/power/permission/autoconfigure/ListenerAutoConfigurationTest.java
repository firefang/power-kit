package io.github.firefang.power.permission.autoconfigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockingDetails;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockingDetails;
import org.mockito.invocation.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.firefang.power.permission.serialize.IPermissionService;
import io.github.firefang.power.permission.serialize.PermissionDO;
import io.github.firefang.power.permission.test.PowerPermissionConfiguration;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerPermissionConfiguration.class)
public class ListenerAutoConfigurationTest {
    @Autowired
    private IPermissionService permSrv;

    @Test
    public void test() {
        List<String> names = Arrays.asList("test", "testId", "testExp");
        MockingDetails details = mockingDetails(permSrv);
        List<PermissionDO> ps = null;
        boolean invoked = false;
        for (Invocation i : details.getInvocations()) {
            if ("upsertBatch".equals(i.getMethod().getName())) {
                ps = i.getArgument(0);
                invoked = true;
                break;
            }
        }
        assertTrue(invoked);
        assertEquals(3, ps.size());
        for (PermissionDO p : ps) {
            assertTrue(names.contains(p.getName()));
            assertEquals("test", p.getGroup());
        }
    }

}

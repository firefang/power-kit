package io.github.firefang.power.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.firefang.power.test.PowerMybatisTestConfiguration;
import io.github.firefang.power.test.PowerMybatisTestEntity;
import io.github.firefang.power.test.mapper.IPowerMybatisTestMapper;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerMybatisTestConfiguration.class)
public class JsonTypeHandlerTest {
    @Autowired
    private IPowerMybatisTestMapper mapper;

    @Test
    public void test() {
        Map<String, Object> props = new HashMap<>();
        props.put("type", "form");
        Map<String, String> children = new HashMap<>();
        children.put("a", "a");
        props.put("content", children);

        PowerMybatisTestEntity e = new PowerMybatisTestEntity();
        e.setId(1);
        e.setProps(props);
        mapper.add(e);

        PowerMybatisTestEntity result = mapper.findById(1);
        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result.getId());
        assertEquals(props, result.getProps());
    }

}

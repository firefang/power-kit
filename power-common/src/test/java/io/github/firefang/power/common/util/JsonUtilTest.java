package io.github.firefang.power.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author xinufo
 *
 */
public class JsonUtilTest {

    @Test
    public void stringify_Null() {
        String json = JsonUtil.stringify(null);
        assertEquals("", json);
    }

    @Test
    public void stringify_NotNull() {
        String json = JsonUtil.stringify(new PowerCommonTestEntity());
        assertEquals("{\"id\":0,\"name\":null}", json);
    }

    @Test
    public void parse_Null() {
        PowerCommonTestEntity obj = JsonUtil.parse(null, PowerCommonTestEntity.class);
        assertNull(obj);
    }

    @Test
    public void parse_NotNull() {
        PowerCommonTestEntity obj = JsonUtil.parse("{\"id\": 2, \"name\": \"name\"}", PowerCommonTestEntity.class);
        assertNotNull(obj);
        assertEquals(2, obj.getId());
        assertEquals("name", obj.getName());
    }

    public static class PowerCommonTestEntity {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}

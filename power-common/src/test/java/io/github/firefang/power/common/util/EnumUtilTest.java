package io.github.firefang.power.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

/**
 * @author xinufo
 *
 */
public class EnumUtilTest {

    @Test
    public void fromString_Success() {
        TestEnum type = EnumUtil.fromString(TestEnum.class, "a");
        assertEquals(TestEnum.A, type);
    }

    @Test
    public void fromString_Strict_Null() {
        TestEnum type = EnumUtil.fromString(TestEnum.class, "a", true);
        assertNull(type);
    }

    @Test
    public void fromStringOptional_Success() {
        Optional<TestEnum> type = EnumUtil.fromStringOptional(TestEnum.class, "b");
        assertNotNull(type);
        assertTrue(type.isPresent());
    }

    @Test
    public void fromStringOptional_Strict_Null() {
        Optional<TestEnum> type = EnumUtil.fromStringOptional(TestEnum.class, "b", true);
        assertNotNull(type);
        assertFalse(type.isPresent());
    }

    private static enum TestEnum {
        A, B;
    }

}

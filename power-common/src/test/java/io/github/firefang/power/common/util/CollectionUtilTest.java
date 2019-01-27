package io.github.firefang.power.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * @author xinufo
 *
 */
public class CollectionUtilTest {

    @Test
    public void isEmpty_Collection_Null_True() {
        Collection<?> c = null;
        assertTrue(CollectionUtil.isEmpty(c));
    }

    @Test
    public void isEmpty_Collection_NotNull_False() {
        Collection<?> c = Collections.singletonList("test");
        assertFalse(CollectionUtil.isEmpty(c));
    }

    @Test
    public void isEmpty_Map_Null_True() {
        Map<?, ?> m = null;
        assertTrue(CollectionUtil.isEmpty(m));
    }

    @Test
    public void isEmpty_Map_NotNull_False() {
        Map<?, ?> m = Collections.singletonMap("test", "test");
        assertFalse(CollectionUtil.isEmpty(m));
    }

    @Test
    public void mapSize_Success() {
        int size = CollectionUtil.mapSize(10);
        assertEquals(14, size);
    }

    @Test
    public void mapSize_Factor_Success() {
        int size = CollectionUtil.mapSize(10, 1F);
        assertEquals(11, size);
    }

}

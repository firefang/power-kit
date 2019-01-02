package io.github.firefang.power.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author xinufo
 *
 */
public class StringUtilTest {

    @Test
    public void hasLength_Null_False() {
        assertFalse(StringUtil.hasLength(null));
    }

    @Test
    public void hasLength_EmptyStr_False() {
        assertFalse(StringUtil.hasLength(""));
    }

    @Test
    public void hasLength_NotEmptyStr_True() {
        assertTrue(StringUtil.hasLength("test"));
    }

}

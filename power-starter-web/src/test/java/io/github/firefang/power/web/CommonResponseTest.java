package io.github.firefang.power.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.github.firefang.power.web.CommonResponse;

/**
 * @author xinufo
 *
 */
public class CommonResponseTest {

    @Test
    public void success() {
        CommonResponse<String> cr = CommonResponse.success();
        assertNotNull(cr);
        assertEquals(CommonResponse.SUCCESS, cr.getCode());
        assertEquals(CommonResponse.MSG_SUCCESS, cr.getMessage());
        assertNull(cr.getData());

        CommonResponse<String> cr2 = cr.data("test");
        assertTrue(cr == cr2);
        assertEquals("test", cr.getData());
    }

    @Test
    public void success_msg() {
        CommonResponse<String> cr = CommonResponse.success("test");
        assertNotNull(cr);
        assertEquals(CommonResponse.SUCCESS, cr.getCode());
        assertEquals("test", cr.getMessage());
        assertNull(cr.getData());
    }

    @Test
    public void fail() {
        CommonResponse<String> cr = CommonResponse.fail(CommonResponse.BAD_REQUEST, "test");
        assertNotNull(cr);
        assertEquals(CommonResponse.BAD_REQUEST, cr.getCode());
        assertEquals("test", cr.getMessage());
        assertNull(cr.getData());
    }

}

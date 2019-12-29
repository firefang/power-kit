package io.github.firefang.power.permission.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.firefang.power.permission.IPermissionChecker;
import io.github.firefang.power.permission.UserInfo;
import io.github.firefang.power.permission.test.PowerPermissionConfiguration;
import io.github.firefang.power.permission.test.PowerPermissionTestBean;
import io.github.firefang.power.permission.test.PowerPermissionTestController;
import io.github.firefang.power.permission.test.PowerPermissionTestController2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerPermissionConfiguration.class)
public class PermissionAspectTest {
    @Autowired
    private IPermissionChecker checker;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PowerPermissionTestBean bean;

    @Autowired
    private PowerPermissionTestController controller;

    @Autowired
    private PowerPermissionTestController2 controller2;

    private MockMvc mvc;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(checker);
    }

    @Test
    public void proxy() {
        assertTrue(AopUtils.isAopProxy(controller));
    }

    @Test
    public void checkPermission_NoUserInfo_Forbidden() throws Exception {
        mvc.perform(get("/check")).andExpect(status().isForbidden());
    }

    @Test
    public void checkPerm_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        Map<String, Object> params = Collections.singletonMap("id", 1);
        when(checker.verticalCheck(eq("test"), eq(info), any(), eq(params))).thenReturn(true);
        when(checker.horizontalCheck(eq("test"), eq(info), any(), eq(params))).thenReturn(true);
        mvc.perform(get("/check")).andExpect(status().isOk()).andExpect(content().string("a"));
    }

    @Test
    public void checkPerm_Params_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        Map<String, Object> params = Collections.singletonMap("id", 1);
        when(checker.verticalCheck(eq("testId"), eq(info), eq(params), any())).thenReturn(true);
        when(checker.horizontalCheck(eq("testId"), eq(info), eq(params), any())).thenReturn(true);
        mvc.perform(get("/checkId").param("id", "1")).andExpect(status().isOk()).andExpect(content().string("a"));
    }

    @Test
    public void checkPerm_ParamsExp_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        Map<String, Object> params = Collections.singletonMap("id", 1);
        when(checker.verticalCheck(eq("testExp"), eq(info), eq(params), any())).thenReturn(true);
        when(checker.horizontalCheck(eq("testExp"), eq(info), eq(params), any())).thenReturn(true);
        mvc.perform(
                post("/checkExp").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"id\":1, \"name\":\"a\"}"))
                .andExpect(status().isOk()).andExpect(content().string("a"));
    }

    @Test
    public void checkPermission_VerticalException_Forbidden() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        when(checker.verticalCheck(eq("testVertical"), eq(info), any(), any())).thenThrow(RuntimeException.class);
        mvc.perform(get("/verticalCheck")).andExpect(status().isForbidden())
                .andExpect(content().json("{'code':403,'message':'检查权限失败','data':null}", true));
        verify(checker).verticalCheck(eq("testVertical"), eq(info), any(), any());
    }

    @Test
    public void checkPermission_VerticalFalse_Forbidden() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        when(checker.verticalCheck(eq("testVertical"), eq(info), any(), any())).thenReturn(false);
        mvc.perform(get("/verticalCheck")).andExpect(status().isForbidden())
                .andExpect(content().json("{'code':403,'message':'无访问权限','data':null}", true));
        verify(checker).verticalCheck(eq("testVertical"), eq(info), any(), any());
    }

    @Test
    public void checkPermission_HorizontalException_Forbidden() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        when(checker.verticalCheck(eq("testVertical"), eq(info), any(), any())).thenReturn(true);
        when(checker.horizontalCheck(eq("testHorizontal"), eq(info), any(), any())).thenThrow(RuntimeException.class);
        mvc.perform(get("/horizontalCheck")).andExpect(status().isForbidden())
                .andExpect(content().json("{'code':403,'message':'检查权限失败','data':null}", true));
        verify(checker).horizontalCheck(eq("testHorizontal"), eq(info), any(), any());
    }

    @Test
    public void checkPermission_HorizontalFalse_Forbidden() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        when(checker.verticalCheck(eq("testVertical"), eq(info), any(), any())).thenReturn(true);
        when(checker.horizontalCheck(eq("testHorizontal"), eq(info), any(), any())).thenReturn(false);
        mvc.perform(get("/horizontalCheck")).andExpect(status().isForbidden())
                .andExpect(content().json("{'code':403,'message':'无访问权限','data':null}", true));
        verify(checker).horizontalCheck(eq("testHorizontal"), eq(info), any(), any());
    }

    @Test
    public void checkPermission_NoCheck_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(any())).thenReturn(info);
        mvc.perform(get("/noCheck")).andExpect(status().isOk());
    }

    @Test
    public void methodInControllerWithoutPermissionAnno_NotProxy() throws Exception {
        mvc.perform(get("/c")).andExpect(status().isOk()).andExpect(content().string("c"));
        verifyNoMoreInteractions(checker);
    }

    @Test
    public void controllerWithoutPermissionGroupAnno_NotProxy() {
        assertEquals(PowerPermissionTestController2.class, controller2.getClass());
    }

    @Test
    public void methodNotInControllerWithPermissionAnno_NotProxy() {
        assertEquals(PowerPermissionTestBean.class, bean.getClass());
        bean.b();
        verifyNoMoreInteractions(checker);
    }

}

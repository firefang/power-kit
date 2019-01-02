package io.github.firefang.power.permission.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
    public void checkPermission_NoUserInfo_Forbidden() throws Exception {
        mvc.perform(get("/check")).andExpect(status().isForbidden());
    }

    @Test
    public void proxy() {
        assertTrue(AopUtils.isAopProxy(controller));
    }

    @Test
    public void checkPermission_NoPermission_Forbidden() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(Mockito.any())).thenReturn(info);
        when(checker.canAccess("test", 1, 1, null)).thenReturn(false);

        mvc.perform(get("/check")).andExpect(status().isForbidden());
        verify(checker).canAccess("test", 1, 1, null);
    }

    @Test
    public void checkPermission_Exception_Forbidden() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(Mockito.any())).thenReturn(info);
        when(checker.canAccess("test", 1, 1, null)).thenThrow(RuntimeException.class);
        mvc.perform(get("/check")).andExpect(status().isForbidden());
        verify(checker).canAccess("test", 1, 1, null);
    }

    @Test
    public void checkPerm_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(Mockito.any())).thenReturn(info);
        when(checker.canAccess("test", 1, 1, null)).thenReturn(true);
        mvc.perform(get("/check")).andExpect(status().isOk()).andExpect(content().string("a"));
    }

    @Test
    public void checkPerm_EntityId_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(Mockito.any())).thenReturn(info);
        when(checker.canAccess("testId", 1, 1, 1)).thenReturn(true);
        mvc.perform(get("/checkId").param("id", "1")).andExpect(status().isOk()).andExpect(content().string("a"));
    }

    @Test
    public void checkPerm_EntityIdExpression_Success() throws Exception {
        UserInfo info = new UserInfo();
        info.setUserId(1);
        info.setRoleId(1);
        when(checker.getUserInfoFromRequest(Mockito.any())).thenReturn(info);
        when(checker.canAccess("testExp", 1, 1, 1)).thenReturn(true);
        mvc.perform(
                post("/checkExp").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"id\":1, \"name\":\"a\"}"))
                .andExpect(status().isOk()).andExpect(content().string("a"));
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

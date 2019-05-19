package io.github.firefang.power.login.autoconfigure;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.IAuthService;
import io.github.firefang.power.login.LoginProperties;
import io.github.firefang.power.login.test.PowerLoginTestConfiguration;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerLoginTestConfiguration.class)
@ActiveProfiles("token")
@DirtiesContext
public class LoginAutoConfigurationTokenTest {
    private static final String KEY = LoginProperties.DEFAULT_TOKEN_KEY;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private IAuthService srv;

    private MockMvc mvc;

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(srv);
    }

    @Test
    public void login_Success() throws Exception {
        when(srv.login(Mockito.eq("test"), Mockito.eq("test"), Mockito.any())).thenReturn("token");
        mvc.perform(post("/auth/login").param("username", "test").param("password", "test")).andExpect(status().isOk())
                .andExpect(content().json("{'code':200, 'message':'ok', 'data':'token'}"));
        verify(srv).login(Mockito.eq("test"), Mockito.eq("test"), Mockito.any());
        verifyNoMoreInteractions(srv);
    }

    @Test
    public void logout_Success() throws Exception {
        mvc.perform(post("/auth/logout").header(KEY, "token")).andExpect(status().isOk())
                .andExpect(content().json("{'code':200, 'message':'ok', 'data':null}"));
        verify(srv).auth("token");
        verify(srv).logout(Mockito.any());
    }

    @Test
    public void loginRequest_Success() throws Exception {
        mvc.perform(get("/test").header(KEY, "token")).andExpect(status().isOk()).andExpect(content().string("ok"));
        verify(srv).auth("token");
        verifyNoMoreInteractions(srv);
    }

    @Test
    public void noLoginRequest_Fail() throws Exception {
        doThrow(new UnAuthorizedException()).when(srv).auth("token");
        mvc.perform(get("/test").header(KEY, "token")).andExpect(status().isUnauthorized())
                .andExpect(content().json("{'code':401, 'message':null, 'data':null}"));
    }

    @Test
    public void noFound_Success() throws Exception {
        mvc.perform(get("/404")).andExpect(status().isNotFound());
        verifyZeroInteractions(srv);
    }

}

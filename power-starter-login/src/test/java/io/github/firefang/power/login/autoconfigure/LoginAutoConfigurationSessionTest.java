package io.github.firefang.power.login.autoconfigure;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.LoginProperties;
import io.github.firefang.power.login.session.ISessionAuthService;
import io.github.firefang.power.login.test.PowerLoginTestConfiguration;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerLoginTestConfiguration.class)
@ActiveProfiles("session")
@DirtiesContext
public class LoginAutoConfigurationSessionTest {
    private static final String KEY = LoginProperties.DEFAULT_SESSION_KEY;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ISessionAuthService srv;

    private MockMvc mvc;

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        reset(srv);
    }

    @Test
    public void login_Success() throws Exception {
        mvc.perform(post("/auth/login").param("username", "test").param("password", "test")).andExpect(status().isOk())
                .andExpect(content().json("{'code':200, 'message':'ok', 'data':null}"));
        verify(srv).login("test", "test");
    }

    @Test
    public void logout_Success() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(KEY, "test");
        mvc.perform(post("/auth/logout").session(session)).andExpect(status().isOk())
                .andExpect(content().json("{'code':200, 'message':'ok', 'data':null}"));
        verify(srv).logout("test");
    }

    @Test
    public void loginRequest_Success() throws Exception {
        mvc.perform(get("/test")).andExpect(status().isOk()).andExpect(content().string("ok"));
        verify(srv).auth(KEY);
    }

    @Test
    public void noLoginRequest_Fail() throws Exception {
        doThrow(new UnAuthorizedException()).when(srv).auth(KEY);
        mvc.perform(get("/test")).andExpect(status().isUnauthorized())
                .andExpect(content().json("{'code':600, 'message':null, 'data':null}"));
    }

}

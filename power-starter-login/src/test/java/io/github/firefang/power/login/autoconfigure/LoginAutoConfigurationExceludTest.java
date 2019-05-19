package io.github.firefang.power.login.autoconfigure;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.firefang.power.login.IAuthService;
import io.github.firefang.power.login.test.PowerLoginTestConfiguration;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerLoginTestConfiguration.class)
@ActiveProfiles("exclude")
@DirtiesContext
public class LoginAutoConfigurationExceludTest {

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
    public void exclude_Success() throws Exception {
        mvc.perform(get("/excludes/test")).andExpect(status().isOk());
        verifyZeroInteractions(srv);
    }

}

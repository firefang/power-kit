package io.github.firefang.power.web.util;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.firefang.power.web.test.PowerWebTestConfiguration;
import io.github.firefang.power.web.util.CurrentRequestUtil;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerWebTestConfiguration.class)
public class CurrentRequestUtilTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getCurrentRequest_Fail() throws Exception {
        final String[] holder = new String[1];
        Thread th = new Thread(() -> {
            try {
                CurrentRequestUtil.getCurrentRequest();
            } catch (IllegalStateException e) {
                holder[0] = e.getMessage();
            }
        });
        th.start();
        th.join();
        assertEquals("Can't get current request", holder[0]);
    }

    @Test
    public void getHeader_Success() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(3);
        CyclicBarrier cb = new CyclicBarrier(3);
        Map<String, String> map = new ConcurrentHashMap<>(4);
        AtomicInteger ai = new AtomicInteger();
        for (int i = 0; i < 3; ++i) {
            es.execute(() -> {
                try {
                    String id = String.valueOf(ai.incrementAndGet());
                    cb.await();
                    MvcResult result = mvc.perform(get("/header").header("test", id)).andReturn();
                    map.put(id, result.getResponse().getContentAsString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        es.shutdown();
        es.awaitTermination(3, TimeUnit.SECONDS);
        assertEquals("1", map.get("1"));
        assertEquals("2", map.get("2"));
        assertEquals("3", map.get("3"));
    }

    @Test
    public void getSessionAttribute_Success() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(3);
        CyclicBarrier cb = new CyclicBarrier(3);
        Map<String, String> map = new ConcurrentHashMap<>(4);
        AtomicInteger ai = new AtomicInteger();
        for (int i = 0; i < 3; ++i) {
            es.execute(() -> {
                try {
                    String id = String.valueOf(ai.incrementAndGet());
                    cb.await();
                    MvcResult result = mvc.perform(get("/session").param("test", id)).andReturn();
                    map.put(id, result.getResponse().getContentAsString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        es.shutdown();
        es.awaitTermination(3, TimeUnit.SECONDS);
        assertEquals("1", map.get("1"));
        assertEquals("2", map.get("2"));
        assertEquals("3", map.get("3"));
    }

}

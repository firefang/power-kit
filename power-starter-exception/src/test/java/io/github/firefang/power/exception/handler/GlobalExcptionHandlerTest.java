package io.github.firefang.power.exception.handler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.github.firefang.power.exception.handler.test.PowerExceptionTestConfiguration;

/**
 * @author xinufo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PowerExceptionTestConfiguration.class)
public class GlobalExcptionHandlerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void unAuth() throws Exception {
        mvc.perform(get("/unauth")).andExpect(status().isUnauthorized())
                .andExpect(content().json("{'code':401,'message':null,'data':null}", true));
    }

    @Test
    public void noPerm() throws Exception {
        mvc.perform(get("/noperm")).andExpect(status().isForbidden());
    }

    @Test
    public void business() throws Exception {
        mvc.perform(get("/business")).andExpect(status().isOk())
                .andExpect(content().json("{'code':600,'message':'操作失败, test','data':null}", true));
    }

    @Test
    public void badReq() throws Exception {
        mvc.perform(get("/badreq")).andExpect(status().isBadRequest())
                .andExpect(content().json("{'code':400,'message':'test','data':null}", true));
    }

    @Test
    public void ex() throws Exception {
        mvc.perform(get("/ex")).andExpect(status().isInternalServerError())
                .andExpect(content().json("{'code':500,'message':'未知异常','data':null}", true));
    }

    @Test
    public void missPathVar() throws Exception {
        mvc.perform(get("/misspathvar/1")).andExpect(status().isInternalServerError())
                .andExpect(content().json("{'code':500,'message':'缺少路径参数: id','data':null}", true));
    }

    @Test
    public void missSerlvetReqParam() throws Exception {
        mvc.perform(get("/missservletreqparam")).andExpect(status().isBadRequest())
                .andExpect(content().json("{'code':400,'message':'缺少请求参数: id','data':null}", true));
    }

    @Test
    public void servletReqBinding() throws Exception {
        mvc.perform(get("/servletreqbinding")).andExpect(status().isBadRequest())
                .andExpect(content().json("{'code':400,'message':'请求绑定异常','data':null}", true));
    }

    @Test
    public void conversionNotSupported() throws Exception {
        mvc.perform(get("/conversionnotsupported")).andExpect(status().isInternalServerError());
    }

    @Test
    public void typeMistake() throws Exception {
        mvc.perform(get("/typemistake").param("id", "a")).andExpect(status().isBadRequest())
                .andExpect(content().json("{'code':400,'message':'参数类型错误，要求类型: Integer','data':null}", true));
    }

    @Test
    public void messageNotWritable() throws Exception {
        mvc.perform(get("/messagenotwritable")).andExpect(status().isInternalServerError());
    }

    @Test
    public void methodArgNotValid() throws Exception {
        mvc.perform(post("/methodargnotvalid").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(content()
                        .json("{'code':400,'message':'参数校验不通过','data':{'name':\"name can't be null\"}}", true));
    }

    @Test
    public void missServletReqPart() throws Exception {
        mvc.perform(post("/missservletreqpart").contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'code':400,'message':'缺少请求参数: file','data':null}", true));
    }

    @Test
    public void bind() throws Exception {
        mvc.perform(get("/bind")).andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.log())
                .andExpect(content().json("{'code':400,'message':'参数校验不通过','data':{'field':'test'}}", true));
    }

}

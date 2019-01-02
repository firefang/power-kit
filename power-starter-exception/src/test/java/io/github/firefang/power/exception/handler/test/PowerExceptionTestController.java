package io.github.firefang.power.exception.handler.test;

import javax.validation.Valid;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.firefang.power.exception.BadRequestException;
import io.github.firefang.power.exception.BusinessException;
import io.github.firefang.power.exception.InternalException;
import io.github.firefang.power.exception.NoPermissionException;
import io.github.firefang.power.exception.UnAuthorizedException;

/**
 * @author xinufo
 *
 */
@RestController
public class PowerExceptionTestController {

    // for GlobalExceptionHandlerTest

    @RequestMapping("/unauth")
    public void unauth() {
        throw new UnAuthorizedException();
    }

    @RequestMapping("/noperm")
    public void noPerm() {
        throw new NoPermissionException();
    }

    @RequestMapping("/business")
    public void business() {
        throw new BusinessException("test", "test");
    }

    @RequestMapping("/badreq")
    public void badReq() {
        throw new BadRequestException("test");
    }

    @RequestMapping("/ex")
    public void ex() throws Exception {
        throw new InternalException("test");
    }

    @RequestMapping("/misspathvar/{id2}")
    public void missPathVar(@PathVariable("id") Integer id) {
    }

    @RequestMapping("/missservletreqparam")
    public void missSerlvetReqParam(@RequestParam("id") Integer id) {
    }

    @RequestMapping("/servletreqbinding")
    public void servletReqBinding() throws ServletRequestBindingException {
        throw new ServletRequestBindingException("test");
    }

    @RequestMapping(value = "/conversionnotsupported")
    public void conversionNotSupported() {
        throw new ConversionNotSupportedException("a", String.class, null);
    }

    @RequestMapping("/typemistake")
    public void typeMistake(@RequestParam("id") Integer id) {
    }

    @RequestMapping("messagenotwritable")
    public void messageNotWritable() {
        throw new HttpMessageNotWritableException("test");
    }

    @RequestMapping("/methodargnotvalid")
    public void methodArgNotValid(@Valid @RequestBody PowerExceptionTestEntity e) {
    }

    @RequestMapping("/missservletreqpart")
    public void missServletReqPart(@RequestParam("file") MultipartFile file) {
    }

    @RequestMapping("/bind")
    public void bind() throws BindException {
        BeanPropertyBindingResult r = new BeanPropertyBindingResult("test", "test");
        r.addError(new FieldError("obj", "field", "test"));
        throw new BindException(r);
    }

}

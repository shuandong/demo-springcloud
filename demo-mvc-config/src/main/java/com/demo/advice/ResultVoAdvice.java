package com.demo.advice;

import com.demo.annotation.NonPackage;
import com.demo.enums.HttpCode;
import com.demo.utils.Assert;
import com.demo.vo.ResultVo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResultVoAdvice implements ResponseBodyAdvice<Object> {


    /**
     * 是否对响应做出处理
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(NonPackage.class)
                || methodParameter.getMethod().isAnnotationPresent(NonPackage.class)) {
            return false;
        }
        return true;
    }

    /**
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(HttpCode.SUCCESS.getCode());
        resultVo.setMessage(HttpCode.SUCCESS.getValue());
        resultVo.setSuccess(true);

        if (Assert.isEmpty(o) || o instanceof ResultVo) {
            resultVo = (ResultVo<Object>) o;
        } else {
            resultVo.setData(o);
        }
        return resultVo;
    }
}

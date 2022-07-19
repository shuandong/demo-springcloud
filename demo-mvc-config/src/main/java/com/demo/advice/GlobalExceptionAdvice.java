package com.demo.advice;


import com.demo.enums.HttpCode;
import com.demo.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shu
 * @describtion 全局异常处理类
 * @date 2022-07-17 20:28
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResultVo<String> globalExceptionHandler(HttpServletRequest httpRequest, Exception e) {

        ResultVo<String> resultVo = new ResultVo<>();
        resultVo.setCode(HttpCode.FAIL.getCode());
        resultVo.setData(e.getMessage());
        resultVo.setSuccess(false);
        resultVo.setMessage(HttpCode.FAIL.getValue());
        log.error("service error!:[{}]", e.getMessage(), e);
        return resultVo;

    }
}

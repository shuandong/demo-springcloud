package com.demo.aspect;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shu
 * @describtion aop 打印controller入参返回值
 * @date 2022-07-17  21:01
 */
@Aspect
public class RequestLogRecordAspect {

    private static final Logger log = LoggerFactory.getLogger(RequestLogRecordAspect.class);

    /**
     * 拦截controller类，日志记录请求参数和输出结果
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("(@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))")
    public Object resultHandler(ProceedingJoinPoint pjp) throws Throwable {
        String id = IdUtil.objectId();
        log.debug(id + ":日志-----------------");
        recordParam(id, pjp);

        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        if (result != null) {
            log.debug(id + ":耗时:" + time + ":输出结果：" + JSON.toJSONString(result));
        } else {
            log.debug(id + ":耗时:" + time + ":输出结果空");
        }

        log.debug(id + ":-----------------日志");
        return result;
    }

    private void recordParam(String id, ProceedingJoinPoint pjp) throws Exception {
        Class<? extends Object> actionClass = pjp.getTarget().getClass();
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        log.debug(id + ":请求类：" + actionClass.getName());
        log.debug(id + ":请求方法：" + ms.getName());
        String[] parameterNames = ms.getParameterNames();
        Object[] args = pjp.getArgs();
        if (ArrayUtils.isNotEmpty(parameterNames)
                && ArrayUtils.isNotEmpty(args)
                && parameterNames.length == args.length) {
            Map<String, Object> ps = new HashMap<>();
            for (int i = 0; i < parameterNames.length; i++) {
                String key = parameterNames[i];
                Object value = args[i];
                if (value instanceof Model ||
                        value instanceof HttpSession
                        || value instanceof HttpServletRequest
                        || value instanceof HttpServletResponse
                        || value instanceof MultipartFile
                        || value instanceof BindingResult) {
                    ps.put(key, value.toString());
                } else {
                    ps.put(key, value);
                }
            }
            log.debug(id + ":传入参数：" + JSON.toJSONString(ps));
        }
    }
}

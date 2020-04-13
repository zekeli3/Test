package com.lkz.blogs01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class logAspect {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.lkz.blogs01.web.*.*(..))")
    public void log(){}
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url=request.getRequestURI();
        String ip=request.getRemoteAddr();
        String classmethed=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        ResultLog resultLog=new ResultLog(url,ip,classmethed,args);

        logger.info("Request: {}",resultLog);

    }
    @After("log()")
    public void doAfter(){
      logger.info("----------after-------");
    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAftetReturn(Object result){
        logger.info("Result:"+result);
    }


    private class ResultLog{
        private String url;

        private String ip;
        private  String classMehted;
        private Object[] args;

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMehted='" + classMehted + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

        public ResultLog(String url,  String ip, String classMehted, Object[] args) {
            this.url = url;

            this.ip = ip;
            this.classMehted = classMehted;
            this.args = args;
        }



    }
}
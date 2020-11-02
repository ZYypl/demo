package com.example.demo.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.OperLog;
import com.example.demo.entity.ExceptionLog;
import com.example.demo.entity.OperationLog;
import com.example.demo.service.ExceptionLogService;
import com.example.demo.service.OperationLogService;
import com.example.demo.util.HttpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * com.example.demo.aop
 *
 * @author ypl
 * @create 2020-11-02 10:08
 */

@Aspect
@Component
public class OperLogAspect {

        @Autowired
        private OperationLogService operationLogServiceImpl;

        @Autowired
        private ExceptionLogService exceptionLogServiceImpl;

        /**
         * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
         */
        @Pointcut("@annotation(com.example.demo.annotation.OperLog)")
        public void operLogPoinCut() {
        }

        /**
         * 设置操作异常切入点记录异常日志 扫描所有controller包下操作
         */
        @Pointcut("execution(* com.example.demo.controller..*.*(..))")
        public void operExceptionLogPoinCut() {
        }

        /**
         * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
         *
         * @param joinPoint 切入点
         * @param keys      返回结果
         */
        @AfterReturning(value = "operLogPoinCut()", returning = "keys")
        public void saveOperLog(JoinPoint joinPoint, Object keys) {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes
                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);

            OperationLog operlog = new OperationLog();
            try {
                operlog.setOperId(UUID.randomUUID().toString()); // 主键ID

                // 从切面织入点处通过反射机制获取织入点处的方法
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                // 获取切入点所在的方法
                Method method = signature.getMethod();
                // 获取操作
                OperLog opLog = method.getAnnotation(OperLog.class);
                if (opLog != null) {
                    String operModul = opLog.operModul();
                    String operType = opLog.operType();
                    String operDesc = opLog.operDesc();
                    operlog.setOperModel(operModul); // 操作模块
                    operlog.setOperType(operType); // 操作类型
                    operlog.setOperDesc(operDesc); // 操作描述
                }
                // 获取请求的类名
                String className = joinPoint.getTarget().getClass().getName();
                // 获取请求的方法名
                String methodName = method.getName();
                methodName = className + "." + methodName;

                operlog.setOperMethod(methodName); // 请求方法

                // 请求的参数
                Map<String, String> rtnMap = converMap(request.getParameterMap());
                // 将参数所在的数组转换成json
                String params = JSON.toJSONString(rtnMap);

                operlog.setOperRequestParam(params); // 请求参数
                operlog.setOperResponseParam(JSON.toJSONString(keys)); // 返回结果
                //operlog.setOperUserId(UserShiroUtil.getCurrentUserLoginName()); // 请求用户ID
                //operlog.setOperUserName(UserShiroUtil.getCurrentUserName()); // 请求用户名称
                operlog.setOperIp(HttpUtil.getIpAddr(request)); // 请求IP
                operlog.setOperUrl(request.getRequestURI()); // 请求URI
                operlog.setOperTime(new Date()); // 创建时间

                operationLogServiceImpl.insert(operlog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
         *
         * @param joinPoint 切入点
         * @param e         异常信息
         */
        @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
        public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
            // 获取RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 从获取RequestAttributes中获取HttpServletRequest的信息
            HttpServletRequest request = (HttpServletRequest) requestAttributes
                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);

            ExceptionLog excepLog = new ExceptionLog();
            try {
                // 从切面织入点处通过反射机制获取织入点处的方法
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                // 获取切入点所在的方法
                Method method = signature.getMethod();
                excepLog.setExcepId(UUID.randomUUID().toString());
                // 获取请求的类名
                String className = joinPoint.getTarget().getClass().getName();
                // 获取请求的方法名
                String methodName = method.getName();
                methodName = className + "." + methodName;
                // 请求的参数
                Map<String, String> rtnMap = converMap(request.getParameterMap());
                // 将参数所在的数组转换成json
                String params = JSON.toJSONString(rtnMap);
                excepLog.setExcepRequestParam(params); // 请求参数
                excepLog.setOperMethod(methodName); // 请求方法名
                excepLog.setExcepName(e.getClass().getName()); // 异常名称
                excepLog.setExcepMessage(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace())); // 异常信息
                //excepLog.setOperUserId(UserShiroUtil.getCurrentUserLoginName()); // 操作员ID
                //excepLog.setOperUserName(UserShiroUtil.getCurrentUserName()); // 操作员名称
                excepLog.setOperUrl(request.getRequestURI()); // 操作URI
                excepLog.setOperIp(HttpUtil.getIpAddr(request)); // 操作员IP
                excepLog.setOperTime(new Date()); // 发生异常时间

                exceptionLogServiceImpl.insert(excepLog);

            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }

        /**
         * 转换request 请求参数
         *
         * @param paramMap request获取的参数数组
         */
        public Map<String, String> converMap(Map<String, String[]> paramMap) {
            Map<String, String> rtnMap = new HashMap<String, String>();
            for (String key : paramMap.keySet()) {
                rtnMap.put(key, paramMap.get(key)[0]);
            }
            return rtnMap;
        }

        /**
         * 转换异常信息为字符串
         *
         * @param exceptionName    异常名称
         * @param exceptionMessage 异常信息
         * @param elements         堆栈信息
         */
        public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
            StringBuffer strbuff = new StringBuffer();
            for (StackTraceElement stet : elements) {
                strbuff.append(stet + "\n");
            }
            String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
            return message;
        }

}

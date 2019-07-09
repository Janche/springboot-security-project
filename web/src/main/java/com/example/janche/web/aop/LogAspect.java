package com.example.janche.web.aop;

import com.example.janche.common.util.IPUtils;
import com.example.janche.common.utils.CoreUtils;
import com.example.janche.log.domain.SysLog;
import com.example.janche.log.service.SysLogService;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 操作日志handler
 */
@Slf4j
@Aspect
@Component
public class LogAspect implements Serializable {

    @Resource
    private SysLogService sysLogService;

    /** 如果Log注解的包名改变,记得这里要更新 */
    @Pointcut("@annotation(com.example.janche.web.aop.Log)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            // e.printStackTrace();
            throw e;
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);
        return result;
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e){
        // 保存异常日志
        if(null != e){
            saveLog(point, 0, e);
        }
    }

    /**
     * 存储log
     */
    private void saveLog(JoinPoint joinPoint, long time) {
        saveLog(joinPoint, time, null);
    }

    /**
     * 存储log
     */
    private void saveLog(JoinPoint joinPoint, long time, Throwable e) {
        LoginUserDTO currentUser = SecurityUtils.getCurrentUser();
        // TODO 是否记录未登录用户的信息
        if(null == currentUser){
            currentUser = LoginUserDTO.builder()
                    .id(0L)
                    .username("未登录用户")
                    .build();
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            int operate = logAnnotation.value();
            String operation = CoreUtils.getOperation(operate);
            sysLog.setOperation(operate);
            // 注解上的模块类型
            int type = logAnnotation.type();
            String module = CoreUtils.getModule(type);
            sysLog.setLogType(type);
            // 注解上的日志描述
            String desc = currentUser.getUsername() + "在 " + module + " 调用了 " + logAnnotation.description() + " 的方法";
            sysLog.setLogDesc(desc);
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setLogMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setLogParams(params);
        }
        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 设置IP地址
        sysLog.setLogIp(IPUtils.getIpAddr(request));
        request.getRemoteAddr();
        sysLog.setLogUser(currentUser.getId());
        sysLog.setLogTime(time);
        sysLog.setCreateTime(new Date());

        // 加入异常的信息
        if(null != e){
            sysLog.setExceptionCode(e.getClass().getName());
            sysLog.setExceptionDetail(e.getMessage());
        }
        // TODO 保存系统日志,入库,这里模拟打出信息即可
        System.out.println(sysLog);
        sysLogService.saveLog(sysLog);
    }

}

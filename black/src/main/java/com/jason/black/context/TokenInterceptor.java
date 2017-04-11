package com.jason.black.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jason.black.annotations.Token;
import com.jason.black.domain.entity.Region;
import com.jason.black.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    private static Cache<String, String> regionCache = CacheBuilder.newBuilder().build();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (o instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) o;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (Objects.nonNull(annotation)) {
                boolean needToken = annotation.get();
                if (needToken) {
                    String token = UUID.randomUUID().toString();
                    httpServletRequest.getSession(true).setAttribute("token", token);
                    httpServletResponse.addHeader("token", token);
                }
                boolean needAuth = annotation.auth();
                if (needAuth) {
                    if (isRepeatSubmit(httpServletRequest)) {
                        String serverToken = (String) httpServletRequest.getSession(true).getAttribute("token");
                        logger.error("请勿重复提交，token:{}", serverToken);
                        throw new ServiceException(200004);
                    }
                    httpServletRequest.getSession(true).removeAttribute("token");
                }
            }
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(true).getAttribute("token");
        if (Objects.isNull(serverToken)) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (Objects.isNull(clinetToken)) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}

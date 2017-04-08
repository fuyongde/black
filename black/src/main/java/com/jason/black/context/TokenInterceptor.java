package com.jason.black.context;

import com.jason.black.annotations.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (o instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) o;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    httpServletRequest.getSession(false).setAttribute("token", UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(httpServletRequest)) {
                        return false;
                    }
                    httpServletRequest.getSession(false).removeAttribute("token");
                }
            }
            return true;
        } else {
            return super.preHandle(httpServletRequest, httpServletResponse, o);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (Objects.isNull(serverToken)) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}

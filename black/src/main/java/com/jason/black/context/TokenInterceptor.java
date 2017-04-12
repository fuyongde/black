package com.jason.black.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jason.black.annotations.Token;
import com.jason.black.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * The type Token interceptor.
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String TOKEN_HEADER = "Token";

    /**
     * The Logger.
     */
    final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    /**
     * 防止重复的token缓存，可以考虑添加超时的限制
     */
    private static Cache<String, String> tokenCache = CacheBuilder.newBuilder().build();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (o instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) o;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (Objects.nonNull(annotation)) {
                boolean needToken = annotation.get();
                //若需要token，则需要把token放入response中
                if (needToken) {
                    String token = UUID.randomUUID().toString();
                    tokenCache.put(token, token);
                    httpServletResponse.addHeader(TOKEN_HEADER, token);
                }

                boolean needAuth = annotation.auth();
                //若需要验证token
                if (needAuth) {
                    String clientToken = httpServletRequest.getHeader(TOKEN_HEADER);
                    //客户端没有传token
                    if (StringUtils.isBlank(clientToken)) {
                        throw new ServiceException(200005);
                    }
                    String serverToken = tokenCache.get(clientToken, () -> "");
                    if (Objects.equals(clientToken, serverToken)) {
                        tokenCache.invalidate(clientToken);
                    } else {
                        //token校验不通过
                        logger.error("请勿重复提交，token:{}", serverToken);
                        tokenCache.invalidate(clientToken);
                        throw new ServiceException(200004);
                    }
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
}

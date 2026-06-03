package com.example.core.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //CORS는 통과
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String validToken = token.substring(7);
            if(!jwtProvider.verifyToken(validToken)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었습니다.");
                return false;
            }
            return true;
        }
        else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효한 접근 권한이 없습니다.");
            return false;
        }
    }
}

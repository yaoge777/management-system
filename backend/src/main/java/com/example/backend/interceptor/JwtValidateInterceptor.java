package com.example.backend.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {

//    @Autowired
//    private JwtUtil ju;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("X-Token");
//        log.debug(request.getRequestURI() + "need verify" + token);
//        if(token != null){
//            try {
//                ju.parseToken(token);
//                log.debug(request.getRequestURI() + "verify passed");
//                return true;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//        log.debug(request.getRequestURI() + "verify failed");
//        response.setContentType("application/json;charset=utf-8");
//        Result<Object> fail = Result.fail(200003, "jwt not valid, login again");
//        response.getWriter().write(JSON.toJSONString(fail));
//        return false;
//    }
}


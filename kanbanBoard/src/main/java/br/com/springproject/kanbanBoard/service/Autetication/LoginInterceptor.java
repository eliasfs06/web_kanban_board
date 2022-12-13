package br.com.springproject.kanbanBoard.service.Autetication;

import br.com.springproject.kanbanBoard.service.CookieService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

    @Component
    public class LoginInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle
                (HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception{
                System.out.println("Pre Handle Method is Calling -");

                if(CookieService.getCookie(request, "userID") != null){
                    return true;
                }
                response.sendRedirect("/login");
                return false;
        }
    }

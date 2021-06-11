package com.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import com.web.Response.BaseResponse;
import com.web.entities.Response;
import com.web.services.UserService;
import org.springframework.http.ResponseEntity;


@WebFilter(urlPatterns = { "/logout", "/get_comment","/set_comment","/del_comment","/edit_comment","/like",

        "/report","/add_post","/get_post","/get_list_post","/edit_post","/delete_post",

        "/search","/change_password","/set_user_info","/get_user_friends",

        "/set_request_friend", "/get_user_info", "get_requested_friend","set_accept_friend" })

    public class APIFilter implements Filter {

        private UserService userService;

        public APIFilter() {
            userService = new UserService();
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            httpRequest.setCharacterEncoding("utf-8");
            httpResponse.setContentType("application/json");
            BaseResponse baseResponse = new BaseResponse();
            Gson gson = new Gson();
            String authToken = httpRequest.getHeader("Authorization");

            System.out.println("authToken = " + authToken);
            String url = httpRequest.getRequestURI();
            System.out.println("url = " + url);
            try {
                if (userService.validateToken(authToken) && userService.getPhoneNumberFromToken(authToken) != null) {

                    chain.doFilter(request, response);
                }else{

                    baseResponse.setCode(Response.CODE_1002);
                    baseResponse.setMessage(Response.MESSAGE_1002);
                    httpResponse.getWriter().print(gson.toJson(baseResponse));
                }
            } catch (IllegalArgumentException e) {

                if(authToken == "") {
                    System.out.println("Exception = "+e.getMessage());
                    baseResponse.setCode(Response.CODE_9998);
                    baseResponse.setMessage(Response.MESSAGE_9998);
                    httpResponse.getWriter().print(gson.toJson(baseResponse));
                }else {
                    baseResponse.setCode(Response.CODE_1002);
                    baseResponse.setMessage(Response.MESSAGE_1002);
                    httpResponse.getWriter().print(gson.toJson(baseResponse));
                }
            }

        }

        @Override
        public void destroy() {
            // TODO Auto-generated method stub

        }
    }


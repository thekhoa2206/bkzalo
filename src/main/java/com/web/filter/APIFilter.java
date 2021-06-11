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


@WebFilter(urlPatterns = { "/logout", "/add_post", "/set_comment","/edit_comment","/del_comment", "/get_post", "/blocks", "/like",
        "/delete_post", "/get_comment", "/get_list_posts", "/report", "/set_accept_friend",

        "/set_request_friend", "/get_user_infor","/change_password",

        "/set_request_friend", "/get_user_info", "/set_request_friend" })

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


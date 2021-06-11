package com.web.conf;

import com.web.filter.APIFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<APIFilter> loggingFilter(){
            FilterRegistrationBean<APIFilter> registrationBean
                    = new FilterRegistrationBean<>();
        registrationBean.setFilter(new APIFilter());
        registrationBean.addUrlPatterns("/logout", "/get_comment","/set_comment","/del_comment","/edit_comment","/like",

                "/report","/add_post","/get_post","/get_list_post","/edit_post","/delete_post",

                "/search","/change_password","/set_user_info","/get_user_friends",

                "/set_request_friend", "/get_user_info", "get_requested_friend","set_accept_friend" );
        return registrationBean;
    }
}

package com.yem.web.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yem.web.manage.filter.LoginFilter;

/**
 * 拦截配置
 *
 * @author Curise
 * @create 2018/12/14
 * @since 1.0.0
 */
public class WebConfigurer implements WebMvcConfigurer  {

	@Autowired
	private LoginFilter loginFilter;

	// 这个方法是用来配置静态资源的，比如html，js，css，等等
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
		.addResourceLocations("classpath:/static/");
	}

	/*// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginFilter)
		.addPathPatterns("/**")
		.excludePathPatterns("/login", "/register");
	}*/
}

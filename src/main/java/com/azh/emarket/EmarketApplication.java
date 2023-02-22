package com.azh.emarket;

import com.azh.emarket.filters.CustomerAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmarketApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<CustomerAuthFilter> userRegistrationBean(){
		FilterRegistrationBean<CustomerAuthFilter> registrationBean = new FilterRegistrationBean<>();
		CustomerAuthFilter customerAuthFilter = new CustomerAuthFilter();
		registrationBean.setFilter(customerAuthFilter);
		registrationBean.addUrlPatterns("/api/products/*");
		registrationBean.addUrlPatterns("/api/cart/*");
		return registrationBean;
	}

}

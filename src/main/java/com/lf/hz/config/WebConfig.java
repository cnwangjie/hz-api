package com.lf.hz.config;

import com.lf.hz.http.filter.IpVerifier;
import com.lf.hz.http.filter.UAVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Config config;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "cache-control", "x-requested-with");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UAVerifier()).addPathPatterns("/**");

        if (config.isRestrictIntranet())
            registry.addInterceptor(new IpVerifier())
                    .addPathPatterns("/**")
                    .excludePathPatterns("/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**")
                .addResourceLocations("file:" + config.getResoucesPath());

        super.addResourceHandlers(registry);
    }
}

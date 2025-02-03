package shop.mtcoding.blog.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.blog.core.interceptor.Logininterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override// 두번째 .에서 추가할 수 있도록
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Logininterceptor())
                .addPathPatterns("/api/**");
    }
}

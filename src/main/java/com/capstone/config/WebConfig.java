import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 정적 리소스 매핑
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/") // 실제 파일 경로
                .setCachePeriod(3600); // 캐시 설정 (초 단위)
    }
}

package inpacker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

// import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableConfigurationProperties({ResourceProperties.class})
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final String[] STATIC_RESOURCES = new String[]{
        "/**/*.css",
        "/**/*.html",
        "/**/*.js",
        "/**/*.json",
        "/**/*.bmp",
        "/**/*.jpeg",
        "/**/*.jpg",
        "/**/*.png",
        "/**/*.ttf",
        "/**/*.eot",
        "/**/*.svg",
        "/**/*.woff",
        "/**/*.woff2",
        "/**/*.css.map"
    };

    @Autowired
    private ResourceProperties resourceProperties = new ResourceProperties();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(STATIC_RESOURCES)
            .addResourceLocations(resourceProperties.getStaticLocations());

        registry.addResourceHandler("/**")
            .addResourceLocations(getIndexLocations())
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
                @Override
                protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    return location.exists() && location.isReadable() ? location : null;
                }
            });
    }

    private String[] getIndexLocations() {
        return Arrays.stream(resourceProperties.getStaticLocations())
            .map(location -> location + "index.html")
            .toArray(String[]::new);
    }
    
//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurerAdapter() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**").allowedOrigins("*");
//                 registry.addMapping("/**").allowedMethods("*");
//             }
//         };
//     }

}

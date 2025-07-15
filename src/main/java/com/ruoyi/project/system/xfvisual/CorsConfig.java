//package com.ruoyi.project.system.xfvisual;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**") // 匹配你的接口路径
//                .allowedOrigins("*") // 允许所有来源，建议生产环境替换为具体域名
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .exposedHeaders("Authorization")
//                .allowCredentials(false)
//                .maxAge(3600);
//    }
//
//    // 方式2：使用CorsFilter（更灵活）
////    @Bean
////    public CorsFilter corsFilter() {
////        CorsConfiguration config = new CorsConfiguration();
////        config.addAllowedOriginPattern("*");  // 允许所有域名，但允许携带凭证时不能用"*"
////        config.addAllowedMethod("*");  // 允许所有方法
////        config.addAllowedHeader("*");  // 允许所有头
////        config.setAllowCredentials(true);  // 允许携带Cookie
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", config);  // 对所有接口都有效
////
////        return new CorsFilter(source);
////    }
//}

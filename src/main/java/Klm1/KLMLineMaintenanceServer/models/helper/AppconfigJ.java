package Klm1.KLMLineMaintenanceServer.models.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppconfigJ implements WebMvcConfigurer {

  @Value("${jwt.issuer:MyOrganisation}")
  public String issuer;

  @Value("${jwt.pass-phrase}")
  public String passphrase;

  @Value("${jwt.expiration-seconds}")
  public int expiration;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowCredentials(true)
            .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
            .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedOrigins("*");

  }
}

package de.aittr.g_38_jp_shop.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@ConfigurationProperties(prefix = "do")
@Getter
@Setter
public class DOProperties {

    private String accessKey;
    private String secretKey;
    private String  endpoint;
    private String  region;
}

package org.springframework.security.crypto.encrypt.property.springboot;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.property.EncryptedPropertySupportPostProcessor;

@Configuration
public class EncryptedOAuthClientCredentialsPropertiesConfig {

    @Bean
    public static BeanDefinitionRegistryPostProcessor encryptedPropertySupportPostProcessor() {
        return new EncryptedPropertySupportPostProcessor();
    }

    @Bean
    public OAuthClientCredentialsProperties oauthClientCredentialsProperties() {
        return new OAuthClientCredentialsProperties();
    }

}
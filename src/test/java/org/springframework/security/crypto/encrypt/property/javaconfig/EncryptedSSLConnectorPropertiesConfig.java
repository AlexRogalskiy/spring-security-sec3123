package org.springframework.security.crypto.encrypt.property.javaconfig;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.encrypt.property.EncryptedPropertySupportPostProcessor;

@Configuration
public class EncryptedSSLConnectorPropertiesConfig {

    @Bean
    public static BeanFactoryPostProcessor propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static BeanFactoryPostProcessor encryptedPropertySupportPostProcessor() {
        return new EncryptedPropertySupportPostProcessor();
    }

    @Bean
    public SSLConnectorProperties sslConnectorProperties() {
        return new SSLConnectorProperties();
    }

}
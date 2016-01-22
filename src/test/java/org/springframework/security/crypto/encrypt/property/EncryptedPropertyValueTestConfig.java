package org.springframework.security.crypto.encrypt.property;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.property.annotation.EncryptedPropertyBeanFactoryPostProcessor;
import org.springframework.security.crypto.encrypt.provider.EncryptorProvider;
import org.springframework.security.crypto.encrypt.provider.StringEncryptorProvider;

@Configuration
public class EncryptedPropertyValueTestConfig {

    @Bean
    public BeanFactoryPostProcessor encryptedPropertyBeanFactoryPostProcessor() {
        return new EncryptedPropertyBeanFactoryPostProcessor();
    }

    @Bean
    public EncryptorProvider<String> stringEncryptorProvider() {
        return new StringEncryptorProvider();
    }

}
package org.springframework.security.crypto.encrypt.property;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.encrypt.provider.EncryptorProvider;
import org.springframework.security.crypto.encrypt.provider.StringEncryptorProvider;

@Configuration
public class EncryptedPropertyValueTestConfig {

    @Bean
    public static BeanFactoryPostProcessor propertySourcesPlaceholderConfigurer() {
        /*
            As per javadoc:

            In order to resolve ${...} placeholders in <bean> definitions or @Value annotations using properties from a PropertySource,
            one must register a PropertySourcesPlaceholderConfigurer. This happens automatically when using <context:property-placeholder> in XML,
            but must be explicitly registered using a static @Bean method when using @Configuration classes.
         */

        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public BeanFactoryPostProcessor encryptedPropertySupportPostProcessor() {
        // TODO
        // The EncryptedPropertySupportPostProcessor should be able to auto-detect EncryptorProvider Type(s).
        // Forcing explicit registration for now.
        EncryptedPropertySupportPostProcessor encryptedPropertySupportPostProcessor = new EncryptedPropertySupportPostProcessor();
        encryptedPropertySupportPostProcessor.setEncryptorProvider(stringEncryptorProvider());

        return encryptedPropertySupportPostProcessor;
    }

    @Bean
    public EncryptorProvider<String> stringEncryptorProvider() {
        return new StringEncryptorProvider();
    }

    @Bean
    public ApplicationProperties applicationProperties() {
        return new ApplicationProperties();
    }

}
package org.springframework.security.crypto.encrypt.property.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 *
 *
 * This BeanFactoryPostProcessor will be called after PropertySourcesPlaceholderConfigurer
 * using the "Ordered". It will lookk for classes annotated with EncryptedPropertySource and load the properties and decrypt and put into Environment.
 *
 */
public class EncryptedPropertyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("In");
    }

}

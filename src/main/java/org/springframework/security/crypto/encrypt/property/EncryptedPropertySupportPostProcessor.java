package org.springframework.security.crypto.encrypt.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringValueResolver;

import java.util.Collections;
import java.util.Set;

public class EncryptedPropertySupportPostProcessor implements
        BeanDefinitionRegistryPostProcessor, PriorityOrdered, EnvironmentAware {

    private int order = Ordered.LOWEST_PRECEDENCE;

    private Environment environment;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // TODO Auto-register Crypto-related core services

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactoryToProcess) throws BeansException {
        registerEncryptedPropertyValueConverter(environment);

    }

    protected void registerEncryptedPropertyValueConverter(Environment environment) {
        if (ConfigurableEnvironment.class.isInstance(environment)) {
            ConfigurableEnvironment configurableEnvironment = ConfigurableEnvironment.class.cast(environment);
            configurableEnvironment.getConversionService().addConverter(new EncryptedPropertyValueConverter());
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private static class EncryptedPropertyValueConverter implements GenericConverter {
        private StringValueResolver valueResolver = new EncryptedStringValueResolver();

        @Override
        public Set<ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new ConvertiblePair(String.class, String.class));
        }

        @Override
        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            return valueResolver.resolveStringValue(String.class.cast(source));
        }
    }

}
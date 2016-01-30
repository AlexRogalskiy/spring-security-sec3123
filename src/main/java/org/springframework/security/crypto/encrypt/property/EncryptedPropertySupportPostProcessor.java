package org.springframework.security.crypto.encrypt.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringValueResolver;

import java.util.Collections;
import java.util.Set;

/**
 * This class is a specialization of {@link BeanDefinitionRegistryPostProcessor} which is responsible for
 * registering {@link BeanDefinition}'s that provide support around encrypted property values.
 *
 * A {@link GenericConverter} is registered with the current {@link Environment}'s {@link ConversionService}
 * and is solely responsible for detecting encrypted property values (indicated by placeholders [...])
 * during property resolution and then decrypting the value before returning back to the caller.
 *
 * @see org.springframework.security.crypto.encrypt.property.EncryptedStringValueResolver
 */
public class EncryptedPropertySupportPostProcessor implements
        BeanDefinitionRegistryPostProcessor, PriorityOrdered, EnvironmentAware {

    private int order = Ordered.LOWEST_PRECEDENCE;

    private Environment environment;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // TODO Register beans providing this feature

        // Register converter against the current environment
        registerEncryptedPropertyValueConverter(environment);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactoryToProcess) throws BeansException {
    }

    protected void registerEncryptedPropertyValueConverter(Environment environment) {
        ConfigurableEnvironment configurableEnvironment = ConfigurableEnvironment.class.cast(environment);
        configurableEnvironment.getConversionService().addConverter(new EncryptedPropertyValueConverter());
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
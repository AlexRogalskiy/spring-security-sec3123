package org.springframework.security.crypto.encrypt.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.provider.EncryptorProvider;
import org.springframework.util.StringValueResolver;

public class EncryptedPropertySupportPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware, Ordered {

    public static final String ENCRYPTED_PROPERTY_VALUE_PREFIX = "[";

    public static final String ENCRYPTED_PROPERTY_VALUE_SUFFIX = "]";

    protected Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // TODO
        // Decrypt encrypted property values in BeanDefinition's (using BeanDefinitionVisitor)


        // Register 'Embedded' StingValueResolver with BeanFactory.
        // Used to resolve string attributes within Annotated Types during Bean Creation (AutowiredAnnotationBeanPostProcessor)
        // This StingValueResolver is solely responsible for detecting encrypted values and then decrypting them.
        PropertyValueDecryptor propertyValueDecryptor = new PropertyValueDecryptor();
        propertyValueDecryptor.setBeanFactory(beanFactory);
        beanFactory.addEmbeddedValueResolver(propertyValueDecryptor);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        /*
            FIXME:
            This BeanFactoryPostProcessor MUST be called after PropertySourcesPlaceholderConfigurer is called.
            Both of these post processors register a StringValueResolver with the ConfigurableListableBeanFactory.addEmbeddedValueResolver hook.
            The StringValueResolver registered by PropertySourcesPlaceholderConfigurer will resolve property placeholders against the Environment.
            The StringValueResolver registered by *this* BeanFactoryPostProcessor will resolve/decrypt encrypted property values against the registerd EncryptorProvider.
            Hence, the ordering is important. First the property placeholder replacement and then the decryption of the property value.
                NOTE: The embedded value resolver's are indirectly called by AutowiredAnnotationBeanPostProcessor during bean creation

            PropertySourcesPlaceholderConfigurer order is set at Ordered.LOWEST_PRECEDENCE

            So setting the order of *this* BeanFactoryPostProcessor to Ordered.LOWEST_PRECEDENCE will ensure it's called after?
            Or looks like we need to set it at Ordered.LOWEST_PRECEDENCE + 1 to guarantee that's it's called after?
         */

        return Ordered.LOWEST_PRECEDENCE + 1;
    }

    private class PropertyValueDecryptor implements StringValueResolver {

        private ConfigurableListableBeanFactory beanFactory;

        private EncryptorProvider<String> encryptorProvider;


        private PropertyValueDecryptor() {
        }

        @Override
        public String resolveStringValue(String strVal) {
            if (strVal == null) {
                return strVal;
            }
            if (!strVal.startsWith(ENCRYPTED_PROPERTY_VALUE_PREFIX) ||
                    !strVal.endsWith(ENCRYPTED_PROPERTY_VALUE_SUFFIX)) {
                return strVal;
            }

            // Strip out the encrypted property value placeholders (indicators)
            strVal = strVal.substring(1, strVal.length() - 1);

            String decryptedValue = getEncryptorProvider().decrypt(strVal);

            return decryptedValue;
        }

        private EncryptorProvider<String> getEncryptorProvider() {
            if (encryptorProvider != null) {
                return encryptorProvider;
            }
            try {
                encryptorProvider = beanFactory.getBean(EncryptorProvider.class);
            } catch (BeansException be) {
                // TODO Implement better handling
                throw be;
            }

            return encryptorProvider;
        }

        private void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

    }

}
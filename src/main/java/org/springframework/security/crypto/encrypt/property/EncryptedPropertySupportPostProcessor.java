package org.springframework.security.crypto.encrypt.property;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.encrypt.StringDecryptor;
import org.springframework.util.StringValueResolver;

public class EncryptedPropertySupportPostProcessor implements BeanFactoryPostProcessor, BeanNameAware, BeanFactoryAware, Ordered {

    private String beanName;

    private BeanFactory beanFactory;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactoryToProcess) throws BeansException {
        StringValueResolver stringValueResolver = createStringValueResolverAdaptDecryptor(beanFactoryToProcess);

        // Decrypt encrypted property values in BeanDefinition's
        // NOTE: Copied from PlaceholderConfigurerSupport.doProcessProperties() - review later for possible reuse?
        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(stringValueResolver);

        String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
        for (String curName : beanNames) {
            // Check that we're not parsing our own bean definition,
            // to avoid failing on unresolvable placeholders in properties file locations.
            if (!(curName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
                BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);
                try {
                    visitor.visitBeanDefinition(bd);
                } catch (Exception ex) {
                    throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName, ex.getMessage(), ex);
                }
            }
        }

        // Register 'Embedded' StingValueResolver with BeanFactory.
        // Used to resolve string attributes within Annotated Types during Bean Creation (AutowiredAnnotationBeanPostProcessor)
        // This StingValueResolver is solely responsible for detecting encrypted values and then decrypting them.
        beanFactoryToProcess.addEmbeddedValueResolver(stringValueResolver);
    }

    private StringValueResolver createStringValueResolverAdaptDecryptor(BeanFactory beanFactory) {
        StringDecryptor stringDecryptor = new StringDecryptor();
        stringDecryptor.setBeanFactory(beanFactory);

        return new StringValueResolver() {
            @Override
            public String resolveStringValue(String strVal) {
                return stringDecryptor.decrypt(strVal);
            }
        };
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
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

}
package org.springframework.security.crypto.encrypt;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.security.crypto.encrypt.provider.EncryptorProvider;

public class StringDecryptor implements Decryptable<String>, BeanFactoryAware {

    public static final String ENCRYPTED_STRING_VALUE_PREFIX = "[";

    public static final String ENCRYPTED_STRING_VALUE_SUFFIX = "]";

    private BeanFactory beanFactory;

    private EncryptorProvider<String> encryptorProvider;


    @Override
    public String decrypt(String strVal) {
        if (strVal == null) {
            return strVal;
        }
        if (!strVal.startsWith(ENCRYPTED_STRING_VALUE_PREFIX) ||
                !strVal.endsWith(ENCRYPTED_STRING_VALUE_SUFFIX)) {
            return strVal;
        }

        // Strip out the encrypted string value placeholders (indicators)
        strVal = strVal.substring(1, strVal.length() - 1);

        String decryptedStrValue = getEncryptorProvider().decrypt(strVal);

        return decryptedStrValue;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
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

}

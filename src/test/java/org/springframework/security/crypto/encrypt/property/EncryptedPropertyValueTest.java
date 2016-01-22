package org.springframework.security.crypto.encrypt.property;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.provider.StringEncryptorProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EncryptedPropertyValueTestConfig.class)
@TestPropertySource("/application-encrypted.properties")
public class EncryptedPropertyValueTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected Environment environment;

    @Autowired
    protected StringEncryptorProvider stringEncryptorProvider;


    @Test
    public void testDecryptPropertyValues() {
        System.out.println("IN");
    }

}

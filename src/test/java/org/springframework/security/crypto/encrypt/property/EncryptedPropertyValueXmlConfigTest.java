package org.springframework.security.crypto.encrypt.property;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans-test.xml")
@TestPropertySource(locations = {"/application-encrypted.properties", "/application-partial-encrypted.properties"})
public class EncryptedPropertyValueXmlConfigTest {

    /*
      Application Properties:
        name1=[encrypted-value1]
        name2=[encrypted-value2]
        name3=[encrypted-value3]
        name4=value4
        name5=[encrypted-value5]
        name6=value6
    */
    @Autowired
    private ApplicationProperties applicationProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(applicationProperties.getValue1()).isEqualTo("decrypted-value1");
        assertThat(applicationProperties.getValue2()).isEqualTo("decrypted-value2");
        assertThat(applicationProperties.getValue3()).isEqualTo("decrypted-value3");
        assertThat(applicationProperties.getValue5()).isEqualTo("decrypted-value5");
    }

}

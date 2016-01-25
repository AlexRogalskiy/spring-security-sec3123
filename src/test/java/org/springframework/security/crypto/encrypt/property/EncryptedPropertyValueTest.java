package org.springframework.security.crypto.encrypt.property;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EncryptedPropertyValueTestConfig.class)
@TestPropertySource(locations = {"/application.properties", "/application-encrypted.properties", "/application-partial-encrypted.properties"})
public class EncryptedPropertyValueTest {

    /*
      Application Properties:
        name1=value1
        name2=value2
        name3=value3
        name4=[encrypted-value4]
        name5=[encrypted-value5]
        name6=[encrypted-value6]
        name7=value7
        name8=[encrypted-value8]
        name9=value9
    */
    @Autowired
    private ApplicationProperties applicationProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertEquals("name4", "decrypted-value4", applicationProperties.getValue4());
        assertEquals("name5", "decrypted-value5", applicationProperties.getValue5());
        assertEquals("name6", "decrypted-value6", applicationProperties.getValue6());
        assertEquals("name8", "decrypted-value8", applicationProperties.getValue8());
    }

    @Test
    public void testPropertyValuesNotDecrypted() {
        assertEquals("name1", "value1", applicationProperties.getValue1());
        assertEquals("name2", "value2", applicationProperties.getValue2());
        assertEquals("name3", "value3", applicationProperties.getValue3());
        assertEquals("name7", "value7", applicationProperties.getValue7());
        assertEquals("name9", "value9", applicationProperties.getValue9());
    }

}

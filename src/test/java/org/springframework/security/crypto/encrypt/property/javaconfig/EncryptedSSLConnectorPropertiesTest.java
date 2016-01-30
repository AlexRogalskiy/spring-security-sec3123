package org.springframework.security.crypto.encrypt.property.javaconfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/*
    This test class demonstrates the use case where an application requires SSL configuration properties
    at startup time in order to configure an HTTPS Connector with the embedded server.

    There are specific SSL configuration properties that are 'highly sensitive', for example,
    the password for a private/secret key or the password for the keystore itself.
    Therefore, it is extremely important to securely 'deliver'
    these configuration values to the application at startup time.

    The tests below assume the sensitive-specific configuration properties are encrypted (in Hex)
    when loaded into the application environment and subsequently decrypted when used by the application.

    The encrypted properties in 'application.properties' are as follows:

    // 8443
    server.port=[38343433]

    // keysecret
    server.ssl.key-password=[6b6579736563726574]

    // classpath:partners-keystore.jks
    server.ssl.key-store=[636c617373706174683a706172746e6572732d6b657973746f72652e6a6b73]

    // keystoresecret
    server.ssl.key-store-password=[6b657973746f7265736563726574]

    // classpath:partners-keystore.jks
    server.ssl.trust-store=[636c617373706174683a706172746e6572732d6b657973746f72652e6a6b73]

    // truststoresecret
    server.ssl.trust-store-password=[747275737473746f7265736563726574]

*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EncryptedSSLConnectorPropertiesConfig.class)
@TestPropertySource(locations = {"/application.properties"})
public class EncryptedSSLConnectorPropertiesTest {

    @Autowired
    private SSLConnectorProperties sslConnectorProperties;

    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(sslConnectorProperties.getServerPort()).isEqualTo(Integer.valueOf(8443));
        assertThat(sslConnectorProperties.getKeyPassword()).isEqualTo("keysecret");
        assertThat(sslConnectorProperties.getKeyStore()).isEqualTo("classpath:partners-keystore.jks");
        assertThat(sslConnectorProperties.getKeyStorePassword()).isEqualTo("keystoresecret");
        assertThat(sslConnectorProperties.getTrustStore()).isEqualTo("classpath:partners-keystore.jks");
        assertThat(sslConnectorProperties.getTrustStorePassword()).isEqualTo("truststoresecret");
    }

}

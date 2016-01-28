package org.springframework.security.crypto.encrypt.property.javaconfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EncryptedSSLConnectorPropertiesConfig.class)
@TestPropertySource(locations = {"/application.properties"})
public class EncryptedSSLConnectorPropertiesTest {

    /*
      Configuration in "application.properties"

        ## SSL Connector
        server.ssl.enabled=true
        server.ssl.key-alias=partners
        server.ssl.key-password=[keysecret]
        server.ssl.key-store=[classpath:partners-keystore.jks]
        server.ssl.key-store-password=[keystoresecret]
        server.ssl.key-store-type=jks
        server.ssl.trust-store=[classpath:partners-keystore.jks]
        server.ssl.trust-store-password=[truststoresecret]
        server.ssl.trust-store-type=jks
    */
    @Autowired
    private SSLConnectorProperties sslConnectorProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(sslConnectorProperties.getKeyPassword()).isEqualTo("decrypted-keysecret");
        assertThat(sslConnectorProperties.getKeyStore()).isEqualTo("decrypted-classpath:partners-keystore.jks");
        assertThat(sslConnectorProperties.getKeyStorePassword()).isEqualTo("decrypted-keystoresecret");
        assertThat(sslConnectorProperties.getTrustStore()).isEqualTo("decrypted-classpath:partners-keystore.jks");
        assertThat(sslConnectorProperties.getTrustStorePassword()).isEqualTo("decrypted-truststoresecret");
    }

}

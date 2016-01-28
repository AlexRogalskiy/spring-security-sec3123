package org.springframework.security.crypto.encrypt.property.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptedOAuthCredentialsPropertiesApplication.class)
public class EncryptedOAuthCredentialsPropertiesTest {

    /*
      Configuration in "application.properties"

        ## OAuth Client Credentials
        security.oauth2.client.client-id=[serviceapp1]
        security.oauth2.client.client-secret=[serviceapp1secret]
    */
    @Autowired
    private OAuthCredentialsProperties oauthCredentialsProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(oauthCredentialsProperties.getClientId()).isEqualTo("decrypted-serviceapp1");
        assertThat(oauthCredentialsProperties.getClientSecret()).isEqualTo("decrypted-serviceapp1secret");
    }

}

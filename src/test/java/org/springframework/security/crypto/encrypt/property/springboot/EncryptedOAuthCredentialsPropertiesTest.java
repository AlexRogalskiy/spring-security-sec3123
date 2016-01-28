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

        ## serviceapp1
        security.oauth2.client.client-id=[7365727669636561707031]

        ## serviceapp1secret
        security.oauth2.client.client-secret=[7365727669636561707031736563726574]
    */
    @Autowired
    private OAuthCredentialsProperties oauthCredentialsProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(oauthCredentialsProperties.getClientId()).isEqualTo("serviceapp1");
        assertThat(oauthCredentialsProperties.getClientSecret()).isEqualTo("serviceapp1secret");
    }

}

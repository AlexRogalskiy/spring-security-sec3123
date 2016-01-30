package org.springframework.security.crypto.encrypt.property.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/*
    This test class demonstrates the use case where an application requires OAuth 2.0 Client Credential
    configuration properties to be used for a Service-to-Service call using the 'Client Credentials Grant Flow'.

    The properties that contain the Client Id and Client Secret are considered very sensitive, therefore,
    it is extremely important to securely 'deliver' these configuration values to the application at runtime.

    The tests below assume the sensitive-specific configuration properties are encrypted (in Hex)
    when loaded into the application environment and subsequently decrypted when used by the application.

    The encrypted properties in 'application.properties' are as follows:

    // serviceapp1
    security.oauth2.client.client-id=[7365727669636561707031]

    // serviceapp1secret
    security.oauth2.client.client-secret=[7365727669636561707031736563726574]

*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EncryptedOAuthClientCredentialsPropertiesApplication.class)
public class EncryptedOAuthClientCredentialsPropertiesTest {

    @Autowired
    private OAuthClientCredentialsProperties oauthClientCredentialsProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(oauthClientCredentialsProperties.getClientId()).isEqualTo("serviceapp1");
        assertThat(oauthClientCredentialsProperties.getClientSecret()).isEqualTo("serviceapp1secret");
    }

}
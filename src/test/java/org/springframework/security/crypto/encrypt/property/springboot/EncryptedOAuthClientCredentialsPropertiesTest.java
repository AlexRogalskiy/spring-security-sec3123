package org.springframework.security.crypto.encrypt.property.springboot;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
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

    @Autowired
    private Environment environment;


    @Test
    public void testDefaultPropertiesDecrypted() {
        assertThat(oauthClientCredentialsProperties.getClientId()).isEqualTo("serviceapp1");
        assertThat(oauthClientCredentialsProperties.getClientSecret()).isEqualTo("serviceapp1secret");
    }

    @Test
    public void testSystemPropertyOverrideDecrypted() {
        String clientSecretPropertyName = "security.oauth2.client.client-secret";

        // Check default
        assertThat(environment.getProperty(clientSecretPropertyName)).isEqualTo("serviceapp1secret");

        System.setProperty(clientSecretPropertyName, "[73657276696365617070312d6e6577736563726574]");

        // Check override
        assertThat(environment.getProperty(clientSecretPropertyName)).isEqualTo("serviceapp1-newsecret");
    }

    @Test
    public void testCommandLineArgumentsDecrypted() {
        String clientSecretPropertyName = "security.oauth2.client.client-secret";
        String defaultClientSecret = "serviceapp1secret";
        String newClientSecret = "serviceapp1-newsecret";

        // Check default
        assertThat(environment.getProperty(clientSecretPropertyName)).isEqualTo(defaultClientSecret);

        // Add command line arguments property source
        SimpleCommandLinePropertySource commandLineArguments = new SimpleCommandLinePropertySource(
                "--" + clientSecretPropertyName + "=[73657276696365617070312d6e6577736563726574]");
        ConfigurableEnvironment configurableEnvironment = ConfigurableEnvironment.class.cast(environment);
        configurableEnvironment.getPropertySources().addFirst(commandLineArguments);

        // Check override
        assertThat(environment.getProperty(clientSecretPropertyName)).isEqualTo(newClientSecret);

        // Reset
        configurableEnvironment.getPropertySources().remove(commandLineArguments.getName());

        // Check default again
        assertThat(environment.getProperty(clientSecretPropertyName)).isEqualTo(defaultClientSecret);
    }

}
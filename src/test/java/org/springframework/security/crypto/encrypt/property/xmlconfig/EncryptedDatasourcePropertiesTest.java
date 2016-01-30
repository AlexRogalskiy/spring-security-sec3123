package org.springframework.security.crypto.encrypt.property.xmlconfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/*
    This test class demonstrates the use case where an application requires
    configuration properties used for configuring a DataSource at application startup.

    The properties that contain the database username and password are considered very sensitive,
    therefore, it is extremely important to securely 'deliver' these configuration values at application startup.

    The tests below assume the sensitive-specific configuration properties are encrypted (in Hex)
    when loaded into the application environment and subsequently decrypted when used by the application.

    The encrypted properties in 'application.properties' are as follows:

    // jdbc:mysql://localhost:3306/testdb
    spring.datasource.url=[6a6462633a6d7973716c3a2f2f6c6f63616c686f73743a333330362f746573746462]

    // testuser1
    spring.datasource.username=[746573747573657231]

    // testuser1password
    spring.datasource.password=[74657374757365723170617373776f7264]

*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans-test-encrypted-datasource.xml")
@TestPropertySource(locations = {"/application.properties"})
public class EncryptedDatasourcePropertiesTest {

    @Autowired
    private DataSourceProperties dataSourceProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(dataSourceProperties.getUrl()).isEqualTo("jdbc:mysql://localhost:3306/testdb");
        assertThat(dataSourceProperties.getUserName()).isEqualTo("testuser1");
        assertThat(dataSourceProperties.getPassword()).isEqualTo("testuser1password");
    }

}
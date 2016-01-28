package org.springframework.security.crypto.encrypt.property.xmlconfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans-test-encrypted-datasource.xml")
@TestPropertySource(locations = {"/application.properties"})
public class EncryptedDatasourcePropertiesTest {

    /*
      Configuration in "application.properties"

        ## jdbc:mysql://localhost:3306/testdb
        spring.datasource.url=[6a6462633a6d7973716c3a2f2f6c6f63616c686f73743a333330362f746573746462]

        ## testuser1
        spring.datasource.username=[746573747573657231]

        ## testuser1password
        spring.datasource.password=[74657374757365723170617373776f7264]
    */
    @Autowired
    private DataSourceProperties dataSourceProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(dataSourceProperties.getUrl()).isEqualTo("jdbc:mysql://localhost:3306/testdb");
        assertThat(dataSourceProperties.getUserName()).isEqualTo("testuser1");
        assertThat(dataSourceProperties.getPassword()).isEqualTo("testuser1password");
    }

}

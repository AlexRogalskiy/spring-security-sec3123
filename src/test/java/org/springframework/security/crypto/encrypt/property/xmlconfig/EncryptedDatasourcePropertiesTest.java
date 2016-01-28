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

        ## Datasource
        spring.datasource.driver-class-name=com.mysql.jdbc.Driver
        spring.datasource.jmx-enabled=true
        spring.datasource.name=testdb
        spring.datasource.url=[jdbc:mysql://localhost:3306/testdb]
        spring.datasource.username=[testuser1]
        spring.datasource.password=[testuser1password]
    */
    @Autowired
    private DataSourceProperties dataSourceProperties;


    @Test
    public void testPropertyValuesDecrypted() {
        assertThat(dataSourceProperties.getUrl()).isEqualTo("decrypted-jdbc:mysql://localhost:3306/testdb");
        assertThat(dataSourceProperties.getUserName()).isEqualTo("decrypted-testuser1");
        assertThat(dataSourceProperties.getPassword()).isEqualTo("decrypted-testuser1password");
    }

}

package org.springframework.security.crypto.encrypt.property;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(EncryptedPropertyValueJavaConfig.class)
public class EncryptedPropertyValueSpringBoot {

    public static void main(String[] args) {
        SpringApplication.run(EncryptedPropertyValueSpringBoot.class, args);
    }

}

package org.springframework.security.crypto.encrypt.property.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptedPropertySource {

    String[] value();

}

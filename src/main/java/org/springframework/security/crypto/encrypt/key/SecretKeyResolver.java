package org.springframework.security.crypto.encrypt.key;

public interface SecretKeyResolver<T> {

    SecretKey<T> resolve();

}
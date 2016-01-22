package org.springframework.security.crypto.key;

public interface SecretKeyResolver<T> {

    SecretKey<T> resolve();

}
package org.springframework.security.crypto.key;

public interface SecretKey<T> {

    T getKey();

}
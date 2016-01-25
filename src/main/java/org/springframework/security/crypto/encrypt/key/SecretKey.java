package org.springframework.security.crypto.encrypt.key;

public interface SecretKey<T> {

    T getKey();

}
package org.springframework.security.crypto.encrypt;

public interface Encryptable<T> {

    T encrypt(T data);

}
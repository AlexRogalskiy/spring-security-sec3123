package org.springframework.security.crypto.encrypt.provider;

public interface EncryptorProvider<T> {

    T encrypt(T data);

    T decrypt(T encryptedData);

}
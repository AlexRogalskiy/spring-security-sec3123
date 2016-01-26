package org.springframework.security.crypto.encrypt;

public interface Decryptable<T> {

    T decrypt(T data);

}

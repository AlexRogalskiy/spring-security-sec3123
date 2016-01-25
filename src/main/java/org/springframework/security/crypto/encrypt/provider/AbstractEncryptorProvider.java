package org.springframework.security.crypto.encrypt.provider;

import org.springframework.security.crypto.encrypt.key.SecretKeyResolver;

public abstract class AbstractEncryptorProvider<R, T> implements EncryptorProvider<T> {

    protected AbstractEncryptorProvider() {
    }

    public abstract SecretKeyResolver<R> getSecretKeyResolver();

}
package org.springframework.security.crypto.encrypt.provider;

import org.springframework.security.crypto.encrypt.key.SecretKey;
import org.springframework.security.crypto.encrypt.key.SecretKeyResolver;

public class StringEncryptorProvider extends AbstractEncryptorProvider<String, String> {

    public StringEncryptorProvider() {
    }

    @Override
    public SecretKeyResolver<String> getSecretKeyResolver() {
        return new SystemEnvironmentSecretKeyResolver();
    }

    @Override
    public String encrypt(String data) {
        return "encrypted-" + data;
    }

    @Override
    public String decrypt(String encryptedData) {
        return encryptedData.replace("encrypted", "decrypted");
    }

    private static class SystemEnvironmentSecretKeyResolver implements SecretKeyResolver<String> {
        @Override
        public SecretKey<String> resolve() {
            return new SecretKey<String>() {
                @Override
                public String getKey() {
                    // TODO Get the secret key from the system environment using the 'well known' name
                    return null;
                }
            };
        }
    }

}
package org.springframework.security.crypto.key;

public class StringSecretKeyResolver implements SecretKeyResolver<String> {

    @Override
    public SecretKey<String> resolve() {
        return null;
    }

}

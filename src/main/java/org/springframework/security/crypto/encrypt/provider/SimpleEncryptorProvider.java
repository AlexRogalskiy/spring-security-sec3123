package org.springframework.security.crypto.encrypt.provider;

public class SimpleEncryptorProvider implements EncryptorProvider<String> {

    @Override
    public String encrypt(String data) {
        return "encrypted-" + data;
    }

    @Override
    public String decrypt(String data) {
        return data.replace("encrypted", "decrypted");
    }

}
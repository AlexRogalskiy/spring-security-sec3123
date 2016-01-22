package org.springframework.security.crypto.encrypt.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class StringEncryptorProvider implements EncryptorProvider<String> {

    private String prop1Value;

    @Autowired
    private Environment environment;

    public StringEncryptorProvider() {
    }

    @Override
    public String encrypt(String data) {
        return "encrypted-"+data;
    }

    @Override
    public String decrypt(String encryptedData) {
        return "decrypted-"+encryptedData;
    }

    public String getProp1Value() {
        return prop1Value;
    }

    @Value("#{environment['name1']}")
    public void setProp1Value(String prop1Value) {
        this.prop1Value = prop1Value;
    }
}
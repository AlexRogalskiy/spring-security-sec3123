package org.springframework.security.crypto.encrypt.provider;

import org.springframework.security.crypto.encrypt.Decryptable;
import org.springframework.security.crypto.encrypt.Encryptable;

public interface EncryptorProvider<T> extends Encryptable<T>, Decryptable<T> {

}
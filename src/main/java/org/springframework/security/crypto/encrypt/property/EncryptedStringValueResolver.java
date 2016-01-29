package org.springframework.security.crypto.encrypt.property;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.util.StringValueResolver;

public class EncryptedStringValueResolver implements StringValueResolver {

    public static final String ENCRYPTED_STRING_VALUE_PREFIX = "[";

    public static final String ENCRYPTED_STRING_VALUE_SUFFIX = "]";

    // TODO Inject application-provided TextEncryptor - using default impl for now
    private TextEncryptor textEncryptor = new HexTextEncryptor();

    @Override
    public String resolveStringValue(String strVal) {
        if (!canResolve(strVal)) {
            return strVal;
        }

        // Strip out the encrypted string value placeholders
        strVal = strVal.substring(1, strVal.length() - 1);

        String decryptedStrValue = textEncryptor.decrypt(strVal);

        return decryptedStrValue;
    }

    protected boolean canResolve(String strVal) {
        if (strVal == null) {
            return false;
        }
        return (strVal.startsWith(ENCRYPTED_STRING_VALUE_PREFIX) &&
                strVal.endsWith(ENCRYPTED_STRING_VALUE_SUFFIX));
    }

    private static class HexTextEncryptor implements TextEncryptor {

        @Override
        public String encrypt(String text) {
            return new String(Hex.encode(Utf8.encode(text)));
        }

        @Override
        public String decrypt(String encryptedText) {
            return Utf8.decode(Hex.decode(encryptedText));
        }
    }

}
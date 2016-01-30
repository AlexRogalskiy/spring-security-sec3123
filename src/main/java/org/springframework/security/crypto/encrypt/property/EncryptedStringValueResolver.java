package org.springframework.security.crypto.encrypt.property;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.util.StringValueResolver;

/**
 * This class is a {@link StringValueResolver} which is capable of
 * detecting encrypted string values.
 *
 * If the string value is encrypted, it will attempt to decrypt the value
 * using the provided {@link TextEncryptor} before returning back to the caller.
 *
 * @see org.springframework.security.crypto.encrypt.TextEncryptor
 * @see org.springframework.security.crypto.encrypt.property.EncryptedPropertySupportPostProcessor
 */
public class EncryptedStringValueResolver implements StringValueResolver {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "[";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "]";

    protected String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

    protected String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;

    // TODO Obtain application-provided TextEncryptor (using default impl for now)
    private TextEncryptor textEncryptor = new HexTextEncryptor();


    @Override
    public String resolveStringValue(String strVal) {
        if (!canResolve(strVal)) {
            return strVal;
        }

        // Strip out the placeholders
        strVal = strVal.substring(getPlaceholderPrefix().length(), strVal.length() - getPlaceholderSuffix().length());

        String decryptedStrValue = textEncryptor.decrypt(strVal);

        return decryptedStrValue;
    }

    protected boolean canResolve(String strVal) {
        if (strVal == null) {
            return false;
        }
        return (strVal.startsWith(getPlaceholderPrefix()) &&
                strVal.endsWith(getPlaceholderSuffix()));
    }

    public String getPlaceholderPrefix() {
        return placeholderPrefix;
    }

    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    public String getPlaceholderSuffix() {
        return placeholderSuffix;
    }

    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
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
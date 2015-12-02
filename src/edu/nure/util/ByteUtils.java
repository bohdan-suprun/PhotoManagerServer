package edu.nure.util;

/**
 * Created by bod on 02.12.15.
 */
public class ByteUtils {

    static final String HEXES = "0123456789ABCDEF";

    public static String toHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    public static byte[] fromHex(String hexString) {
        hexString = hexString.toUpperCase();
        byte[] result = new byte[hexString.length() / 2];

        for (int i = 0; i < hexString.length() / 2; i += 2) {
            char digit1 = hexString.charAt(i);
            char digit2 = hexString.charAt(i + 1);
            result[i / 2] = (byte) ((HEXES.indexOf(digit1) << 4) | HEXES.indexOf(digit2));
        }
        return result;
    }
}

package de.hscoburg.etif.vbis.lagerix.backend.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class implements an SHA512 digest method and the formatting into HEX.
 *
 * @author zuch1000
 */
public class SHA512 {

    /**
     * Formating the passed byte array into HEX String
     *
     * @param data - The byte array
     * @return The formated String
     */
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /**
     * Build the digest of the parameter and format it to HEX:
     *
     * @param text - The text for the digests
     * @return - The digest in hex format
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String SHA512(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-512");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("UTF-8"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}

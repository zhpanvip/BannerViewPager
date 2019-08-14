package com.zhpan.idea.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhpan on 2017/10/20.
 * Description:
 */

public class KeyTools {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String TAG = "KeyTools";

    /**
     * 获取MD5加密的之后的hex字符串
     * @param info
     * @return
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes());
            byte[] encryption = md5.digest();
            return DataConversionTools.bytesToHexString(encryption);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    /**
     * 获取HMAC-SHA1加密之后的hex字符串
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    public static String getHmacSHA1(String data, String key) throws SignatureException {
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                    HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            return DataConversionTools.bytesToHexString(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : "
                    + e.getMessage());
        }
    }
}

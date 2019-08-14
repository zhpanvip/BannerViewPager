package com.zhpan.idea.net.https;


import com.zhpan.idea.R;
import com.zhpan.idea.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


/**
 * Created by zhpan on 2018/1/25.
 */
public class SslContextFactory {
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_TRUST_MANAGER = "X.509";
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";
    private static final String CLIENT_TRUST_KEY = "PKCS12";
    private static final String CLIENT_TRUST_PROVIDER = "BC";
    public static String TRUST_CA_PWD = "Huawei@123";
    public static String SELF_CERT_PWD = "IoM@1234";

    /**
     * 单项认证
     */
    public static SSLSocketFactory getSSLSocketFactoryForOneWay(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CLIENT_TRUST_MANAGER, CLIENT_TRUST_PROVIDER);
            KeyStore keyStore = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 双向认证
     *
     * @return SSLSocketFactory
     */
    public static SSLSocketFactory getSSLSocketFactoryForTwoWay() {
        try {
            InputStream certificate = Utils.getContext().getResources().openRawResource(R.raw.capk);
            //  CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
            KeyStore keyStore = KeyStore.getInstance(CLIENT_TRUST_KEY);
            keyStore.load(certificate, SELF_CERT_PWD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, SELF_CERT_PWD.toCharArray());

            try {
                if (certificate != null)
                    certificate.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //初始化keystore
            KeyStore clientKeyStore = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
            clientKeyStore.load(Utils.getContext().getResources().openRawResource(R.raw.cabks), TRUST_CA_PWD.toCharArray());

            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(clientKeyStore);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, SELF_CERT_PWD.toCharArray());

            sslContext.init(kmf.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

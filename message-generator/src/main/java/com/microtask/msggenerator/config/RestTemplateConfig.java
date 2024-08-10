package com.microtask.msggenerator.config;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;



import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class RestTemplateConfig {
//server.ssl.trust-store = classpath:certs/https/service-truststore.p12

    @Value("${security.ssl.trust-store}")
    private Resource trustStore;
//
//    {
//        try {
//            trustStore = new FileUrlResource("/certs/https/service-truststore.p12");
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Value("${security.ssl.trust-store-password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException,
                                CertificateException, IOException {

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();
        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConFactory)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);


//        return new RestTemplate();
    }
//    public static void main(String[] args) throws Exception {
//        KeyStore trustStore = KeyStore.getInstance("PKCS12");
//        try (InputStream trustStoreStream = RestTemplateConfig.class.getResourceAsStream("/certs/https/service-truststore.p12")) {
//            if (trustStoreStream == null) {
//                throw new Exception("Truststore not found!");
//            }
//            trustStore.load(trustStoreStream, "your_password".toCharArray());
//        }
//
//        System.out.println("Truststore loaded successfully with aliases: ");
//        trustStore.aliases().asIterator().forEachRemaining(System.out::println);
//    }

}
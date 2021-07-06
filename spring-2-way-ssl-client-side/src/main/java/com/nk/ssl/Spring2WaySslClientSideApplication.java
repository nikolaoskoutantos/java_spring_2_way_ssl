package com.nk.ssl;

import java.io.InputStream;
import java.net.http.HttpClient;
import java.security.KeyStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

@SuppressWarnings("deprecation")
@SpringBootApplication
public class Spring2WaySslClientSideApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring2WaySslClientSideApplication.class, args);
	}
	
	
	@Bean
	public RestTemplate getRestTemp() {
		RestTemplate restTemp = new RestTemplate();
		KeyStore key_store;
		HttpComponentsClientHttpRequestFactory reqFactory = null;
		
		try {
			key_store = KeyStore.getInstance("jks");
			ClassPathResource classRes = new ClassPathResource("nt-gateway.jks");
			InputStream inStream = classRes.getInputStream();
			key_store.load(inStream, "nt-gateway".toCharArray());
			
			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
					.loadTrustMaterial(null , new TrustSelfSignedStrategy())
					.loadKeyMaterial(key_store , "nt-gateway".toCharArray()).build(),
					NoopHostnameVerifier.INSTANCE);
			
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(socketFactory)
					.setMaxConnTotal(Integer.valueOf(5))
					.setMaxConnPerRoute(Integer.valueOf(5))
					.build();
			
			reqFactory = new HttpComponentsClientHttpRequestFactory(httpclient);
			reqFactory.setReadTimeout(Integer.valueOf(10000));
			reqFactory.setReadTimeout(Integer.valueOf(10000));
			restTemp.setRequestFactory(reqFactory);
			
		}catch(Exception ex) {
			System.err.println("Something occured during the creation of RestTemplate" + ex);
			 ex.printStackTrace();
		}
		
		return restTemp;
	}
}

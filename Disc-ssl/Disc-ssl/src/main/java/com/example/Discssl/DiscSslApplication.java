package com.example.Discssl;

import java.io.File;

import javax.net.ssl.SSLContext;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class DiscSslApplication {


	/*@Value("${server.ssl.trust-store}")
	private File trustStore;
	@Value("${server.ssl.trust-store-password}")
	private String trustStorePassword;
	@Value("${server.ssl.key-store}")
	private File keyStore;
	@Value("${server.ssl.key-store-password}")
	private String keyStorePassword;
	@Value("${server.ssl.key-password}")
	private String keyPassword;

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs getTrustStoredEurekaClient(SSLContext sslContext) {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		args.setSSLContext(sslContext);
		args.setHostnameVerifier(new NoopHostnameVerifier());
		return args;
	}

	@Bean
	public SSLContext sslContext() throws Exception {
		return new SSLContextBuilder()
				.loadTrustMaterial(trustStore, trustStorePassword.toCharArray())
				.loadKeyMaterial(keyStore, keyStorePassword.toCharArray(), keyPassword.toCharArray())
				.build();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(DiscSslApplication.class, args);
	}


}


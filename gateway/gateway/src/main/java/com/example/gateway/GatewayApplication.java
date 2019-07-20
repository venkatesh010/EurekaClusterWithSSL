package com.example.gateway;

import com.netflix.discovery.DiscoveryClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;
import java.io.File;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayApplication {
	@Value("${server.ssl.trust-store}")
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
		return args;
	}
	@Bean
	public SSLContext sslContext() throws Exception {
		return new SSLContextBuilder()
				.loadTrustMaterial(trustStore, trustStorePassword.toCharArray())
				.loadKeyMaterial(keyStore, keyStorePassword.toCharArray(), keyPassword.toCharArray())
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}



	@Bean
	RouteLocator getBean(RouteLocatorBuilder builder){
		return builder.routes().route(
				r->r.path("/")
				.uri("forward:/hello"))
				.route(
					r->r.path("/dummy/*")
					.filters(
							f->f.rewritePath("/dummy/(?<segment>.*)", "/${segment}")
					).uri("lb://DUMMY/")
				).build();
	}
}

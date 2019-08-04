package com.example.gateway;

import com.netflix.discovery.DiscoveryClient;
import org.apache.http.ssl.SSLContextBuilder;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

	@GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<String> proxy(ProxyExchange<byte[]> proxy) throws Exception {

		Mono<ResponseEntity<byte[]>> resp1 = proxy.uri("http://localhost:9002/hello").get();
		Mono<ResponseEntity<byte[]>> resp2 = proxy.uri("http://localhost:9002/").get();

		Mono<String> composed = resp1.zipWith(resp2, (a,b)->{
			ByteBuffer bf1 = ByteBuffer.wrap(Objects.requireNonNull(a.getBody()));
			ByteBuffer bf2 = ByteBuffer.wrap(Objects.requireNonNull(b.getBody()));

			return StandardCharsets.UTF_8.decode(bf1).toString() + StandardCharsets.UTF_8.decode(bf2).toString();
		});


		//composed.subscribe(System.out::println);
		return composed;
	}
}

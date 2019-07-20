package com.example.Discssl;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;

import javax.net.ssl.SSLContext;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import com.netflix.eureka.EurekaServerConfig;
import com.netflix.eureka.cluster.PeerEurekaNode;
import com.netflix.eureka.cluster.PeerEurekaNodes;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.eureka.resources.ServerCodecs;
import com.netflix.eureka.transport.JerseyReplicationClient;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.ReflectionUtils.getField;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class DiscSslApplication {


	@Value("${server.ssl.trust-store}")
	private String trustStore;
	@Value("${server.ssl.trust-store-password}")
	private String trustStorePassword;
	@Value("${server.ssl.key-store}")
	private String keyStore;
	@Value("${server.ssl.key-store-password}")
	private String keyStorePassword;
	@Value("${server.ssl.key-password}")
	private String keyPassword;

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs getTrustStoredEurekaClient(SSLContext sslContext) {
		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		args.setSSLContext(sslContext);
		//args.setHostnameVerifier(new NoopHostnameVerifier());
		return args;
	}

	@Bean
	public SSLContext sslContext() throws Exception {
		return new SSLContextBuilder()
				.loadTrustMaterial(ResourceUtils.getURL(trustStore), trustStorePassword.toCharArray())
				.loadKeyMaterial(ResourceUtils.getURL(keyStore), keyStorePassword.toCharArray(), keyPassword.toCharArray())
				.build();
	}

/*
	@Bean
	public PeerEurekaNodes peerEurekaNodes(PeerAwareInstanceRegistry registry,
			ServerCodecs serverCodecs,
			ApplicationInfoManager applicationInfoManager,
			EurekaServerConfig eurekaServerConfig,
			EurekaClientConfig eurekaClientConfig) {
		return new PeerEurekaNodes(registry, eurekaServerConfig,
				eurekaClientConfig, serverCodecs, applicationInfoManager) {
			@Override
			protected PeerEurekaNode createPeerEurekaNode(String peerEurekaNodeUrl) {
				PeerEurekaNode peerEurekaNode = super.createPeerEurekaNode(peerEurekaNodeUrl);


try {
	Field privateStringField = PeerEurekaNode.class.getDeclaredField("replicationClient");

	privateStringField.setAccessible(true);

	JerseyReplicationClient replicationClient = (JerseyReplicationClient) privateStringField.get(peerEurekaNode);

	Field privateStringField1 = JerseyReplicationClient.class.getDeclaredField("jerseyApacheClient");

	privateStringField1.setAccessible(true);

	ApacheHttpClient4 jerseyApacheClient = (ApacheHttpClient4) privateStringField1.get(replicationClient);

	jerseyApacheClient.addFilter(new YourCustomClientFilter(){

	});
}catch (Exception e){
	System.out.println("VENY---------------------------------------------------------------");
}

return peerEurekaNode;
			}
		};
	}
*/


	public static void main(String[] args) {
		SpringApplication.run(DiscSslApplication.class, args);
	}

	@Configuration
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.passwordEncoder(NoOpPasswordEncoder.getInstance())
					.withUser("admin").password("admin")
					.authorities("ADMIN");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.csrf()
					.disable()
					.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.httpBasic();
		}
	}
}


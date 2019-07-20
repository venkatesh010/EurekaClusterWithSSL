package com.example.Discssl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import static com.sun.jersey.client.urlconnection.HTTPSProperties.PROPERTY_HTTPS_PROPERTIES;

//@Component
public class YourCustomClientFilter
	//	extends ClientFilter
{
	//@Autowired
	/*private SSLContext sslContext=new SSLContextBuilder()
			.loadTrustMaterial(ResourceUtils.getURL("D:\\dev\\workspace\\eureka-ssl\\Disc-ssl\\Disc-ssl\\src\\main\\resources\\edu_truststore.jks"), "chase123".toCharArray())
			.loadKeyMaterial(ResourceUtils.getURL("D:\\dev\\workspace\\eureka-ssl\\Disc-ssl\\Disc-ssl\\src\\main\\resources\\edu_keystore.jks"), "chase123".toCharArray(), "chase123".toCharArray())
			.build();

	public YourCustomClientFilter() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException, UnrecoverableKeyException {
	}

	@Override
	public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {
		clientRequest.getHeaders().putSingle(PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(new NoopHostnameVerifier(), sslContext));
		return getNext().handle(clientRequest);
	}
*/


}

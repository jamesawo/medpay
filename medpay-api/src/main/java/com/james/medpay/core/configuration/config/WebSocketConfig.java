package com.james.medpay.core.configuration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private static final String SOCKET_ENDPOINT = "/socket";
	private static final String SOCKET_DESTINATION_PREFIX = "app";
	private static final String SOCKET_TOPIC = "/topic";
	@Value("${app.frontend.url}")
	private String webUrlEndpoint;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(SOCKET_ENDPOINT).setAllowedOrigins(webUrlEndpoint).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker(SOCKET_TOPIC);
		registry.setApplicationDestinationPrefixes(SOCKET_DESTINATION_PREFIX);
	}
}

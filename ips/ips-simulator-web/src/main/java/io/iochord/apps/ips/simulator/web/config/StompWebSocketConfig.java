package io.iochord.apps.ips.simulator.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import io.iochord.apps.ips.core.services.ServiceContext;

/**
 * 
 * Web Socket Configuration
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * STOMP endpoint registration
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(ServiceContext.WS_ENDPOINT).setAllowedOrigins("*").withSockJS();
	}

	/**
	 * Websocket prefix registration
	 * - request  = ServiceContext.WS_REQUEST_URI
	 * - response = ServiceContext.WS_RESPONSE_URI
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes(ServiceContext.WS_REQUEST_URI);
		registry.enableSimpleBroker(ServiceContext.WS_RESPONSE_URI);
	}
}
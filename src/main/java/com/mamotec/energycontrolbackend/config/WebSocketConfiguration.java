package com.mamotec.energycontrolbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Aktivieren Sie den einfachen Broker für den Nachrichtenaustausch
        config.setApplicationDestinationPrefixes("/app"); // Prefix für Anwendungsziele
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registrieren Sie den WebSocket-Endpunkt, über den die Verbindung hergestellt wird
        registry.addEndpoint("/ws-endpoint")
                .setAllowedOrigins("*") // Erlauben Sie Verbindungen von allen Ursprüngen (nur für Testzwecke)
                .setHandshakeHandler(new DefaultHandshakeHandler()); // Verwenden Sie den Standard-Handshake-Handler
    }
}

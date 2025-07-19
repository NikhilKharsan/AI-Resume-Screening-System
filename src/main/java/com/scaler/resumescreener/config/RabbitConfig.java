package com.scaler.resumescreener.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String ANALYSIS_QUEUE = "analysis.queue";
    public static final String ANALYSIS_EXCHANGE = "analysis.exchange";
    public static final String ANALYSIS_ROUTING_KEY = "analysis.routing.key";

    @Bean
    public org.springframework.amqp.core.Queue analysisQueue() {
        return (org.springframework.amqp.core.Queue) QueueBuilder.durable(ANALYSIS_QUEUE).build();
    }

    @Bean
    public TopicExchange analysisExchange() {
        return new TopicExchange(ANALYSIS_EXCHANGE);
    }

    @Bean
    public Binding analysisBinding() {
        return BindingBuilder
                .bind(analysisQueue())
                .to(analysisExchange())
                .with(ANALYSIS_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}

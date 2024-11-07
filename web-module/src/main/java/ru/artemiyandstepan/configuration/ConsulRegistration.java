package ru.artemiyandstepan.configuration;

import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

@Singleton
@Startup
public class ConsulRegistration {

    private static final Logger logger = LogManager.getLogger(ConsulRegistration.class);

    //конфиг
    private static final String SERVICE_NAME = "first-service";
    private static final String SERVICE_ID = "first-service-web-1.0-SNAPSHOT";
    private static final String SERVICE_ADDRESS = "localhost";
    private static final int SERVICE_PORT = 8080;
    private static final String CONSUL_URL = "http://localhost:8500";

    //регистрация сервиса в consul
    @PostConstruct
    public void registerService() {
        int registrationRetryCounter = 0;
        while (registrationRetryCounter < 5) {
            try {
                Consul consul = Consul.builder().withUrl(CONSUL_URL).build();

                Registration service = ImmutableRegistration.builder()
                        .id(SERVICE_ID)
                        .name(SERVICE_NAME)
                        .address(SERVICE_ADDRESS)
                        .port(SERVICE_PORT)
                        .build();
                consul.agentClient().register(service);
                logger.info("Сервис зарегистрирован в Consul: {}", SERVICE_NAME);
                break;
            } catch (Exception e) {
                registrationRetryCounter++;
                logger.error("Ошибка регистрации в Consul, попытка {}: {}", registrationRetryCounter, e.getMessage());
                if (registrationRetryCounter == 5) {
                    logger.error("Не удалось зарегистрировать сервис в Consul");
                }
                // ждет 2с прежде чем пробовать снова
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    //отмена регистрации при отключениии
    @PreDestroy
    public void deregisterService() {
        try {
            Consul consul = Consul.builder().withUrl(CONSUL_URL).build();
            consul.agentClient().deregister(SERVICE_ID);
            logger.info("Сервис удален из Consul: {}", SERVICE_NAME);
        } catch (Exception e) {
            logger.error("Ошибка удаления из Consul: {}", e.getMessage());
        }
    }


}


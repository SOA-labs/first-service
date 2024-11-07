package ru.artemiyandstepan.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000"); // Разрешить доступ с порта 3000
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true"); // разрешить отправку и получение cookie
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS"); // разрешенные методы
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization"); //резрешенные заголовки
    }
}


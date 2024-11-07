package ru.artemiyandstepan;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Main extends Application {
    //    public static void main(String[] args) {
//        System.out.println("Hello world!");
//    }
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Регистрация ресурсы
        resources.add(MovieResource.class);
        // Регистрация фильтр CORS
        resources.add(ru.artemiyandstepan.filter.CORSFilter.class);

        return resources;
    }
}
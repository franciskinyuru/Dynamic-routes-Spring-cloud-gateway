package apigw.configuration;

import apigw.service.ApiRouteService;
import apigw.service.impl.ApiPathRouteLocatorImpl;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService, RouteLocatorBuilder routeLocatorBuilder){
        return new ApiPathRouteLocatorImpl(apiRouteService,routeLocatorBuilder);
    }

//    @Bean
//    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory) {
//        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(new ClassPathResource("schema.sql"));
//        initializer.setDatabasePopulator(populator);
//        return initializer;
//    }
}

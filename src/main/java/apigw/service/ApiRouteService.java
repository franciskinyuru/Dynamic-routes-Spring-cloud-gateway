package apigw.service;

import apigw.model.db.ApiRoute;
import apigw.model.web.CreateOrUpdateApiRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {
    Flux<ApiRoute> findApiRoutes();
    Mono<ApiRoute> findApiRoute(Long id);
    Mono<Void> createApiRoute(CreateOrUpdateApiRoute createOrUpdateApiRoute);
    Mono<Void> updateApiRoute (Long id, CreateOrUpdateApiRoute createOrUpdateApiRoute);
    Mono<Void> deleteApiRoute(Long id);
}

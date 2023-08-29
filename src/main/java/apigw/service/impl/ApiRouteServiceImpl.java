package apigw.service.impl;

import apigw.model.db.ApiRoute;
import apigw.model.web.CreateOrUpdateApiRoute;
import apigw.repository.ApiRouteRepository;
import apigw.service.ApiRouteService;
import apigw.service.GatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiRouteServiceImpl implements ApiRouteService {
    @Autowired
    private ApiRouteRepository apiRouteRepository;
    @Autowired
    private GatewayRouteService gatewayRouteService;
    @Override
    public Flux<ApiRoute> findApiRoutes() {
        return apiRouteRepository.findAll();
    }

    @Override
    public Mono<ApiRoute> findApiRoute(Long id) {
        return findAndValidate(id);
    }

    @Override
    public Mono<Void> createApiRoute(CreateOrUpdateApiRoute createOrUpdateApiRoute) {
        ApiRoute apiRoute=setNewApiRoute(new ApiRoute(),createOrUpdateApiRoute);
        return apiRouteRepository.save(apiRoute).
                doOnSuccess(obj -> gatewayRouteService.refreshRoutes())
                .then();
    }

    @Override
    public Mono<Void> updateApiRoute(Long id, CreateOrUpdateApiRoute createOrUpdateApiRoute) {
        return findAndValidate(id)
                .map(apiRoute -> setNewApiRoute(apiRoute,createOrUpdateApiRoute))
                .flatMap(apiRouteRepository::save)
                .doOnSuccess(obj -> gatewayRouteService.refreshRoutes())
                .then();
    }

    @Override
    public Mono<Void> deleteApiRoute(Long id) {
        return findAndValidate(id)
                .flatMap(apiRoute -> apiRouteRepository.deleteById(apiRoute.getId()))
                .doOnSuccess(obj -> gatewayRouteService.refreshRoutes());
    }

    private Mono<ApiRoute> findAndValidate(Long id){
        return apiRouteRepository.findById(id).switchIfEmpty(Mono.error(
                new RuntimeException(String.format("Api route with id %d not found", id))
        ));
    }

    private ApiRoute setNewApiRoute(ApiRoute apiRoute,
                                    CreateOrUpdateApiRoute createOrUpdateApiRouteRequest) {
        apiRoute.setPath(createOrUpdateApiRouteRequest.getPath());
        apiRoute.setMethod(createOrUpdateApiRouteRequest.getMethod());
        apiRoute.setUri(createOrUpdateApiRouteRequest.getUri());
        return apiRoute;
    }
}

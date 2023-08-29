package apigw.controller;

import apigw.model.constant.ApiPath;
import apigw.model.db.ApiRoute;
import apigw.model.web.ApiRouteResponse;
import apigw.model.web.CreateOrUpdateApiRoute;
import apigw.service.ApiRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping(path = ApiPath.INTERNAL_API_ROUTES)
public class InternalApiRouteController {
    private final ApiRouteService apiRouteService;
    @Autowired
    public InternalApiRouteController(ApiRouteService apiRouteService) {
        this.apiRouteService = apiRouteService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<ApiRouteResponse>> findApiRoutes(){
        return apiRouteService.findApiRoutes()
                .map(this::convertApiRouteIntoApiRouteResponse)
                .collectList()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ApiRouteResponse> findApiRoute(@PathVariable Long id){
        return apiRouteService.findApiRoute(id)
                .map(this::convertApiRouteIntoApiRouteResponse)
                .subscribeOn(Schedulers.boundedElastic());
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> createApiRoute(@RequestBody @Validated CreateOrUpdateApiRoute createOrUpdateApiRoute){
        return apiRouteService.createApiRoute(createOrUpdateApiRoute)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> updateApiRoute(@PathVariable Long id, @RequestBody @Validated CreateOrUpdateApiRoute createOrUpdateApiRoute){
        return apiRouteService.updateApiRoute(id,createOrUpdateApiRoute)
                .subscribeOn(Schedulers.boundedElastic());
    }
    @DeleteMapping(path = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> deleteApiRoute(@PathVariable Long id){
        return apiRouteService.deleteApiRoute(id)
                .subscribeOn(Schedulers.boundedElastic());
    }
    private ApiRouteResponse convertApiRouteIntoApiRouteResponse(ApiRoute apiRoute) {
        ApiRouteResponse apiRouteResponse=new ApiRouteResponse();
        apiRouteResponse.setId(apiRoute.getId());
        apiRouteResponse.setPath(apiRoute.getPath());
        apiRouteResponse.setMethod(apiRoute.getMethod());
        apiRouteResponse.setUri(apiRoute.getUri());
        return apiRouteResponse;
    }
}

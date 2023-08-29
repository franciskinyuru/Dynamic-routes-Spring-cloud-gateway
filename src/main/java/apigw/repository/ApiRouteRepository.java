package apigw.repository;

import apigw.model.db.ApiRoute;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute, Long> {

}

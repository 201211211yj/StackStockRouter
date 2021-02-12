package stackstock.router.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import stackstock.router.document.Nasdaq;

@Repository
public interface NasdaqRepository extends ReactiveMongoRepository<Nasdaq, String> {

    Mono<Nasdaq> findById(String Id);
    Flux<Nasdaq> findFirstByCode(String code);
    Flux<Nasdaq> findByCode(String code);
    Flux<Nasdaq> deleteByCode(String code);

}

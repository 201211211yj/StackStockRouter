package stackstock.router.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import stackstock.router.document.Ticker;

@Repository
public interface TickerRepository extends ReactiveMongoRepository<Ticker, String> {

    Mono<Ticker> findByCode(String code);
}

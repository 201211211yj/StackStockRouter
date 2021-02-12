package stackstock.router.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import stackstock.router.document.Nasdaq;
import stackstock.router.document.Ticker;
import stackstock.router.repository.TickerRepository;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class TickerHandler {

    @Autowired
    TickerRepository tickerRepository;
    
    public Mono<ServerResponse> getNasdaqTikcers(ServerRequest request){
        Flux<Ticker> result = tickerRepository.findAll();
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, Nasdaq.class);
    }
    public Mono<ServerResponse> getNasdaqTickerByCode(ServerRequest request){
        String code = request.pathVariable("code");
        Mono <Ticker> result = tickerRepository.findByCode(code);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, Nasdaq.class);

    }
}

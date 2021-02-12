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
import stackstock.router.repository.NasdaqRepository;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class NasdaqHandler {

    @Autowired
    NasdaqRepository nasdaqRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getNasdaq(ServerRequest request) {
        String code = request.pathVariable("code");
        Flux <Nasdaq> fluxNasdaq = nasdaqRepository.findByCode(code);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxNasdaq,Nasdaq.class);
    }

    public Mono<ServerResponse> postNasdaq(ServerRequest request) {

        Flux<Nasdaq> result = request.bodyToFlux(Nasdaq.class)
                .flatMap(nasdaqRepository::save);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result,Nasdaq.class);
    }

    public Mono<ServerResponse> putNasdaq(ServerRequest request) {

        Mono<Nasdaq> nasdaq = request.bodyToMono(Nasdaq.class);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(nasdaq,Nasdaq.class);
    }

    public Mono<ServerResponse> deleteNasdaq(ServerRequest request) {
        String code = request.pathVariable("code");
        Flux<Nasdaq> fluxNasdaq = nasdaqRepository.deleteByCode(code);
        fluxNasdaq.subscribe();
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxNasdaq,Void.class);
    }
    
}

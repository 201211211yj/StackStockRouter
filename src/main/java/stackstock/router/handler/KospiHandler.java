package stackstock.router.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import stackstock.router.document.Kospi;
import stackstock.router.repository.KospiRepository;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class KospiHandler {

    @Autowired
    KospiRepository kospiRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getKospi(ServerRequest request) {
        String code = request.pathVariable("code");
        Flux <Kospi> fluxKospi = kospiRepository.findByCode(code);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxKospi,Kospi.class);
    }

    public Mono<ServerResponse> postKospi(ServerRequest request) {

        Flux<Kospi> result = request.bodyToFlux(Kospi.class)
                .flatMap(kospiRepository::save);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result,Kospi.class);
    }

    public Mono<ServerResponse> putKospi(ServerRequest request) {

        Mono<Kospi> kospi = request.bodyToMono(Kospi.class);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(kospi,Kospi.class);
    }

    public Mono<ServerResponse> deleteKospi(ServerRequest request) {
        String code = request.pathVariable("code");
        Flux<Kospi> fluxKospi = kospiRepository.deleteByCode(code);
        fluxKospi.subscribe();
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fluxKospi,Void.class);
    }
    
}

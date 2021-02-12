package stackstock.router.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import stackstock.router.handler.KospiHandler;
import stackstock.router.handler.NasdaqHandler;
import stackstock.router.handler.TickerHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterConfigurer implements WebFluxConfigurer {

    @Autowired
    KospiHandler kospiHandler;
    @Autowired
    NasdaqHandler nasdaqHandler;
    @Autowired
    TickerHandler tickerHandler;

    @Bean
    public RouterFunction<?> kospiRouterFunction() {
        return route(GET("/"),
                request -> ok().render("home")).
                andRoute(GET("/hello"),
                        request -> ok().bodyValue("hello")).
                andRoute(GET("/kospi/{code}"), kospiHandler::getKospi).
                andRoute(POST("/kospi"), kospiHandler::postKospi).
                andRoute(PUT("/kospi"), kospiHandler::putKospi).
                andRoute(DELETE("/kospi/{code}"), kospiHandler::deleteKospi);

    }

    @Bean
    public RouterFunction<?> nasdaqRouterFunction() {
        return route(GET("/nasdaq/{code}"), nasdaqHandler::getNasdaq).
                andRoute(POST("/nasdaq"), nasdaqHandler::postNasdaq).
                andRoute(PUT("/nasdaq"), nasdaqHandler::putNasdaq).
                andRoute(DELETE("/nasdaq/{code}"), nasdaqHandler::deleteNasdaq);

    }

    @Bean
    public RouterFunction<?> nasdaqTickerRouterFunction() {
        return route(GET("/nasdaqTickers"), tickerHandler::getNasdaqTikcers).
                andRoute(GET("nasdaqTicker/{code}"), tickerHandler::getNasdaqTickerByCode);

    }

}

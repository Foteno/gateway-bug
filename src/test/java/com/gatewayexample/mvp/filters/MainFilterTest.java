package com.gatewayexample.mvp.filters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TestGatewayFilterTest {

	@InjectMocks
	private MainFilter testGatewayFilter;

	@Test
	void shouldRunFilter() {
		MockServerHttpRequest mockRequest = MockServerHttpRequest
				.get("/testfilter")
				.build();
		MockServerWebExchange exchange = MockServerWebExchange.from(mockRequest);

		Mono<Void> result = testGatewayFilter.filter(exchange, e -> Mono.empty());
		StepVerifier.create(result)
				.verifyComplete();
		//assertion passes with spring boot 3.3.x and spring cloud gateway dependencies 4.1.x but not 3.4.x and 4.2.x respectively
		assertEquals("test", exchange.getRequest().getHeaders().getFirst("test-header"));
	}
}
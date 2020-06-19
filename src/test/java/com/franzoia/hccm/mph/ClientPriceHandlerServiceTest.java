package com.franzoia.hccm.mph;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.franzoia.hccm.mph.domain.PriceHandler;
import com.franzoia.hccm.mph.domain.PriceHandlerBuilder;
import com.franzoia.hccm.mph.service.PriceHandlerService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientPriceHandlerServiceTest {

	@Value("${local.server.port}")
	private int port;
	
	@InjectMocks
	private PriceHandlerService service;
	
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private final String localhost = "http://localhost:";
	
	@Test
	public void testSendToClientMock() {
		
		final Date date = new Date();
		
		final PriceHandler ph = PriceHandlerBuilder.newBuilder()
				.setId(1)
				.setInstrumentName("GPB/USD")
				.setBid(Double.valueOf(119.6))
				.setAsk(Double.valueOf(130.20))
				.setTimestamp(date)
				.build();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(localhost + port + "/client/prices/update");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

		HttpEntity<PriceHandler> entity = new HttpEntity<>(ph, headers);

		ResponseEntity<PriceHandler> result = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.PUT, entity, PriceHandler.class);
		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}

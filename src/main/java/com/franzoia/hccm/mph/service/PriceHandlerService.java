package com.franzoia.hccm.mph.service;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.franzoia.hccm.mph.domain.PriceHandler;
import com.franzoia.hccm.mph.domain.PriceHandlerBuilder;

/**
 * Service class to handle price messages
 * 
 * @author franzoiar
 *
 */
@Service
public class PriceHandlerService {

	Logger logger = LoggerFactory.getLogger(PriceHandlerService.class);

	public static final int SECONDS = 1000;
	public static final String localhost = "http://localhost:8080";
	public static final String MESSAGE_REGEX = "[,]";
	
	public static final Double MARGIN_BID = 0.999; // -0.1%
	public static final Double MARGIN_ASK = 1.001; // +0.1%

	/**
	 * Process messages received adding new Bid and Ask prices and send the result to a client REST service
	 * 
	 * @param message
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public void onMessage(String message) throws InterruptedException, NumberFormatException, ParseException {

		// process data structure
		final PriceHandler ph = createPriceHandler(message);

		// apply price changes
		final PriceHandler phWithMargins = applyPriceUpdate(ph);
		
		// wait a few seconds
		Thread.sleep(SECONDS);

		// send to client rest
		sendPriceToClient(phWithMargins);
	}

	/**
	 * Takes a messgae price handle and applies the price variation
	 * 
	 * @param priceHandler
	 * @return
	 */
	public PriceHandler applyPriceUpdate(PriceHandler priceHandler) {
		return PriceHandlerBuilder.newBuilder()
					.setId(priceHandler.getId())
					.setInstrumentName(priceHandler.getInstrumentName())
					.setTimestamp(priceHandler.getTimestamp())
					.setBid(priceHandler.getBid() * MARGIN_BID)
					.setAsk(priceHandler.getAsk() * MARGIN_ASK)
					.build();
		
	}
	
	/**
	 * creates a new PriceHandle object from a message
	 * 
	 * @param message
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public PriceHandler createPriceHandler(String message) throws NumberFormatException, ParseException {

		final String[] fields = message.split(MESSAGE_REGEX);

		return PriceHandlerBuilder.newBuilder()
					.setId(Integer.valueOf(fields[0]))
					.setInstrumentName(fields[1])
					.setBid(Double.valueOf(fields[2]))
					.setAsk(Double.valueOf(fields[3]))
					.setTimestamp(Utils.TIMESTAMP_FORMAT.parse(fields[4]))
					.build();
	}

	/**
	 * Send the new PriceHandle object to a clients' REST service
	 * for testing purposes this implementation is using LOCALHOST as client REST API server
	 * 
	 * @param priceHandler
	 * @return
	 */
	public HttpStatus sendPriceToClient(PriceHandler priceHandler) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(localhost + "/client/prices/update");

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());

		HttpEntity<PriceHandler> entity = new HttpEntity<>(priceHandler, headers);

		ResponseEntity<PriceHandler> result = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.PUT, entity, PriceHandler.class);
		
		return result.getStatusCode();

	}

}

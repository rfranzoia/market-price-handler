package com.franzoia.hccm.mph.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.franzoia.hccm.mph.domain.PriceHandler;

/**
 * Service class that Mocks the client service handling for the REST API
 * 
 * @author franzoiar
 *
 */
@Service
public class ClientPriceHandlerService {

	Logger logger = LoggerFactory.getLogger(PriceHandlerService.class);
	
	public void clientPriceHandlerMock(PriceHandler priceHandler) {
		logger.info(String.format("client: %s", priceHandler.toString()));
	}
}

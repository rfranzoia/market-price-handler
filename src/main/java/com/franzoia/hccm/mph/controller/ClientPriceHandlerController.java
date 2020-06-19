package com.franzoia.hccm.mph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franzoia.hccm.mph.domain.PriceHandler;
import com.franzoia.hccm.mph.service.ClientPriceHandlerService;

/**
 * REST Controller to mock a client REST application that will receive price updates
 * 
 * @author franzoiar
 *
 */
@RestController
@RequestMapping("/client/prices")
public class ClientPriceHandlerController {
	
	@Autowired
	private ClientPriceHandlerService service;
	
	@PutMapping("/update")
	public void updateEmployee(@RequestBody final PriceHandler price) {
		service.clientPriceHandlerMock(price);
	}

}

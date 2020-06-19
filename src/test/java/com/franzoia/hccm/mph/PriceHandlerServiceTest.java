package com.franzoia.hccm.mph;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.franzoia.hccm.mph.domain.PriceHandler;
import com.franzoia.hccm.mph.domain.PriceHandlerBuilder;
import com.franzoia.hccm.mph.service.PriceHandlerService;
import com.franzoia.hccm.mph.service.Utils;

@RunWith(MockitoJUnitRunner.class)	
public class PriceHandlerServiceTest {

	@InjectMocks
	private PriceHandlerService service;
	
	@Test
	public void testMarginUpdate() {
		final PriceHandler ph = PriceHandlerBuilder.newBuilder()
							.setId(1)
							.setInstrumentName("GPB/USD")
							.setBid(Double.valueOf(119.6))
							.setAsk(Double.valueOf(130.20))
							.setTimestamp(new Date())
							.build();
		
		final Double expectedAsk = ph.getAsk() * PriceHandlerService.MARGIN_ASK;
		final Double expectedBid = ph.getBid() * PriceHandlerService.MARGIN_BID;
		
		final PriceHandler updatedPH = service.applyPriceUpdate(ph);
		
		assertThat(updatedPH.getAsk()).isEqualTo(expectedAsk);
		assertThat(updatedPH.getBid()).isEqualTo(expectedBid);
	}
	
	@Test
	public void testMessageConverter() throws NumberFormatException, ParseException {
		
		final Date date = new Date();
		
		final PriceHandler ph = PriceHandlerBuilder.newBuilder()
				.setId(1)
				.setInstrumentName("GPB/USD")
				.setBid(Double.valueOf(119.6))
				.setAsk(Double.valueOf(130.20))
				.setTimestamp(date)
				.build();
		 
		final String message = "1,GPB/USD,119.6,130.20," + Utils.getFormattedTimestamp(date);
		
		PriceHandler priceHandler = service.createPriceHandler(message);
		
		assertThat(priceHandler).isEqualTo(ph);
	}
	
}

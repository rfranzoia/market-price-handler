package com.franzoia.hccm.mph.domain;

import java.util.Date;

/**
 * PriceHandle builder class
 * 
 * @author franzoiar
 *
 */
public class PriceHandlerBuilder {

	private Integer id;
	private String instrumentName;
	private Double bid;
	private Double ask;
	private Date timestamp;

	private PriceHandlerBuilder() {
	}
	
	public static PriceHandlerBuilder newBuilder() {
		return new PriceHandlerBuilder();
	}
	
	public PriceHandlerBuilder setId(Integer id) {
		this.id = id;
		return this;
	}

	public PriceHandlerBuilder setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
		return this;
	}

	public PriceHandlerBuilder setBid(Double bid) {
		this.bid = bid;
		return this;
	}

	public PriceHandlerBuilder setAsk(Double ask) {
		this.ask = ask;
		return this;
	}

	public PriceHandlerBuilder setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public PriceHandler build() {
		return new PriceHandler(id, instrumentName, bid, ask, timestamp);
	}

}

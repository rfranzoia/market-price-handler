package com.franzoia.hccm.mph.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Price Handle 
 * 
 * @author franzoiar
 *
 */
public class PriceHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String instrumentName;
	private Double bid;
	private Double ask;
	private Date timestamp;
	
	public PriceHandler() {
	}

	public PriceHandler(Integer id, String instrumentName, Double bid, Double ask, Date timestamp) {
		this.id = id;
		this.instrumentName = instrumentName;
		this.bid = bid;
		this.ask = ask;
		this.timestamp = timestamp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceHandler other = (PriceHandler) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %f %f %s", instrumentName, bid, ask, timestamp);
	}
	
}

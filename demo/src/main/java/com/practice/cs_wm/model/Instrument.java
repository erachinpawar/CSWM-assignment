package com.practice.cs_wm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="INSTRUMENTS")
public class Instrument {
	
	

    @Id
    @Column(name="instrument_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "instruments_seq")
	private long refInstrumentId;
    
    @Column(name="instrument_name")
	private String instrumentName;
    
    
    @Column(name="created_by")
    private String createdBy;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="created_on")
    private Date createdOn;
	
	
	public long getRefInstrumentId() {
		return refInstrumentId;
	}
	public void setRefInstrumentId(long refInstrumentId) {
		this.refInstrumentId = refInstrumentId;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	@Override
	public String toString() {
		return "refInstruments [refInstrumentId=" + refInstrumentId + ", instrumentName=" + instrumentName + ",  createdBy=" + createdBy + ", createdOn=" + createdOn + "]";
	}
	
	
	

}

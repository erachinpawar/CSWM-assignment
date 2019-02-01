package com.practice.cs_wm.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.Instrument;

@Service
public interface InstrumentService  extends AbstractService {

	List<Instrument> getAllInstruments();

	Instrument addInstrument(Instrument instrument);

	Instrument updateInstrument(Instrument instrument);

	void removeInstrument(long instrumentId);

	Instrument getInstrument(long instrumentid);

	Map<Long, Instrument> getAllInstrumentMap();
	
	

	
	
}

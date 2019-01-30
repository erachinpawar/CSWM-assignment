package com.practice.cs_wm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.repository.InstrumentRepository;
import com.practice.cs_wm.service.InstrumentService;

@Service
public class InstrumentServiceImpl implements InstrumentService  {
	
	@Autowired
	InstrumentRepository instrumentRepository;

	@Override
	public List<Instrument> getAllInstruments() {
		return (List<Instrument>) instrumentRepository.findAll();
	}

	@Override
	public Instrument addInstrument(Instrument instrument) {
		return instrumentRepository.save(instrument);
	}

	@Override
	public Instrument getInstrument(long refInstrumentId) {
		return instrumentRepository.findByRefInstrumentId(refInstrumentId);
	}

	@Override
	public Instrument updateInstrument(Instrument instrument) {
		// TODO Auto-generated method stub
		return instrumentRepository.save(instrument);
	}

	@Override
	public void removeInstrument(long refInstrumentId) {
		instrumentRepository.deleteById(refInstrumentId);		
	}

	@Override
	public Map<Long, Instrument> getAllInstrumentMap() {
		Map<Long, Instrument> instrumentMap = new HashMap<>();
		getAllInstruments().forEach(instrument -> instrumentMap.put(instrument.getRefInstrumentId(),instrument) );
		return instrumentMap;
	}
	
	

}

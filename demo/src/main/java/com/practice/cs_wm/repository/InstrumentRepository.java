package com.practice.cs_wm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.cs_wm.model.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

	
Instrument findByRefInstrumentId(long refInstrumentId);
	
	Instrument deleteByRefInstrumentId(long refInstrumentId);

}

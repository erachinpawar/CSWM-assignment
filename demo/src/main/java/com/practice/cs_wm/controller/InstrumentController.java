package com.practice.cs_wm.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.service.InstrumentService;

@Path("/Instruments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InstrumentController {
	
	@Autowired
	InstrumentService instrumentService;
	
	
	@GET
	public List<Instrument> getInstruments() {
		return instrumentService.getAllInstruments();
	}
	
	@POST
	public Instrument addInstrument(Instrument instrument, @Context UriInfo uriInfo) {
		Instrument newInstrument = instrumentService.addInstrument(instrument);
		return newInstrument;
	}
	
	@PUT
	@Path("/{instrumentId}")
	public Instrument updateInstrument(@PathParam("instrumentId") long instrumentid, Instrument instrument) {
		instrument.setRefInstrumentId(instrumentid);;
		return instrumentService.updateInstrument(instrument);
	}
	
	@DELETE
	@Path("/{instrumentId}")
	public void deleteInstrument(@PathParam("instrumentId") long instrumentId) {
		instrumentService.removeInstrument(instrumentId);
	}
	
	@GET
	@Path("/{instrumentId}")
	public Instrument getInstrument(@PathParam("instrumentId") long instrumentid, @Context UriInfo uriInfo) {
		Instrument instrument = instrumentService.getInstrument(instrumentid);
		return instrument;
		
	}
	

}



	

package com.practice.cs_wm.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.practice.cs_wm.model.Instrument;
import com.practice.cs_wm.service.InstrumentService;

@Path("/instruments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstrumentResource {

	@Autowired
	InstrumentService instrumentService;

	@GET
	public List<Instrument> getInstruments() {
		List<Instrument> instrumentList = instrumentService.getAllInstruments();
		return instrumentList;
	}

	@POST
	public Instrument addInstrument(Instrument instrument) {
		return instrumentService.addInstrument(instrument);
	}

	@GET
	@Path("/{refInstrumentId}")
	public Instrument getInstrument(@PathParam("refInstrumentId") long refInstrumentId) {
		return instrumentService.getInstrument(refInstrumentId);
	}

	@PUT
	@Path("/{refInstrumentId}")
	
	public Instrument updateInstrument(@PathParam("refInstrumentId") long refInstrumentId, Instrument instrument) {
		instrument.setRefInstrumentId(refInstrumentId);
		return instrumentService.updateInstrument(instrument);
	}

	@DELETE
	@Path("/{refInstrumentId}")
	public void deleteInstrument(@PathParam("refInstrumentId") long refInstrumentId) {
		instrumentService.removeInstrument(refInstrumentId);
	}

}

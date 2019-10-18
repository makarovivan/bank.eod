package com.ibm.experimental.bank.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.experimental.bank.processing.Processor;

@Path("/process")
public class RestService {
	
	@GET
	@Path("/load")
	@Produces(MediaType.TEXT_PLAIN)
	public String load() {
		Processor processor = new Processor();
		new Thread(processor).start();

		return "Processing started: 1000 messages";
	}
	
	@GET
	@Path("/eod")
	@Produces(MediaType.TEXT_PLAIN)
	public String eod() {
		try {
			Message.setNextEod(10);

			return "EOD set";
		} catch (Exception e) {
			return "EOD not set. " + e.getMessage();
		}
	}
	
	@GET
	@Path("/clear")
	@Produces(MediaType.TEXT_PLAIN)
	public String clear() {
		Processor.stopAll();
		return "Processors removed from cache";
	}
}

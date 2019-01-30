package com.practice.cs_wm.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.practice.cs_wm.resources.InstrumentResource;
import com.practice.cs_wm.resources.OrderBookResource;
import com.practice.cs_wm.resources.OrderResource;
import com.practice.cs_wm.resources.RefOrderBookStatusResource;
import com.practice.cs_wm.resources.RefOrderStatusResource;

@Component
@ApplicationPath("/api")
public class JerseyAppConfig extends ResourceConfig{

	public JerseyAppConfig() {
		register(InstrumentResource.class);
		register(OrderResource.class);
		register(OrderBookResource.class);
		register(RefOrderBookStatusResource.class);
		register(RefOrderStatusResource.class);
	}
	

}

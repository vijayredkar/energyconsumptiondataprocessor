package com.powerhouse.ws.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.powerhouse.ws.model.Meter;

public interface EnergyProcessorService 
{
	 Meter getMeter(Long id);
	 Collection<Meter> getAllMeters();	
		 
	 boolean createMeterReadings(Collection<Meter> meters);
	 boolean updateMeterReading(Meter meterReading);
	 
	 boolean deleteMeterReading(Meter meterReadingToDelete);
	
	 Double getConsumption(String meterId, String month);
}

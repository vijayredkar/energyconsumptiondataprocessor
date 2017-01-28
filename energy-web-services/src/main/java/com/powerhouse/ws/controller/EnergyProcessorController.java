package com.powerhouse.ws.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.powerhouse.ws.model.Meter;
import com.powerhouse.ws.service.EnergyProcessorService;

@RestController
public class EnergyProcessorController 
{	
    @Autowired
	EnergyProcessorService energyProcessorService;
			
	//http://localhost:8080/meters
	@RequestMapping(value="/meters",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Meter>> getMeters()
	{	
		Collection<Meter> listOfMeters = energyProcessorService.getAllMeters();
	    return new ResponseEntity(listOfMeters, HttpStatus.OK);
	}
	
	//POSTMAN - localhost:8080/meter/1
	@RequestMapping(value="/meter/{id}",
					produces=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.GET)
	public ResponseEntity<Meter> getMeterById(@PathVariable("id") Long id)
	{		
		return new ResponseEntity<Meter>(energyProcessorService.getMeter(id),HttpStatus.OK);
	}
		
	//POSTMAN - localhost:8080/meter/0001/FEB
	@RequestMapping(value="/meter/{meterid}/{month}",
					produces=MediaType.APPLICATION_JSON_VALUE,
					method=RequestMethod.GET)
	public String getConsumption(@PathVariable("meterid") String meterid, @PathVariable("month") String month)
	{		
		System.out.println("---- meterid and month "+ meterid +" and "+ month);
		
		Double consumption = energyProcessorService.getConsumption(meterid, month);
		String returnConsumption = consumption.toString();
		String responseJSON = "{\"consumption\":"+returnConsumption+"}";
		return responseJSON;
	}
	
	//POSTMAN - localhost:8080/meterreadings/create
		//multiple meterReadings sent as input in a JSON array (array can contain one or more objects)
		//[{"meterId":"0005","profileName":"A","month":"JAN","meterReading":10},{"meterId":"0006","profileName":"B","month":"FEB","meterReading":5}]
		   @RequestMapping(value="/meterreadings/create",
				   		   method=RequestMethod.POST,
				   		   consumes=MediaType.APPLICATION_JSON_VALUE,
				   		   produces=MediaType.APPLICATION_JSON_VALUE)   
			public ResponseEntity<Collection<Meter>> createMeterReadings(@RequestBody  Collection<Meter> meterReadings)
			{  
			   if(energyProcessorService.createMeterReadings(meterReadings))
			   {
				   return new ResponseEntity(HttpStatus.CREATED);//created successfully
			   }
			   
			   return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); //not created			   
			}

  //POSTMAN localhost:8080/meterReadings/delete/1     {"meterId":"0005","profileName":"A","month":"JAN","meterReading":10}		   
   @RequestMapping(value="/meterReadings/delete",
		   		   	method=RequestMethod.DELETE,
		   		   	consumes=MediaType.APPLICATION_JSON_VALUE)   
  public ResponseEntity<Meter> deleteMeterReading(@RequestBody Meter meterReadingToDelete)
  {
	   if(energyProcessorService.deleteMeterReading(meterReadingToDelete))
	   {
		   return new ResponseEntity(HttpStatus.NO_CONTENT);//deleted successfully
	   }
	   
	   return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); //not deleted
  }		   
		   
		   
   //POSTMAN localhost:8080/meterReadings/update/1 {"meterId":"0005","profileName":"A","month":"JAN","meterReading":2}
   //in our scenario {id} does not make sense for update because the client will not know about this DB primary key
   @RequestMapping(value="/meterReadings/update",
		   		   method=RequestMethod.PUT,
		   		   consumes=MediaType.APPLICATION_JSON_VALUE,
		   		   produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Meter> updateMeterReading(@RequestBody Meter updatedMeterReading)
   {
	   if(energyProcessorService.updateMeterReading(updatedMeterReading))
	   {
		   return new ResponseEntity(HttpStatus.OK);//update successfully
	   }
	   
	   return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); //not updated			   
	}
}

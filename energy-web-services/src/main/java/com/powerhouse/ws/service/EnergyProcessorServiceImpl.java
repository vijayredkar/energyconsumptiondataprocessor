package com.powerhouse.ws.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powerhouse.ws.model.Meter;
import com.powerhouse.ws.repository.EnergyProcessorDAO;
import com.powerhouse.ws.validation.MeterReadingValidator;
import com.powerhouse.ws.validation.Validator;


@Service
public class EnergyProcessorServiceImpl implements EnergyProcessorService 
{

	@Autowired
	EnergyProcessorDAO energyProcessorDAO;
	
	@Autowired
	MeterReadingValidator meterReadingValidator;
	
	@Override
	public Meter getMeter(Long id) 
	{		
		return energyProcessorDAO.findOne(id);
	}

	@Override
	public Collection<Meter> getAllMeters() 
	{
		Collection<Meter> meters = energyProcessorDAO.findAll();
		return meters;
	}
	
	public boolean createMeterReadings(Collection<Meter> meterReadings) 
	{
		try {			
			//Collection filteredMeterReadingsCollection = Validator.validateMeterReadings(meterReadings);//get input data validated
			Collection filteredMeterReadingsCollection = meterReadingValidator.validateMeterReadings(meterReadings);//get input data validated
			energyProcessorDAO.save(filteredMeterReadingsCollection);//persist only validated input data
			return true;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Double getConsumption(String meterId, String month) 
	{
		try {			
			String[] calendarMonths = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			
			double meterReading = 0;
			double meterReadingPreviousMonth = 0;
			String prevMonth = null;
			
			List meterReadings = (ArrayList) energyProcessorDAO.findAll();
   
			meterReading = getMeterReadingByMonth(meterReadings, meterId, month);
			
			if("JAN".equals(month))
			{
				return meterReading;//meter reading gets reset start of JAN, so no reading is available for DEC at present
			}
			
			for(int i=0; i<calendarMonths.length; i++)
			{
				if(month.equals(calendarMonths[i]))
				{	
					prevMonth = calendarMonths[i-1];			
				}				
			}			
			meterReadingPreviousMonth = getMeterReadingByMonth(meterReadings, meterId, prevMonth);
			
			return meterReading - meterReadingPreviousMonth;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return null;
	}
	
	private Double getMeterReadingByMonth(List meterReadings, String meterId, String month) throws Exception
	{	
		Iterator metersItr = meterReadings.iterator();		   
		   while(metersItr.hasNext())
		   {
			   Meter meter = (Meter) metersItr.next();
			   if(
				   meter.getMeterId().equals(meterId) &&
				   meter.getMonth().equals(month)						   
				  )
			   {
				   return meter.getMeterReading();
			   }
		   }
		   return null;
	 }
	
	
	public boolean updateMeterReading(Meter meterReadingUpdated)
	{
		try 
		{
			Meter matchingMeterEntity = getMeterReadingEntity(meterReadingUpdated);
			if(matchingMeterEntity != null)
			{
			 //assumption only meterReading value of the existing row was incorrect. 
			 //Alternately the whole existing meterReading row should be deleted from DB and the incoming meterReading should be inserted
				matchingMeterEntity.setMeterReading(meterReadingUpdated.getMeterReading());
			   energyProcessorDAO.save(matchingMeterEntity);
			   return true;
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean deleteMeterReading(Meter meterReadingToDelete)
	{
		try 
		{
			Meter matchingMeterEntity = getMeterReadingEntity(meterReadingToDelete);
			if(matchingMeterEntity != null)
			{
			  energyProcessorDAO.delete(matchingMeterEntity);
			  return true;
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Meter getMeterReadingEntity(Meter meterReadingToMatch) throws Exception
	{
				Meter meter= null;	   
				List meterReadings = (ArrayList) energyProcessorDAO.findAll();
			   
			   Iterator metersItr = meterReadings.iterator();
			   
			   while(metersItr.hasNext())
			   {
				   meter = (Meter) metersItr.next();				   
				   //assumption meterId+month+profleName uniquely identifies a meterReading in the DB
				   if(
					  meter.getMeterId().equals(meterReadingToMatch.getMeterId()) &&
					  meter.getMonth().equals(meterReadingToMatch.getMonth()) &&
					  meter.getProfileName().equals(meterReadingToMatch.getProfileName())						   
					  )
				   {
					   return meter;
				   }
			   }
		return null;
	}	
}

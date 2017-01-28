package com.powerhouse.ws.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.powerhouse.ws.ApplicationTest;
import com.powerhouse.ws.model.Meter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class EnergyProcessorServiceTest extends ApplicationTest
{
	@Autowired
	private EnergyProcessorService service;
	private static boolean dataInitiaized = false;
	private Collection meterReadings = new ArrayList<Meter>() ;
	private static Meter meterReadingUpdated = new Meter() ;
	private static Meter meterReadingToDelete = new Meter() ;

	@Before
	public void setup()
	{	
		if(!dataInitiaized)
		{ 
			create2MeterReadings();
			setupMeterReadingDataForUpdateTest();
			setupMeterReadingDataForDeleteTest();
		}
	}
	
	@After
	public void tearDown()
	{	
	 //any steps to run after each test	
	}
	
	@Test
	public void deleteMeterReadingTest() throws Exception
	{	
		
		List listOfMetersBefore = (ArrayList)service.getAllMeters();
		System.out.println("****listOfMetersBefore.size() before "+listOfMetersBefore.size());
		
		
		System.out.println("****deleteMeterReadingTest meterReadingToDelete.getMeterId()  "+meterReadingToDelete.getMeterId());
		boolean deleted = service.deleteMeterReading(meterReadingToDelete);
		
		System.out.println("****deleted  "+deleted);
		
		//loop through all records and verify that the record no longer exists
		List listOfMeters = (ArrayList)service.getAllMeters();
		
		System.out.println("****listOfMeters.size() after "+listOfMeters.size());
		
		Meter meter = getMeterReadingEntity(meterReadingToDelete, listOfMeters);
		
		Assert.assertNull("Failure deleteMeterReadingTest - record did not get deleted", meter);
	}
	
	@Test
	public void createMeterReadingsTest() throws Exception
	{
		create2MoreMeterReadings();
		
		service.createMeterReadings(meterReadings);
		
		ArrayList listOfMeters = (ArrayList) service.getAllMeters();
		/*Assert.assertNotNull("Failure createMeterReadingsTest - returned object cannot be null", listOfMeters);
		Assert.assertEquals("Failure createMeterReadingsTest - incorrect count of records returned ", 4, listOfMeters.size());*/
		
		
		Meter meterToMatch = new Meter();
		meterToMatch.setMeterId("0006");
		meterToMatch.setMeterReading(14);
		meterToMatch.setMonth("DEC");
		meterToMatch.setProfileName("B");
		
		Meter meter = getMeterReadingEntity(meterToMatch, listOfMeters);
		
		Assert.assertNotNull("Failure createMeterReadingsTest - returned object cannot be null", meter);
		Assert.assertEquals("Failure createMeterReadingsTest - Create failed ", "0006", meter.getMeterId());
		Assert.assertEquals("Failure createMeterReadingsTest - Create failed ", "DEC", meter.getMonth());
		Assert.assertEquals("Failure createMeterReadingsTest - Create failed ", "B", meter.getProfileName());
		Assert.assertEquals("Failure createMeterReadingsTest - Create failed ", new Double(14).toString(), new Double(meter.getMeterReading()).toString());
		
	}
	
	@Test
	public void getMeterTest()
	{	
		Meter meterReading = service.getMeter(new Long(1));
		Assert.assertEquals("Failure getMeterTest - incorrect data retrieved ", "0001", meterReading.getMeterId());
	}
	
	@Test
	public void updateMeterReadingTest() throws Exception
	{	
		service.updateMeterReading(meterReadingUpdated);
		
		//find the record and check if the update value shows up
		List listOfMeters = (ArrayList)service.getAllMeters();
		
		Meter meter = getMeterReadingEntity(meterReadingUpdated, listOfMeters);
		
		Assert.assertNotNull("Failure updateMeterReadingTest - returned object cannot be null", meter);
		Assert.assertEquals("Failure updateMeterReadingTest - incorrect update ", new Double(20).toString(), new Double(meter.getMeterReading()).toString());		
	}
	
	
	
	
	//creates 2 records for unit testing purpose only
	private void  create2MeterReadings()
	{		
		Meter meter1 = new Meter();
		meter1.setMeterId("0001");
		meter1.setMeterReading(8);
		meter1.setMonth("JAN");
		meter1.setProfileName("A");		
		meterReadings.add(meter1);
		
		Meter meter2 = new Meter();
		meter2.setMeterId("0002");
		meter2.setMeterReading(10);
		meter2.setMonth("FEB");
		meter2.setProfileName("B");		
		meterReadings.add(meter2);
		
		service.createMeterReadings(meterReadings);
		
		dataInitiaized = true;
	}	
	
	private void  create2MoreMeterReadings()//data setup for the create unit text case
	{		
		Meter meter1 = new Meter();
		meter1.setMeterId("0005");
		meter1.setMeterReading(12);
		meter1.setMonth("NOV");
		meter1.setProfileName("A");		
		meterReadings.add(meter1);
		
		Meter meter2 = new Meter();
		meter2.setMeterId("0006");
		meter2.setMeterReading(14);
		meter2.setMonth("DEC");
		meter2.setProfileName("B");		
		meterReadings.add(meter2);
	}
	
	private void  setupMeterReadingDataForUpdateTest()//data setup for the update unit test case
	{
		meterReadingUpdated.setMeterId("0001");
		meterReadingUpdated.setMeterReading(20); //this field is updated and should show up 20 when the update test case is verified
		meterReadingUpdated.setMonth("JAN");
		meterReadingUpdated.setProfileName("A");
	}

	private void  setupMeterReadingDataForDeleteTest()//data setup for the delete unit test case
	{
		/*meterReadingToDelete.setMeterId("0001");
		meterReadingToDelete.setMeterReading(8);
		meterReadingToDelete.setMonth("JAN");
		meterReadingToDelete.setProfileName("A");*/
		
		meterReadingToDelete.setMeterId("0002");
		meterReadingToDelete.setMeterReading(10);
		meterReadingToDelete.setMonth("FEB");
		meterReadingToDelete.setProfileName("B");
	}
	
	
	
	
	private Meter getMeterReadingEntity(Meter meterReadingToMatch, List meterReadings) throws Exception
	{		   
		Meter meter= null;   
		
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

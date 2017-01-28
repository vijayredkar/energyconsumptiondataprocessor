package com.powerhouse.ws.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.powerhouse.ws.model.Meter;

@Component
public class MeterReadingValidator extends Validator
{
    private static Map meterReadingsMap = new ConcurrentHashMap<String, Meter>();//map of input data - meter readings
    private static Map filteredMeterReadingsMap = new ConcurrentHashMap<String, Meter>();//after validation. Contains Meter reading data good for DB insert
    private static Collection filteredMeterReadingsCollection = new ArrayList() ;//after validation. Contains Meter reading data good for DB insert
    private static Map calendarMonthMap = new HashMap<String, String>();
    private static Set setOfKeysSorted = new TreeSet<String>();
    private static Set badDataKeys  =  new TreeSet<String>();
    private static Set goodDataKeys  =  new TreeSet<String>();
        
	public static Collection<Meter> validateMeterReadings(Collection<Meter> meterReadings) throws Exception
	{		
		setupCalendarMonthMap();
    	createMeterReadingsMap(meterReadings);
    	validateReadingsPerMeter(setOfKeysSorted);
    	extractValidatedMeterReadings(setOfKeysSorted);
    	Collection<Meter> validatedCollection = createCollectionWithValidatedMeterReadings(goodDataKeys);
    	reset();
    	return validatedCollection;
	}
	
	//used to generate hashMap keys 
	private static void  setupCalendarMonthMap()
	{
		calendarMonthMap.put("JAN","A");
		calendarMonthMap.put("FEB","B");
		calendarMonthMap.put("MAR","C");
		calendarMonthMap.put("APR","D");
		calendarMonthMap.put("MAY","E");
		calendarMonthMap.put("JUN","F");
		calendarMonthMap.put("JUL","G");
		calendarMonthMap.put("AUG","H");
		calendarMonthMap.put("SEP","I");
		calendarMonthMap.put("OCT","J");
		calendarMonthMap.put("NOV","K");
		calendarMonthMap.put("DEC","L");
	}
	
	private static void createMeterReadingsMap(Collection<Meter> meterReadings) throws Exception
	{
		Iterator<Meter> meterReadingsItr = (Iterator<Meter>) meterReadings.iterator();
		while(meterReadingsItr.hasNext())
		{
			Meter meterReading = (Meter) meterReadingsItr.next();
			String key  = meterReading.getMeterId()+"_"+calendarMonthMap.get(meterReading.getMonth());//eg. 0001_1  for 0001 JAN meter reading
			meterReadingsMap.put(key, meterReading); //contains all input readings. Good and erroroneous			
			setOfKeysSorted.add(key);//keys stored sorted and non-duplicates
		}
	}
	
	//validate meter reading value of subsequent month should not be lesser than previous month 
		private static void validateMeterData(Set setOfKeysSorted) //input set has data pertaining to an individual meter only
		{	
			System.out.println("*****setOfKeysSorted.size() 2 "+setOfKeysSorted.size());	
			Set tempSetOfKeysSorted = setOfKeysSorted;		
			Iterator tempSetOfKeysSortedItr = setOfKeysSorted.iterator();		
			Iterator setOfKeysSortedItr = setOfKeysSorted.iterator();
					
			setOfKeysSortedItr.next();//increment once
			
			while(setOfKeysSortedItr.hasNext())
			{
				String keyPrevious = (String) tempSetOfKeysSortedItr.next();
				String keyNext = (String) setOfKeysSortedItr.next();
				
				Meter meterReading  = (Meter) meterReadingsMap.get(keyPrevious);
				Meter meterReadingNext  = (Meter) meterReadingsMap.get(keyNext);
				
				/*System.out.println("\n***** meterReading.getMeterReading()"+ meterReading.getMeterReading());
				System.out.println("***** meterReadingNext.getMeterReading()"+ meterReadingNext.getMeterReading());*/
				
				if(meterReadingNext.getMeterReading() < meterReading.getMeterReading())
				{   //subsequent month's reading value is lesser than previous hence mark this meter's data as bad
					badDataKeys.addAll(setOfKeysSorted);
					//System.out.println("************badDataKeys  "+badDataKeys.toString());
									
					return;//no more processing is required for this meter since one of the readings is determined to be in error. Process next meter
				}
			}
		}	
	
	private static void validateReadingsPerMeter(Set setOfKeysSorted ) throws Exception
	{
		Set subsetOfKeysSorted = new TreeSet<String>();		
		Iterator setOfKeysSortedItr = setOfKeysSorted.iterator();
		
		int counter = 1;
		while(setOfKeysSortedItr.hasNext())
		{		
			String key  = (String)setOfKeysSortedItr.next();
			subsetOfKeysSorted.add(key);
			
			counter++;
			
			if(counter == 13)//every meter will have 12 input readings only
			{
				//System.out.println("*****subsetOfKeysSorted.size() 1 "+subsetOfKeysSorted.size());
				validateMeterData(subsetOfKeysSorted);
				//reset to read the next set of 12 readings for another meter
				counter =1;
				subsetOfKeysSorted = new TreeSet<String>();
			}
		}//end while
	}
	
	//populate good data keys in to a separate set
	private static void extractValidatedMeterReadings(Set setOfKeysSorted) throws Exception
	{
		goodDataKeys.addAll(setOfKeysSorted);
		goodDataKeys.removeAll(badDataKeys);
	}
	
	private static Collection<Meter> createCollectionWithValidatedMeterReadings(Set goodDataKeys) throws Exception
	{
		String key = null;
		Iterator goodDataKeysItr = goodDataKeys.iterator();
		while(goodDataKeysItr.hasNext())
		{
			key = (String)goodDataKeysItr.next();
			Meter meter = (Meter) meterReadingsMap.get(key);
			filteredMeterReadingsMap.put(key, meter);			
			filteredMeterReadingsCollection.add(meter);//contains collection that is good for DB persist			
		}
		
		return filteredMeterReadingsCollection;
	}
	
	private static void reset() throws Exception
	{	
		 meterReadingsMap = new ConcurrentHashMap<String, Meter>();
	     filteredMeterReadingsMap = new ConcurrentHashMap<String, Meter>();
	     filteredMeterReadingsCollection = new ArrayList() ;
	     calendarMonthMap = new HashMap<String, String>();
	     setOfKeysSorted = new TreeSet<String>();
	     badDataKeys  =  new TreeSet<String>();
	     goodDataKeys  =  new TreeSet<String>();
	}
 }
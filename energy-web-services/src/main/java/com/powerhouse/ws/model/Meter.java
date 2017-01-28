package com.powerhouse.ws.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Meter 
{	
	@Id
	@GeneratedValue
	Long id;
	String meterId;
	String profileName;
	String month;
	double meterReading;
	
	public Meter()
	{
		
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}	
	
	
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public double getMeterReading() {
		return meterReading;
	}
	public void setMeterReading(double meterReading) {
		this.meterReading = meterReading;
	} 

}

package com.powerhouse.ws.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.powerhouse.ws.ApplicationTest;
import com.powerhouse.ws.model.Meter;
import com.powerhouse.ws.model.Profiles;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ProfileServiceTest extends ApplicationTest
{
	/*Providing a skeleton here 
	 * Could not complete the Profiles Junit cases due to lack of time.
	 * These will be very similar to the EnergyProcessorServiceTest.java JUnit
	 * 
	 */
	
	
	@Autowired
	private ProfilesService service;

	@Before
	public void setup()
	{
		
	}
	
	@After
	public void tearDown()
	{		
	}	
	
	@Test
	public void deleteProfileTest() throws Exception
	{
		
	}
	
	@Test
	public void createProfilesTest() throws Exception
	{	
	}
	
	@Test
	public void getProfileTest()
	{	
	}
	
	@Test
	public void updateProfileTest() throws Exception
	{		
	}
		
}

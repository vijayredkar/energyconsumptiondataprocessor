package com.powerhouse.ws.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powerhouse.ws.model.Profiles;
import com.powerhouse.ws.repository.EnergyProcessorDAO;
import com.powerhouse.ws.repository.ProfilesDAO;


@Service
public class ProfilesServiceImpl implements ProfilesService 
{
	@Autowired
	ProfilesDAO profilesDAO;
	
	@Override
	public Profiles getProfile(Long id) 
	{		
		return profilesDAO.findOne(id);
	}

	@Override
	public Collection<Profiles> getAllProfiles() 
	{
		Collection<Profiles> profiles = profilesDAO.findAll();
		return profiles;
	}
	
	public boolean createProfiles(Collection<Profiles> profiles) 
	{
		try {			
			profilesDAO.save(profiles);//persist entire JSON array in one shot			
			return true;
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean updateProfile(Profiles profileUpdated)
	{
		try 
		{
			Profiles matchingProfilesEntity = getProfilesEntity(profileUpdated);
			if(matchingProfilesEntity != null)
			{
			 //assumption only fraction value of the existing row was incorrect. 
			 //Alternately the whole existing profile row should be deleted from DB and the incoming profile should be inserted
				matchingProfilesEntity.setFraction(profileUpdated.getFraction());
			   profilesDAO.save(matchingProfilesEntity);
			   return true;
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean deleteProfile(Profiles profileToDelete)
	{
		try 
		{
			Profiles matchingProfilesEntity = getProfilesEntity(profileToDelete);
			if(matchingProfilesEntity != null)
			{
			  profilesDAO.delete(matchingProfilesEntity);
			  return true;
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Profiles getProfilesEntity(Profiles profileToMatch) throws Exception
	{
				Profiles profile= null;	   
				List profiles = (ArrayList) profilesDAO.findAll();
			   
			   Iterator profilesItr = profiles.iterator();
			   
			   while(profilesItr.hasNext())
			   {
				   profile = (Profiles) profilesItr.next();				   
				   //assumption month+profleName uniquely identifies a profile in the DB
				   if(
					  profile.getMonth().equals(profileToMatch.getMonth()) &&
					  profile.getProfile().equals(profileToMatch.getProfile())						   
					  )
				   {
					   return profile;
				   }
			   }
		return null;
	}	
}

package com.powerhouse.ws.service;

import java.util.Collection;

import com.powerhouse.ws.model.Profiles;

public interface ProfilesService 
{
	 Profiles getProfile(Long id);
	 Collection<Profiles> getAllProfiles();	
		 
	 boolean createProfiles(Collection<Profiles> profiles);
	 boolean updateProfile(Profiles profile);
	 
	 boolean deleteProfile(Profiles profileToDelete);
}

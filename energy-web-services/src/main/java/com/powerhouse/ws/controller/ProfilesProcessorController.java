package com.powerhouse.ws.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.powerhouse.ws.model.Profiles;
import com.powerhouse.ws.service.ProfilesService;

@RestController
public class ProfilesProcessorController 
{	
    @Autowired
	ProfilesService profilesService;
			
    //localhost:8080/profiles
	@RequestMapping
	(value="/profiles",
	method=RequestMethod.GET,
	produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Profiles>> getProfiles()
	{	
		Collection<Profiles> listOfProfiles = profilesService.getAllProfiles();
	    return new ResponseEntity(listOfProfiles, HttpStatus.OK);
	}
	
	//POSTMAN - localhost:8080/profiles/1
	@RequestMapping
	(value="/profiles/{id}",
	produces=MediaType.APPLICATION_JSON_VALUE,
	method=RequestMethod.GET)
	public ResponseEntity<Profiles> getProfilesById(@PathVariable("id") Long id)
	{		
		return new ResponseEntity<Profiles>(profilesService.getProfile(id),HttpStatus.OK);
	}
		
	//POSTMAN - localhost:8080/profiles/create
	//[{"month":"JAN","profile":"A","fraction":0.2},{"month":"FEB","profile":"B","fraction":0.1}]
		   @RequestMapping(value="/profiles/create",
				   		   method=RequestMethod.POST,
				   		   consumes=MediaType.APPLICATION_JSON_VALUE,
				   		   produces=MediaType.APPLICATION_JSON_VALUE)   
			public ResponseEntity<Collection<Profiles>> createProfiles(@RequestBody  Collection<Profiles> profiles)
			{  
			   if(profilesService.createProfiles(profiles))
			   {
				   return new ResponseEntity(HttpStatus.CREATED);//created successfully
			   }
			   
			   return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); //not created			   
			}

  //POSTMAN localhost:8080/profiles/delete 		   {"month":"JAN","profile":"A","fraction":0.7}		   
   @RequestMapping(value="/profiles/delete",
		   		   	method=RequestMethod.DELETE,
		   		   	consumes=MediaType.APPLICATION_JSON_VALUE)   
  public ResponseEntity<Profiles> deleteProfiles(@RequestBody Profiles profileToDelete)
  {
	   if(profilesService.deleteProfile(profileToDelete))
	   {
		   return new ResponseEntity(HttpStatus.NO_CONTENT);//deleted successfully
	   }
	   
	   return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); //not deleted
  }		   
		   
   //POSTMAN localhost:8080/profiles/update    {"month":"JAN","profile":"A","fraction":0.7}
   //in our scenario {id} does not make sense for update because the client will not know about this DB primary key
   @RequestMapping(value="/profiles/update",
		   		   method=RequestMethod.PUT,
		   		   consumes=MediaType.APPLICATION_JSON_VALUE,
		   		   produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Profiles> updateProfile(@RequestBody Profiles updatedProfile)
   {
	   if(profilesService.updateProfile(updatedProfile))
	   {
		   return new ResponseEntity(HttpStatus.OK);//update successfully
	   }
	   
	   return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); //not updated			   
	}
}

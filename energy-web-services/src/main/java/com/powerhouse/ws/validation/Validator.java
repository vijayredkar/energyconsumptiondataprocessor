package com.powerhouse.ws.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Validator 
{
	Logger logger = LoggerFactory.getLogger(Validator.class);
	
  public void validate()
  {
	  //provide any default/common validation routines	  
  }
}
package com.powerhouse.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.powerhouse.ws.model.Meter;

@Repository
public interface EnergyProcessorDAO extends JpaRepository<Meter, Long>
{

}

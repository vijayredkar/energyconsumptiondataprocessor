package com.powerhouse.ws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.powerhouse.ws.model.Profiles;

@Repository
public interface ProfilesDAO extends JpaRepository<Profiles, Long>
{

}

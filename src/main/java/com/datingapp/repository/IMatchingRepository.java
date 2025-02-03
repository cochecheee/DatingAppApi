package com.datingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.Matching;

@Repository
public interface IMatchingRepository extends JpaRepository<Matching, String>{
	
}

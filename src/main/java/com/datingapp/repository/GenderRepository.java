package com.datingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datingapp.entity.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, String>{

}

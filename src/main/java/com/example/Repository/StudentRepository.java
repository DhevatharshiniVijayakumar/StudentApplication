package com.example.Repository;

import com.example.entity.Studententity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Studententity, Long> {

}
package com.valterfi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.valterfi.jpa.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, String> {

}

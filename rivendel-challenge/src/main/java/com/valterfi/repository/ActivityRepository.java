package com.valterfi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.valterfi.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, String> {

}

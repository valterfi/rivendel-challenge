package com.valterfi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.valterfi.domain.Kind;

public interface KindRepository extends JpaRepository<Kind, String> {

}

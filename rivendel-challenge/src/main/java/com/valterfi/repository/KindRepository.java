package com.valterfi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.valterfi.jpa.domain.Kind;

public interface KindRepository extends JpaRepository<Kind, String> {
    
    List<Kind> findByDeleted(boolean deleted);
    
    Kind findOneByIdAndDeleted(String id, boolean deleted);

}

package com.cryptographic.studyCryptographic.repositories;

import com.cryptographic.studyCryptographic.entities.sensitiveData.SensitiveDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensitiveDataRepository extends JpaRepository<SensitiveDataEntity, Long> {

}

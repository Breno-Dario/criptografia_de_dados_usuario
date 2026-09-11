package com.cryptographic.studyCryptographic.services;

import com.cryptographic.studyCryptographic.dtos.SensitiveDataDto;
import com.cryptographic.studyCryptographic.entities.sensitiveData.SensitiveDataEntity;
import com.cryptographic.studyCryptographic.repositories.SensitiveDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensitiveDataService {

  @Autowired
  private SensitiveDataRepository sensitiveDataRepository;

  @Autowired
  private EncryptionService encryptionService;

  public SensitiveDataDto createSensitiveData(SensitiveDataDto sensitiveDataDto) {
    SensitiveDataEntity entity = new SensitiveDataEntity();
    entity.setUserDocument(encryptionService.encryptString(sensitiveDataDto.getUserDocument()));
    entity.setCreditCardToken(encryptionService.encryptString(sensitiveDataDto.getCreditCardToken()));
    entity.setValue(sensitiveDataDto.getValue());

    entity = sensitiveDataRepository.save(entity);

    return toDecryptedDto(entity);
  }

  public SensitiveDataDto getSensitiveData(Long idSensitiveData) {
    SensitiveDataEntity entity = sensitiveDataRepository.findById(idSensitiveData)
      .orElseThrow(() -> new RuntimeException("Sensitive data not found for id " + idSensitiveData));

    return toDecryptedDto(entity);
  }

  public SensitiveDataDto updateSensitiveData(Long idSensitiveData, SensitiveDataDto sensitiveDataDto) {
    SensitiveDataEntity entity = sensitiveDataRepository.findById(idSensitiveData)
      .orElseThrow(() -> new RuntimeException("Sensitive data not found for id " + idSensitiveData));

    entity.setUserDocument(encryptionService.encryptString(sensitiveDataDto.getUserDocument()));
    entity.setCreditCardToken(encryptionService.encryptString(sensitiveDataDto.getCreditCardToken()));
    entity.setValue(sensitiveDataDto.getValue());

    entity = sensitiveDataRepository.save(entity);

    return toDecryptedDto(entity);
  }

  public void deleteSensitiveData(Long idSensitiveData) {
    sensitiveDataRepository.deleteById(idSensitiveData);
  }

  private SensitiveDataDto toDecryptedDto(SensitiveDataEntity entity) {
    SensitiveDataDto dto = new SensitiveDataDto(entity);
    dto.setUserDocument(encryptionService.decryptString(entity.getUserDocument()));
    dto.setCreditCardToken(encryptionService.decryptString(entity.getCreditCardToken()));
    return dto;
  }
}

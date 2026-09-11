package com.cryptographic.studyCryptographic.dtos;

import com.cryptographic.studyCryptographic.entities.sensitiveData.SensitiveDataEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class SensitiveDataDto {
  private Long id;
  private String userDocument;
  private String creditCardToken;
  private Long value;

  public SensitiveDataDto(SensitiveDataEntity entity) {
    id = entity.getId();
    userDocument = entity.getUserDocument();
    creditCardToken = entity.getCreditCardToken();
    value = entity.getValue();
  }
}

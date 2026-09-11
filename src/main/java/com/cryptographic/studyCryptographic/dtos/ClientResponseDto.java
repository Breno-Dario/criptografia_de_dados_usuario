package com.cryptographic.studyCryptographic.dtos;

import com.cryptographic.studyCryptographic.entities.client.AcessLevel;
import com.cryptographic.studyCryptographic.entities.client.ClientEntity;

public record ClientResponseDto(Long id, String login, AcessLevel level) {

  public ClientResponseDto(ClientEntity entity) {
    this(entity.getId(), entity.getLogin(), entity.getLevel());
  }
}

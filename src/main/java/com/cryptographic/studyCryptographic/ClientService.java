package com.cryptographic.studyCryptographic.services;

import com.cryptographic.studyCryptographic.dtos.ClientResponseDto;
import com.cryptographic.studyCryptographic.dtos.RegisterDto;
import com.cryptographic.studyCryptographic.entities.client.ClientEntity;
import com.cryptographic.studyCryptographic.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public ClientResponseDto register(RegisterDto registerDto) {
    if (clientRepository.existsByLogin(registerDto.login())) {
      throw new RuntimeException("Login already in use: " + registerDto.login());
    }

    ClientEntity entity = new ClientEntity();
    entity.setLogin(registerDto.login());
    entity.setPassword(passwordEncoder.encode(registerDto.password()));
    entity.setLevel(registerDto.level());

    entity = clientRepository.save(entity);

    return new ClientResponseDto(entity);
  }
}

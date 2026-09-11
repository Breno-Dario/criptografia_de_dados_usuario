package com.cryptographic.studyCryptographic.controllers;

import com.cryptographic.studyCryptographic.dtos.ClientResponseDto;
import com.cryptographic.studyCryptographic.dtos.RegisterDto;
import com.cryptographic.studyCryptographic.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/auth")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @PostMapping("/register")
  public ResponseEntity<ClientResponseDto> register(@RequestBody RegisterDto registerDto) {
    ClientResponseDto response = clientService.register(registerDto);

    URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
      .path("/auth/{id}")
      .buildAndExpand(response.id()).toUri();

    return ResponseEntity.created(uri).body(response);
  }
}

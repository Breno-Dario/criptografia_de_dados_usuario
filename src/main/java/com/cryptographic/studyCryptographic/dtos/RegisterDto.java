package com.cryptographic.studyCryptographic.dtos;

import com.cryptographic.studyCryptographic.entities.client.AcessLevel;

public record RegisterDto(String login, String password, AcessLevel level) {

}

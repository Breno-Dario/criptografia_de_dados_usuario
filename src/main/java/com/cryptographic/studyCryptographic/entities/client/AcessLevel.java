package com.cryptographic.studyCryptographic.entities.client;

public enum AcessLevel {
  PREMIUM("premium"),
  COMMON("common");

 private String level;

 AcessLevel(String level) {
   this.level = level;
 }

 public String getLevel(){
   return level;
 }
}

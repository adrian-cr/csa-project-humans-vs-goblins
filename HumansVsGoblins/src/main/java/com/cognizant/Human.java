package com.cognizant;

import java.util.Arrays;

public class Human extends Character {
  String name;
  String emoji;
  public Human(String name, String emoji, int x, int y) {
    super(15, x, y);
    this.name = name;
    this.emoji = emoji;
  }
  
  /* Getters and Setters: */
  public String getName() {
    return name;
  }
  
  public void setEmoji(String emoji) {
    this.emoji = emoji;
  }
  
  @Override
  public String toString() {
    return isAlive()? emoji : "☠\uFE0F";
  }
}

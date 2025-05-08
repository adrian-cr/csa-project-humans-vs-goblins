package com.cognizant;

import java.util.Arrays;

public class Human extends Character {
  String name;
  String emoji;
  public Human(String name, String emoji, int x, int y) {
    super(15, x, y);
    this.name = name;
    this.emoji = emoji;
  }//Human() - constructor
  
  /* Getters: */
  public String getEmoji() {
    return emoji;
  }//getEmoji()
  public String getName() {
    return name;
  }//getName()

  /* Setters: */
  public void setEmoji(String emoji) {
    this.emoji = emoji;
  }//setEmoji()
  
  /* Class methods: */
  @Override
  public String toString() {
    return isInCombat()? "\uD83D\uDCA5" : emoji;
  }//toString()
  
}//Human

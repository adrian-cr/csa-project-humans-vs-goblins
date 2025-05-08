package com.cognizant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Goblin extends Character {
  static int count = 1;
  public Goblin(int x, int y) {
    super(new Random().nextInt(10)+2, x, y);
    count++;
  }//Goblin() - constructor
  
  /* Class methods: */
  @Override
  public String toString() {
    return isAlive()? (isInCombat()? "\uD83D\uDCA5" : "\uD83E\uDDDF\u200D♂\uFE0F") : "\uD83D\uDC80";
  }//toString()
  
}//Goblin

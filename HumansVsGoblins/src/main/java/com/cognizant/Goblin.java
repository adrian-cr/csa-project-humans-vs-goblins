package com.cognizant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Goblin extends Character {
  static int count = 1;
  public Goblin(int x, int y) {
    super(new Random().nextInt(10)+10, x, y);
    count++;
  }
  
  @Override
  public String toString() {
    return isAlive()? "\uD83E\uDDDF\u200D♂\uFE0F" : "☠\uFE0F";
  }
}

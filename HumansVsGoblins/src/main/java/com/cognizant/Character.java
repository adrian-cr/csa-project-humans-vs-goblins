package com.cognizant;

import java.util.List;

public abstract class Character {
  static int count = 1;
  final int id;
  int health;
  int x;
  int y;
  boolean inCombat = false;
  public Character(int health, int x, int y) {
    this.health = health;
    this.x = x;
    this.y = y;
    this.id = count;
    count++;
  }//Character() - constructor
  
  /* Getters: */
  public int getId() {
    return id;
  }//getId() - no setters for ids
  public int getHealth() {
    return health;
  }//getHealth()
  public int getX() {
    return x;
  }//getX()
  public int getY() {
    return y;
  }//getY()
  public boolean isInCombat() {
    return inCombat;
  }//isInCombat()
  
  /* Setters: */
  public void setHealth(int health) {
    if (health<0) {
      this.health=0;
      return;
    }//if - checks if health is critical
    if (isAlive()) this.health = health;
  }//setHealth()
  public void setInCombat(boolean inCombat, List<List<Character>> battleList) {//Battle list required to set true->false
    if (inCombat) {
      this.inCombat = inCombat;
      return;
    }//if - checks if param value is true
    for (List<Character> battle : battleList) //checks if character is in any pending battles
      if (battle.get(0).getId()==id || battle.get(1).getId()==id) return;
    this.inCombat = inCombat;
  }//setInCombat()
  public void setX(int x) {
    this.x = x;
  }//setX()
  public void setY(int y) {
    this.y = y;
  }//setY()
  
  /* Class Methods */
  public void attack(Character enemy, int harm) {
    enemy.setHealth(enemy.getHealth() - harm);
  }//attack()
  public boolean isAlive() {
    return this.health > 0;
  }//isAlive()
  public void move(int x, int y) {
    if (isAlive()) {
      setX(x);
      setY(y);
    }//if - checks if character is still alive
  }//move()
  public String toString(boolean ignoreStatus) {
    return ignoreStatus? this instanceof Human? ((Human) this).getEmoji() : "\uD83E\uDDDF\u200D♂\uFE0F" : null;
  }//toString()

}//Character

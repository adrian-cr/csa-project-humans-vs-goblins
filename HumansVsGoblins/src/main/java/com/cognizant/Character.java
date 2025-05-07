package com.cognizant;

import java.util.Random;

public abstract class Character {
  static int count;
  final int id;
  int health;
  boolean dead = false;
  int x;
  int y;
  boolean inCombat = false;
  public Character(int health, int x, int y) {
    this.health = health;
    this.x = x;
    this.y = y;
    this.id = count;
    count++;
  }
  
  /* Getters & Setters */
  
  public int getId() {
    return id;
  }// No setters for ids
  
  public int getHealth() {
    return health;
  }
  public void setHealth(int change) {
    if (change>=health) {
      dead=true;
      return;
    }
    health = change;
  }
  
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }
  public void setY(int y) {
    this.y = y;
  }
  
  public boolean isInCombat() {
    return inCombat;
  }
  public void setInCombat(boolean inCombat) {
    this.inCombat = inCombat;
  }
  
  /* Class Methods */
  public void attack(Character enemy, int harm) {
    enemy.setHealth(enemy.getHealth() - harm);
  }//attack()
  
  public void move(int x, int y) {
    setX(x);
    setY(y);
  }//move()
  
  public void die() {
  
  }
  
  public boolean isAlive() {
    return this.health > 0;
  }//isAlive()
  
  @Override
  public String toString() {
    return "Character{" +
            "id=" + id +
            ", health=" + health +
            ", x=" + x +
            ", y=" + y +
            ", inCombat=" + inCombat +
            '}';
  }
}

package com.cognizant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Land {
  private final int breadth;
  private final List<Character> characters;
  private final List<Human> humans;
  private final List<Goblin> goblins;
  private final List<List<Character>> battles;
  public Land(int breadth) {
    this.breadth = breadth;
    this.characters = new ArrayList<>();
    this.humans = new ArrayList<>();
    this.goblins = new ArrayList<>();
    this.battles = new ArrayList<>();
  }//Land() - constructor
  
  /* Getters: */
  public List<List<Character>> getBattles() {
    return battles;
  }//getBattles()
  public int getBreadth() { return breadth; }//getBreadth()
  public List<Character> getCharacters() {
    return characters;
  }//getCharacters()
  public List<Goblin> getGoblins() {
    return goblins;
  }//getGoblins()
  public List<Human> getHumans() {
    return humans;
  }//getHumans()
  
  /* Static methods */
  public static String getRandomDirection() {
    List<String> directions = asList("up", "down", "left", "right");
    Collections.shuffle(directions);
    return directions.get(0);
  }//getRandomDirection()
  
  /* Class Methods */
  public void addCharacter(Character character) {
    characters.add(character);
    if (character instanceof Human)//Checks character type
      humans.add((Human) character);
    else
      goblins.add((Goblin) character);
  }//addCharacter()
  public Character findCharacterAt(int x, int y) {
    for (Character c : characters)//iterates over character list for matches
      if (c.getX()==x && c.getY()==y) return c;
    return null;
  }//findCharacterAt()
  public Character findCharacterById(int id) {
    for (Character c : characters)//iterates over character list for matches
      if (c.getId()==id) return c;
    return null;
  }//findCharacterById()
  public boolean hasBattles() {
    return battles.size()>0;
  }//hasBattles()
  public int liveGoblins() {
    int ct = 0;
    for (Goblin g : goblins)//iterates over goblin list
      if (g.isAlive()) ct++;
    return ct;
  }//liveGoblins()
  public int liveHumans() {
    int ct = 0;
    for (Human h : humans)//iterates over human list
      if (h.isAlive()) ct++;
    return ct;
  }//liveHumans()
  public void moveAllGoblins() {
    List<Goblin> goblins = getGoblins();
    for (Goblin goblin : goblins) {
      String direction = getRandomDirection();
      moveCharacter(goblin, direction);
    }//for - iterates over goblin list
  }//moveAllGoblins()
  public boolean moveCharacter(Character character, String direction) {
    if (!character.isAlive()) return false; // checks if character is alive
    int x = character.getX();
      int y = character.getY();
      switch (direction) {
        case "up":
          if (y>0) y -= 1;
          break;
        case "down":
          if (y<breadth-1) y += 1;
          break;
        case "left":
          if (x>0) x -= 1;
          break;
        case "right":
          if (x<breadth-1) x += 1;
          break;
      }//switch - shifts coordinates
      if (character.getX()!=x || character.getY()!=y) {
        if (findCharacterAt(x, y)==null) {
          character.move(x, y);
          return true;
        } else {
          Character other = findCharacterAt(x, y);
          if(other.isAlive()) {
            String characterClass = character.getClass().getName();
            String otherClass = other.getClass().getName();
            if (!characterClass.equals(otherClass)) {
              character.setInCombat(true, null);
              other.setInCombat(true, null);
              List<Character> combatants = new ArrayList<>();
              combatants.add(character);
              combatants.add(other);
              battles.add(combatants);
            } //if - checks if character's class differs from other's
          }//if - checks if character found is alive
          return false;
        }//if-else - checks if space is not taken
      } //if - checks if human actually moved
      return false;
  }//moveCharacter()
  
  @Override
  public String toString() {
    String[] letterCoord = {"🅰", "🅱", "🅲", "🅳", "🅴", "🅵", "🅶", "🅷", "🅸", "🅹", "🅺", "🅻", "🅼", "🅽", "🅾", "🅿", "🆀", "🆁", "🆂", "🆃", "🆄", "🆅", "🆆", "🆇", "🆈", "🆉" };
    String str = "   ";
    for (int i=0; i<breadth; i++) str+= letterCoord[i] + "  ";
    str += "\n";
    for (int i=0; i<breadth; i++) {
      str += i+1 + (i+1<10? "  " : " ");
      for (int j=0; j<breadth; j++) {
        str += (findCharacterAt(j, i)==null? "\uD83C\uDF33" : findCharacterAt(j, i).toString()) + " ";
      }//for - generates row values
      str += "\n";
    }//for - generates rows
    return str;
  }
}

package com.cognizant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Land {
  private final int breadth;
  //List<Cell> cellList;
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
  }
  
  public int getBreadth() {
    return breadth;
  }
  public List<Character> getCharacters() {
    return characters;
  }
  public List<Goblin> getGoblins() {
    return goblins;
  }
  public List<Human> getHumans() {
    return humans;
  }
  
  /* Class Methods */
  
  public void addCharacter(Character character) {
    characters.add(character);
    if (character instanceof Human)
      humans.add((Human) character);
    else
      goblins.add((Goblin) character);
  }//addCharacter()
  
  public Character findCharacterAt(int x, int y) {
    for (Character c : characters)
      if (c.getX()==x && c.getY()==y) return c;
    return null;
  }//findCharacterAt()
  
  public Character findCharacterById(int id) {
    for (Character c : characters)
      if (c.getId()==id) return c;
    return null;
  }//findCharacterById()
  
  public boolean hasBattles() {
    return battles.size()>0;
  }
  
  public int liveGoblins() {
    int ct = 0;
    for (Goblin g : goblins)
      if (g.isAlive()) ct++;
    return ct;
  }//liveGoblins()
  
  public int liveHumans() {
    int ct = 0;
    for (Human h : humans)
      if (h.isAlive()) ct++;
    return ct;
  }//liveHumans()
  
  public void moveAllGoblins() {
    List<Goblin> goblins = getGoblins();
    for (Goblin goblin : goblins) {
      moveGoblin(goblin);
    }
  }
  
  public void moveGoblin(Goblin goblin) {
    List<String> directions = asList("up", "down", "left", "right");
    int x = goblin.getX();
    int y =goblin.getY();
    Collections.shuffle(directions);
    DirectionLoop:
    for (String e : directions) {
      switch (e) {
        case "up":
          if (y>0) y -= 1;
          break DirectionLoop;
        case "down":
          if (y<breadth-1) y += 1;
          break DirectionLoop;
        case "left":
          if (x>0) x -= 1;
          break DirectionLoop;
        case "right":
          if (x<breadth-1) x += 1;
          break DirectionLoop;
      }//switch - shifts coordinates
    }//for - tries random directions looking for an empty space
    if (goblin.getX()!=x || goblin.getY()!=y) {
      if (findCharacterAt(x, y)==null) {
        goblin.move(x, y);
      }
    }
  }//moveGoblin()
  
  public boolean moveHuman(Human human, String direction) {
    int x = human.getX();
    int y = human.getY();
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
    
    if (human.getX()!=x || human.getY()!=y) {
      //System.out.println("[" + human.getX() + "," + human.getY() + "]");
      if (findCharacterAt(x, y)==null) {
        human.move(x, y);
        return true;
      } else {
        Character other = findCharacterAt(x, y);
        if (other instanceof Goblin) {//checks if character is friend or foe
          human.setInCombat(true);
          other.setInCombat(true);
          List<Character> combatants = new ArrayList<>();
          combatants.add(human);
          combatants.add(other);
          battles.add(combatants);
        }
        return false;
      }//if-else - checks if space is not taken
    } //if - checks if human actually moved
    return false;
  }//moveHuman()
  
  
  
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

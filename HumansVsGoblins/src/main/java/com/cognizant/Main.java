package com.cognizant;

import java.lang.Character;
import java.util.*;

import static java.util.Arrays.asList;

public class Main {
  
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
  }
  
  public static String[] getEmojis() {
    return new String[]{"\uD83E\uDDDD\uD83C\uDFFC\u200D♂\uFE0F", "\uD83E\uDDDD\uD83C\uDFFD\u200D♀\uFE0F", "\uD83E\uDDD9\uD83C\uDFFE\u200D♀\uFE0F", "\uD83E\uDDD9\uD83C\uDFFE\u200D♂\uFE0F", "\uD83E\uDDDA\uD83C\uDFFB\u200D♂\uFE0F"};
  }//getEmojis()
  
  public static String getDirection(String direction) {
    direction = direction.toUpperCase();
    return direction.equals("N")? "up" :  direction.equals("E")? "right" : direction.equals("S")? "down" : direction.equals("W")? "left" : "";
  }
  
  public static String[] getNames() {
    return new String[]{"Eleroth", "Fryn", "Pholodyr", "Herrein", "Flint"};
  }//getEmojis()
  
  public static List<int[]> getRandCoords(int breadth, int sample) {
    List<int[]> coords = new ArrayList<>();
    for (int i=0; i<breadth; i++) {
      for (int j=0; j<breadth; j++) {
        coords.add(new int[]{j, i});
      }
    }//for
    Collections.shuffle(coords);
    return coords.subList(0, sample);
  }//getRandCoords()
  
  public static int getGoblinsByDifficulty(String difficulty) {
    return difficulty.equals("E")? 3 : difficulty.equals("M")? 8 : 20;
  }
  
  public static int getHumansByDifficulty(String difficulty) {
    return difficulty.equals("E")? 2 : difficulty.equals("M")? 3 : 5;
  }
  
  public static int getSizeByDifficulty(String difficulty) {
    return difficulty.equals("E")? 5 : difficulty.equals("M")? 10 : 15;
  }
  
  public static boolean isValidDifficulty(String difficulty) {
    return asList("E", "M", "H").contains(difficulty);
  }
  
  public static boolean isValidMove(String move, int humans) {
    if (move.length()!=2) return false;
    move = move.toUpperCase();
    char warrior = move.charAt(0);
    if (!java.lang.Character.isDigit(warrior) || Character.getNumericValue(warrior)<0 || Character.getNumericValue(warrior)>humans) return false;
    char direction = move.charAt(1);
    if (!asList("N", "E", "S", "W").contains(Character.toString(direction))) return false;
    return true;
  }
  
  public static boolean lost(Land arena) {
    return arena.liveHumans() == 0;
  }
  
  public static void printArena(Land land) {
    System.out.println(land.toString());
  }//printArena()
  
  public static void printDifficultyMenu() {
    System.out.print("Select game difficulty (E-easy, M-medium, H-hard): ");
  }
  
  public static void printDifficultySelection(String difficulty, int humans, int goblins, int size) {
    System.out.println(("\nYou have chosen " + (difficulty.equals("E")? "easy" : difficulty.equals("M")? "medium" : "hard") + " mode!").toUpperCase());
    System.out.println("(" + humans + " humans vs " + goblins + " goblins in a " + size + "-by-" + size + "-cell arena)");
  }
  
  public static void printInvalidOptionMessage() {
    System.out.println("\n*****Please enter a valid option*****\n".toUpperCase());
  }
  
  public static void printMoveMenu(int humans) {
    System.out.println("Enter a warrior number and a direction letter to make a move\n(e.g., \"1S\" = \"move " + getEmojis()[0] + " " + getNames()[0] + " south\")\n");
    System.out.println("Warriors".toUpperCase());
    for (int i=0; i<humans; i++) System.out.print((i+1) + ": " + getEmojis()[i] + " " + getNames()[i] + "  ");
    System.out.println();
    System.out.println("Direction".toUpperCase());
    System.out.println("N: North   E: East   S: South   W: West");
    System.out.println();
  }
  
  public static Land setGame(int size, int humans, int goblins) {
    Land land = new Land(size);
    int totalCharacters = humans + goblins;
    List<int[]> randCoords = getRandCoords(size, totalCharacters);
    List<int[]> humanCoords = randCoords.subList(0, humans);
    List<int[]> goblinCoords = randCoords.subList(humans, totalCharacters);
    String[] humanNames = {"Eleroth", "Fryn", "Pholodyr", "Herrein", "Flint"};
    for (int i=0; i<humans; i++) {
      int[] coords = humanCoords.get(i);
      land.addCharacter(new Human(humanNames[i], getEmojis()[i], coords[0], coords[1]));
    }//for - generate humans
    for (int i=0; i<goblins; i++) {
      int[] coords = goblinCoords.get(i);
      land.addCharacter(new Goblin(coords[0], coords[1]));
    }//for - generate goblins
    return land;
  }//SetGame()
  
  public static void wait(int ms) throws InterruptedException{
    Thread.sleep(ms);
  }
  
  public static boolean won(Land arena) {
    return arena.liveGoblins() == 0;
  }
  
  public static void main (String[] args) throws InterruptedException{
    /* INTRODUCTORY VISUALS */
    
    Scanner sc = new Scanner(System.in);
    clearScreen();
    System.out.println("\n\n-------------\uD83E\uDDDF\u200D♂\uFE0F GOBLINS \uD83E\uDDDF\u200D♂\uFE0F-------------\n\n");
    wait(2000);

    /* GAME SETUP */
    printDifficultyMenu();
    String difficulty = sc.next().toUpperCase();
    while (!isValidDifficulty(difficulty)) {
      printInvalidOptionMessage();
      printDifficultyMenu();
      difficulty = sc.next().toUpperCase();
    }
    int size = getSizeByDifficulty(difficulty);
    int goblins = getGoblinsByDifficulty(difficulty);
    int humans = getHumansByDifficulty(difficulty);
    printDifficultySelection(difficulty, humans, goblins, size);
    wait(2000);
    clearScreen();
    System.out.println("All set, let's play!");
    wait(2000);
    
    /* GAME START */
    clearScreen();
    Land arena = setGame(size, humans, goblins);
    
    /* GAME EXECUTION */
    ExecutionLoop:
    while (!won(arena) && !lost(arena)) {
      boolean fight = false;
      /* MOVEMENT LOOP */
      while(!fight) {
        clearScreen();
        arena.moveAllGoblins();
        printArena(arena);
        printMoveMenu(humans);
        String move = sc.next();
        while (!isValidMove(move, humans)) {
          clearScreen();
          printArena(arena);
          printInvalidOptionMessage();
          printMoveMenu(humans);
          move = sc.nextLine();
        }//checks for invalid move input
        int id = Integer.parseInt(move.substring(0, 1)) -1;
        Human warrior = (Human) arena.findCharacterById(id);
        String direction = getDirection(move.substring(1));
        while (!arena.moveHuman(warrior, direction)) {
          clearScreen();
          printArena(arena);
          printMoveMenu(humans);
          move = sc.nextLine();
          id = Integer.parseInt(move.substring(0, 1)) -1;
          warrior = (Human) arena.findCharacterById(id);
          direction = getDirection(move.substring(1));
        }//while - checks if warrior actually moved
      }//while - checks for triggered fights
    }//while - checks if user has won/lost
    clearScreen();
    if (won(arena)) System.out.println("You win!");
    else System.out.println("You have lost!");
    
  }//main()
  
}//Main
package com.cognizant;

import java.util.*;

import static com.cognizant.Playground.rollDice;
import static java.util.Arrays.asList;

public class Main {
  
  /* static methods: */
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
  }//clearScreen()
  public static String getCombatantName(Character combatant) {
    return combatant instanceof Human? ((Human) combatant).getName() : "Goblin";
  }//getCombatantName()
  public static String[] getEmojis() {
    return new String[]{"\uD83E\uDDDD\uD83C\uDFFC\u200D♂\uFE0F", "\uD83E\uDDDD\uD83C\uDFFD\u200D♀\uFE0F", "\uD83E\uDDD9\uD83C\uDFFE\u200D♀\uFE0F", "\uD83E\uDDD9\uD83C\uDFFE\u200D♂\uFE0F", "\uD83E\uDDDA\uD83C\uDFFB\u200D♂\uFE0F"};
  }//getEmojis()
  public static String getDirection(String direction) {
    direction = direction.toUpperCase();
    return direction.equals("N")? "up" :  direction.equals("E")? "right" : direction.equals("S")? "down" : direction.equals("W")? "left" : "";
  }//getDirection()
  public static List<int[]> getRandCoords(int breadth, int sample) {
    List<int[]> coords = new ArrayList<>();
    for (int i=0; i<breadth; i++) {
      for (int j=0; j<breadth; j++) {
        coords.add(new int[]{j, i});
      }//for - generates X coordinates
    }//for - generates Y coordinates
    Collections.shuffle(coords);
    return coords.subList(0, sample);
  }//getRandCoords()
  public static int getGoblinsByDifficulty(String difficulty) {
    return difficulty.equals("E")? 2 : difficulty.equals("M")? 5 : 9;
  }//getGoblinsByDifficulty()
  public static int getHumansByDifficulty(String difficulty) {
    return difficulty.equals("E")? 0 : difficulty.equals("M")? 4 : 6;
  }//getHumansByDifficulty()
  public static int getSizeByDifficulty(String difficulty) {
    return difficulty.equals("E")? 5 : difficulty.equals("M")? 8 : 10;
  }//getSizeByDifficulty()
  public static void insertDiceRollScreen(Scanner sc) {
    //Scanner sc = new Scanner(System.in);
    System.out.print("\n\n  \uD83C\uDFB2  Hit enter to roll the die.\n");
    String input = sc.nextLine();
    input = sc.nextLine();
    while(!isBlankInput(input)) {
      clearScreen();
      System.out.print("\n*****Do not press any other keys*****\n\n".toUpperCase());
      try {wait(2000);}catch (InterruptedException e) {};
      System.out.print("\n\n  \uD83C\uDFB2  Hit enter to roll the die\n".toUpperCase());
      input = sc.nextLine();
    }//while - checks for valid input
  }//insertDiceRollScreen()
  public static boolean isBlankInput(String input) {
    return input.equals("") && !input.equals(" ");
  }//isBlankInput()
  public static boolean isValidDifficulty(String difficulty) {
    return asList("E", "M", "H").contains(difficulty);
  }//isValidDifficulty()
  public static boolean isValidMove(String move, int humans) {
    if (move.length()!=2) return false;
    move = move.toUpperCase();
    String warrior = move.substring(0, 1);
    if (!warrior.matches("-?(0|[1-9]\\d*)") || Integer.parseInt(warrior)<0 || Integer.parseInt(warrior)>humans) return false;
    String direction = move.substring(1,2);
    if (!asList("N", "E", "S", "W").contains(direction)) return false;
    return true;
  }//isValidMove()
  public static boolean lost(Land arena) {
    return arena.liveHumans() == 0;
  }//lost()
  public static void printArena(Land land) {
    System.out.println(land.toString());
  }//printArena()
  public static void printBattleOutcome(Character attackee) {
    clearScreen();
    if (attackee.isAlive()) {
      System.out.println("\n\n  " + attackee.toString(true) + " " + getCombatantName(attackee) + " still stands!\n\n ⚡\uFE0F Remaining health: " + attackee.getHealth());
    } else {
      System.out.println("\n\n   ☠\uFE0F" + " " + getCombatantName(attackee) + " has been defeated!");
    }//if-else - checks if attackee survived
  }//printBattleOutcome()
  public static void printBattleScreen(Character attacker, Character attackee ) {
    System.out.println("          *** BATTLE! ***\n");
    try {wait(1000);} catch (InterruptedException e) {}
    System.out.println((" " + attacker.toString(true) + " " + getCombatantName(attacker) + " has attacked " + attackee.toString(true) + " " + getCombatantName(attackee) + "!\n").toUpperCase());
    System.out.println("  (⚡\uFE0F " + getCombatantName(attackee) + "'s health: " + attackee.getHealth() + ")");
  }//printBattleScreen()
  public static void printClosingMessage(Land arena) {
    if (won(arena)) {
      String str = "";
      for (String e : asList(getEmojis()).subList(0, arena.getHumans().size())) str += e + " ";
      System.out.println("\n\n   ***** " + str +  "You win! *****\n\n");
      return;
    } //checks if user won
    System.out.println("\n\n   ***** ☠\uFE0F  You lose! *****\n\n");
  }//printClosingMessage()
  public static void printDifficultyMenu() {
    System.out.print("Select game difficulty (E-easy, M-medium, H-hard): ");
  }//printDifficultyMenu()
  public static void printDifficultySelection(String difficulty, int humans, int goblins, int size) {
    System.out.println(("\nYou have chosen " + (difficulty.equals("E")? "easy" : difficulty.equals("M")? "medium" : "hard") + " mode!").toUpperCase());
    System.out.println("(" + humans + " humans vs " + goblins + " goblins in a " + size + "-by-" + size + "-cell arena)");
  }//printDifficultySelection()
  public static void printInvalidOptionMessage() {
    System.out.println("\n*****Please enter a valid option*****\n".toUpperCase());
  }//printInvalidOptionMessage()
  public static void printMoveMenu(List<Human> warriors) {
    printTurnMessage("humans");
    System.out.println("\uD83D\uDCDC: Enter a number (WARRIOR) and a letter (DIRECTION) to make a move (e.g., \"1S\" = \"" + warriors.get(0).getName() + " moves south\")\n");
    System.out.println("Warriors".toUpperCase());
    for (Human warrior : warriors) System.out.print(warrior.getId() + ": " + warrior.getEmoji() + warrior.getName() + (warrior.isAlive()? " (⚡\uFE0F: " + warrior.getHealth() + ")" : "") + "\n");//generates stats for every human character
    System.out.println();
    System.out.println("Directions".toUpperCase());
    System.out.println("N: ⬆\uFE0F North   E: ➡\uFE0F East   S: ⬇\uFE0F South   W: ⬅\uFE0F West");
    System.out.println();
  }//printMoveMenu()
  public static void printStartScreen() {
    System.out.println("\n\n-------------\uD83E\uDDDF\u200D♂\uFE0F GOBLINS \uD83E\uDDDF\u200D♂\uFE0F-------------\n\n");
  }//printStartScreen()
  public static void printRollOutcomeScreen(Character attacker, int outcome) {
    System.out.println("\n\n \uD83C\uDFB2  " + attacker.toString(true) + " " + getCombatantName(attacker) + "'s attack roll is: " + outcome);
  }//printRollOutcomeScreen()
  public static void printTurnMessage(String army) {
    System.out.println(("*** It's the " + army + "' turn! ***\n").toUpperCase());
  }//printTurnMessage()
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
    }//for - generates humans
    for (int i=0; i<goblins; i++) {
      int[] coords = goblinCoords.get(i);
      land.addCharacter(new Goblin(coords[0], coords[1]));
    }//for - generates goblins
    return land;
  }//setGame()
  public static void throwAttack(Character attacker, Character target) {
    int attackRoll = rollDice();
    clearScreen();
    printRollOutcomeScreen(attacker, attackRoll);
    try {Thread.sleep(4000);} catch (InterruptedException e) {}
    target.attack(target, attackRoll);
  }//throwAttack()
  public static void wait(int ms) throws InterruptedException{
    Thread.sleep(ms);
  }//wait()
  public static boolean won(Land arena) {
    return arena.liveGoblins() == 0;
  }//won()
  
  /* Main method: */
  public static void main (String[] args) throws InterruptedException{
    /* INTRODUCTORY VISUALS */
    Scanner sc = new Scanner(System.in);
    clearScreen();
    printStartScreen();
    wait(2000);

    /* GAME SETUP */
    printDifficultyMenu();
    String difficulty = sc.next().toUpperCase();
    while (!isValidDifficulty(difficulty)) {
      printInvalidOptionMessage();
      printDifficultyMenu();
      difficulty = sc.next().toUpperCase();
    }//while - validates difficulty
    int size = getSizeByDifficulty(difficulty);
    int goblins = getGoblinsByDifficulty(difficulty);
    int humans = getHumansByDifficulty(difficulty);
    printDifficultySelection(difficulty, humans, goblins, size);
    wait(3000);
    clearScreen();
    System.out.println("All set, let's play!");
    wait(2000);
    Land arena = setGame(size, humans, goblins);
    
    /* GAME EXECUTION */
    clearScreen();
    ExecutionLoop:
    while (!won(arena) && !lost(arena)) {
      boolean fight = false;
      MovementLoop:
      while(!arena.hasBattles()) {
        clearScreen();
        printArena(arena);
        printMoveMenu(arena.getHumans());
        String move = sc.next();
        while (!isValidMove(move, humans)) {
          clearScreen();
          printArena(arena);
          printInvalidOptionMessage();
          printMoveMenu(arena.getHumans());
          move = sc.nextLine();
        }//checks for invalid move input
        int id = Integer.parseInt(move.substring(0, 1));
        Human warrior = (Human) arena.findCharacterById(id);
        String direction = getDirection(move.substring(1));
        while (!arena.moveCharacter(warrior, direction)) {
          System.out.println(arena.hasBattles());
          if (arena.hasBattles())break MovementLoop;
          clearScreen();
          printArena(arena);
          printMoveMenu(arena.getHumans());
          move = sc.next();
          id = Integer.parseInt(move.substring(0, 1));
          warrior = (Human) arena.findCharacterById(id);
          direction = getDirection(move.substring(1));
        }//while - checks if warrior actually moved
        clearScreen();
        printArena(arena);
        printTurnMessage("goblins");
        wait(3000);
        arena.moveAllGoblins();
      }//while - checks for battles in the arena
      List<List<Character>> battles = arena.getBattles();
      int battleCount = battles.size();
      for (int i=0; i<battleCount; i++) {
        List<Character> battle = arena.getBattles().remove(0);
        Character attacker = battle.get(0);
        Character attackee = battle.get(1);
        clearScreen();
        printArena(arena);
        printBattleScreen(attacker, attackee);
        wait(3000);
        clearScreen();
        if (attacker instanceof Human) {
          insertDiceRollScreen(sc);
        }//if - checks if battle requires user input
        throwAttack(attacker, attackee);
        printBattleOutcome(attackee);
        wait(3000);
        if (!attackee.isAlive() && attackee instanceof Human) {
          ((Human)attackee).setEmoji("\uD83D\uDC80");
        }//if - sets object emoji to 💀 if defeated character is human
        if (attackee.isAlive()) {
          clearScreen();
          printBattleScreen(attackee, attacker);
          wait(3000);
          clearScreen();
          if (attackee instanceof Human) {
            insertDiceRollScreen(sc);
          }//if - checks if battle requires user input
          throwAttack(attackee, attacker);
          printBattleOutcome(attacker);
          if (!attacker.isAlive() && attacker instanceof Human) {
            ((Human)attacker).setEmoji("\uD83D\uDC80");
          }//if - sets object emoji to 💀 if defeated character is human
        }//if - checks if attackee is alive
        wait(3000);
        attacker.setInCombat(false, battles);
        attackee.setInCombat(false, battles);
      }//for - repeats process with every battle
    }//while - checks if user won/lost
    clearScreen();
    printClosingMessage(arena);
    
    /* END */
    
  }//main()
}//Main
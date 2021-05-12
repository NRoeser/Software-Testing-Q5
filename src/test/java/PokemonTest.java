package test.java;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.sql2o.*;

import main.java.Move;
import main.java.Pokemon;

public class PokemonTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Pokemon_instantiatesCorrectly_true() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(true, myPokemon instanceof Pokemon);
  }

  @Test
  public void getName_pokemonInstantiatesWithName_String() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals("Squirtle", myPokemon.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Pokemon.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfPokemonAreTheSame_true() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    Pokemon secondPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertTrue(firstPokemon.equals(secondPokemon));
  }
  
  @Test
  public void equals_returnsFalseIfNotInstanceOfPokemon() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertFalse(firstPokemon.equals(2));
  }

  @Test
  public void save_savesPokemonCorrectly_1() {
    Pokemon newPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    newPokemon.save();
    assertEquals(1, Pokemon.all().size());
  }

  @Test
  public void find_findsPokemonInDatabase_true() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    Pokemon savedPokemon = Pokemon.find(myPokemon.getId());
    assertTrue(myPokemon.equals(savedPokemon));
  }

  @Test
  public void addMove_addMoveToPokemon() {
    Move myMove = new Move("Punch", "Normal", 50.0, 100);
    myMove.save();
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    myPokemon.addMove(myMove);
    Move savedMove = myPokemon.getMoves().get(0);
    assertTrue(myMove.equals(savedMove));
  }

  @Test
  public void delete_deleteAllPokemonAndMovesAssociations() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    Move myMove = new Move("Bubble", "Water", 50.0, 100);
    myMove.save();
    myPokemon.addMove(myMove);
    myPokemon.delete();
    assertEquals(0, Pokemon.all().size());
    assertEquals(0, myPokemon.getMoves().size());
  }

  @Test
  public void searchByName_findAllPokemonWithSearchInputString_List() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    assertEquals(myPokemon, Pokemon.searchByName("squir").get(0));
  }

  @Test
  public void fighting_damagesDefender() {
    Pokemon myPokemon = new Pokemon("Squirtle", "Water", "Normal", "A cute turtle", 50.0, 12, 16, false);
    myPokemon.save();
    myPokemon.hp = 500;
    Move myMove = new Move("Bubble", "Water", 50.0, 100);
    myMove.attack(myPokemon);
    System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
        System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
        System.out.println(myPokemon.hp);
    myMove.attack(myPokemon);
    assertEquals(400, myPokemon.hp);
  }
  
  @Test
  public void searchByType() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    firstPokemon.save();
    List<Pokemon> listOfPokemon = Pokemon.searchByType("Water");
    assertTrue(listOfPokemon.contains(firstPokemon));
  }
  
  @Test
  public void testGetHp() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    firstPokemon.setHP(100);
    assertEquals(100, firstPokemon.getHp());
  }
  
  @Test
  public void testGetDescription() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals("A cute turtle", firstPokemon.getDescription());
  }
  
  @Test
  public void testGetWeight() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(50, firstPokemon.getWeight(), 0.001);
  }
  
  @Test
  public void testGetHeight() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(12, firstPokemon.getHeight());
  }
  
  @Test
  public void testGetEvolves() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(16, firstPokemon.getEvolves());
  }
  
  @Test
  public void testGetMega_evolves() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    assertEquals(false, firstPokemon.getMega_evolves());
  }
  
  @Test
  public void testGetImageName() {
    Pokemon firstPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
    firstPokemon.save();
    assertEquals("Squirtle.gif", firstPokemon.getImageName());
  }

}

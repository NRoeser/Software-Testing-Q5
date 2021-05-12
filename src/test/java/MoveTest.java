package test.java;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.sql2o.*;

import main.java.Move;
import main.java.Pokemon;

public class MoveTest {

	@Rule
	public DatabaseRule database = new DatabaseRule();

	@Test
	public void Move_instantiatesCorrectly_true() {
		Move myMove = new Move("Punch", "Normal", 50.0, 100);
		assertEquals(true, myMove instanceof Move);
	}

	@Test
	public void getName_moveInstantiatesWithName_String() {
		Move myMove = new Move("Solar Beam", "Normal", 50.0, 100);
		assertEquals("Solar Beam", myMove.getName());
	}

	@Test
	public void all_emptyAtFirst() {
		assertEquals(Move.all().size(), 0);
	}

	@Test
	public void equals_returnsTrueIfMovesAreTheSame_true() {
		Move firstMove = new Move("Punch", "Normal", 50.0, 100);
		Move secondMove = new Move("Punch", "Normal", 50.0, 100);
		assertTrue(firstMove.equals(secondMove));
	}

	@Test
	public void save_savesMoveCorrectly_1() {
		Move newMove = new Move("Punch", "Normal", 50.0, 100);
		newMove.save();
		assertEquals(1, Move.all().size());
	}

	@Test
	public void deleteMove() {
		Move newMove = new Move("Punch", "Normal", 50.0, 100);
		newMove.save();
		Move newMove2 = new Move("Kick", "Normal", 80.0, 100);
		newMove2.save();
		newMove2.delete();
		assertEquals(1, Move.all().size());
	}

	@Test
	public void sameMove() {
		Move newMove = new Move("Punch", "Normal", 50.0, 100);
		newMove.save();
		assertTrue(newMove.equals(newMove));
	}

	@Test
	public void notSameMove() {
		Move newMove = new Move("Punch", "Normal", 50.0, 100);
		newMove.save();
		Move newMove2 = new Move("Kick", "Normal", 80.0, 100);
		newMove2.save();
		assertFalse(newMove.equals(newMove2));
	}

	@Test
	public void notRealMoveComparison() {
		Move newMove = new Move("Punch", "Normal", 50.0, 100);
		newMove.save();
		assertFalse(newMove.equals(2));
	}

	@Test
	public void find_findsMoveInDatabase_true() {
		Move myMove = new Move("Punch", "Normal", 50.0, 100);
		myMove.save();
		Move savedMove = Move.find(myMove.getId());
		assertTrue(myMove.equals(savedMove));
	}

	@Test
	public void accuracy_checksAccuracy_false() {
		Move myMove = new Move("Flail", "Normal", 100.0, 0);
		myMove.save();
		assertFalse(myMove.hitCalculator());
	}

	@Test
	public void accuracy_checksAccuracy_true() {
		Move myMove = new Move("Flail", "Normal", 100.0, 100);
		myMove.save();
		assertTrue(myMove.hitCalculator());
	}

	@Test
	public void effectiveness_test_works() {
		Move myMove = new Move("Flail", "Water", 100.0, 100);
		myMove.save();
		Pokemon otherPokemon = new Pokemon("Flaming Rock Pikachu", "Rock", "Fire", "A flaming rat", 50.0, 12, 16,
				false);
		assertEquals(4, myMove.effectiveness(otherPokemon), 0);
	}

	@Test
	public void effectiveness_test_works_strongAgainstBoth_point25() {
		Move myMove = new Move("Flail", "Water", 100.0, 100);
		Pokemon otherPokemon = new Pokemon("Chia-Squirtle", "Water", "Grass",
				"A squirtle with chia-pet seeds on its shell", 50.0, 12, 16, false);
		assertEquals(.25, myMove.effectiveness(otherPokemon), 0);
	}

	@Test
	public void attack_method_does_damage() {
		Move myMove = new Move("Punch", "Normal", 60.0, 100);
		Pokemon otherPokemon = new Pokemon("Vanilla pokemon", "Normal", "None", "a normal pokemon", 50.0, 12, 16,
				false);
		assertEquals("The attack does 60,00 damage!", myMove.attack(otherPokemon));
	}

	@Test
	public void getPokemons_getPokemonFromMoveSearch() {
		Move myMove = new Move("Punch", "Normal", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Squirtle", "Water", "None", "A cute turtle", 50.0, 12, 16, false);
		myPokemon.save();
		myPokemon.addMove(myMove);
		Pokemon savedPokemon = myMove.getPokemons().get(0);
		assertTrue(myPokemon.equals(savedPokemon));
	}

	@Test
	public void normalUnEffective() {
		Move myMove = new Move("Punch", "Normal", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Rock Pokemon", "Rock", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void normalNoEffect() {
		Move myMove = new Move("Punch", "Normal", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Ghost Pokemon", "Ghost", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void fireSuperEffective() {
		Move myMove = new Move("Punch", "Fire", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Grass Pokemon", "Grass", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void fireUnEffective() {
		Move myMove = new Move("Punch", "Fire", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Fire Pokemon", "Fire", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void waterUnEffective() {
		Move myMove = new Move("Punch", "Water", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Water Pokemon", "Water", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void waterSuperEffective() {
		Move myMove = new Move("Punch", "Water", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Fire Pokemon", "Fire", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void electricSuperEffective() {
		Move myMove = new Move("Punch", "Electric", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Water Pokemon", "Water", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void electricUnEffective() {
		Move myMove = new Move("Punch", "Electric", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Electric Pokemon", "Electric", "None", "some random pokemon", 50, 12, 16,
				false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void electricNoEffect() {
		Move myMove = new Move("Punch", "Electric", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Ground Pokemon", "Ground", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void grassUnEffective() {
		Move myMove = new Move("Punch", "Grass", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Grass Pokemon", "Grass", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void grassSuperEffective() {
		Move myMove = new Move("Punch", "Grass", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Water Pokemon", "Water", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void iceUnEffective() {
		Move myMove = new Move("Punch", "Ice", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Ice Pokemon", "Ice", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void iceSuperEffective() {
		Move myMove = new Move("Punch", "Ice", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Grass Pokemon", "Grass", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void fightingNoEffect() {
		Move myMove = new Move("Punch", "Fighting", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Ghost Pokemon", "Ghost", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void fightingUnEffective() {
		Move myMove = new Move("Punch", "Fighting", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Flying Pokemon", "Flying", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void fightingSuperEffective() {
		Move myMove = new Move("Punch", "Fighting", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Ice Pokemon", "Ice", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void poisonUnEffective() {
		Move myMove = new Move("Punch", "Poison", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Poison Pokemon", "Poison", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void poisonSuperEffective() {
		Move myMove = new Move("Punch", "Poison", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Grass Pokemon", "Grass", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void groundNoEffect() {
		Move myMove = new Move("Punch", "Ground", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Flying Pokemon", "Flying", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void groundUnEffective() {
		Move myMove = new Move("Punch", "Ground", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Grass Pokemon", "Grass", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void groundSuperEffective() {
		Move myMove = new Move("Punch", "Ground", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Electric Pokemon", "Electric", "None", "some random pokemon", 50, 12, 16,
				false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void flyingUnEffective() {
		Move myMove = new Move("Punch", "Flying", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Electric Pokemon", "Electric", "None", "some random pokemon", 50, 12, 16,
				false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void flyingSuperEffective() {
		Move myMove = new Move("Punch", "Flying", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Grass Pokemon", "Grass", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void psychicUnEffective() {
		Move myMove = new Move("Punch", "Psychic", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Psychic Pokemon", "Psychic", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void psychicSuperEffective() {
		Move myMove = new Move("Punch", "Psychic", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Fighting Pokemon", "Fighting", "None", "some random pokemon", 50, 12, 16,
				false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void bugUnEffective() {
		Move myMove = new Move("Punch", "Bug", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Fire Pokemon", "Fire", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void bugSuperEffective() {
		Move myMove = new Move("Punch", "Bug", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Psychic Pokemon", "Psychic", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void rockUnEffective() {
		Move myMove = new Move("Punch", "Rock", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Fighting Pokemon", "Fighting", "None", "some random pokemon", 50, 12, 16,
				false);
		myPokemon.save();
		assertEquals(0.5, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void rockSuperEffective() {
		Move myMove = new Move("Punch", "Rock", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Fire Pokemon", "Fire", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void ghostSuperEffective() {
		Move myMove = new Move("Punch", "Ghost", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Ghost Pokemon", "Ghost", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void ghostNoEffect() {
		Move myMove = new Move("Punch", "Ghost", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Normal Pokemon", "Normal", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(0, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void dragonSuperEffective() {
		Move myMove = new Move("Punch", "Dragon", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Dragon Pokemon", "Dragon", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals(2, myMove.effectiveness(myPokemon), 0.001);
	}

	@Test
	public void effectivAttack() {
		Move myMove = new Move("Punch", "Dragon", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Dragon Pokemon", "Dragon", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals("The attack is super effective and did 100,00 damage!!", myMove.attack(myPokemon));
	}

	@Test
	public void noEffectAttack() {
		Move myMove = new Move("Punch", "Ghost", 50.0, 100);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Normal Pokemon", "Normal", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals("The attack is ineffective and did 0 damage.", myMove.attack(myPokemon));
	}

	@Test
	public void missedAttack() {
		Move myMove = new Move("Punch", "Dragon", 50.0, 0);
		myMove.save();
		Pokemon myPokemon = new Pokemon("Dragon Pokemon", "Dragon", "None", "some random pokemon", 50, 12, 16, false);
		myPokemon.save();
		assertEquals("The attack misses and did 0 damage...", myMove.attack(myPokemon));
	}

	@Test
	public void searchForMove() {
		Move myMove = new Move("Punch", "Dragon", 50.0, 0);
		myMove.save();
		List<Move> listOfMoves = Move.searchByName("Pu");
		assertTrue(listOfMoves.contains(myMove));
	}
	
	@Test
	public void searchForExactMove() {
		Move myMove = new Move("Punch", "Dragon", 50.0, 0);
		myMove.save();
		List<Move> listOfMoves = Move.searchByExactName("Punch");
		assertTrue(listOfMoves.contains(myMove));
	}
	
	@Test
	public void testGetType() {
		Move myMove = new Move("Punch", "Dragon", 50.0, 0);
		myMove.save();
		assertEquals("Dragon", myMove.getType());
	}

}

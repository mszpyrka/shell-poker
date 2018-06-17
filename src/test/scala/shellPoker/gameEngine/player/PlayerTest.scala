package shellPoker.gameEngine.player

import org.scalatest.FunSuite
import shellPoker.core.cards.{Card, King, Queen, Spades}

/** Tests for PositionHelper class. */
class PlayerTest extends FunSuite {

  {
    val mockPlayer: Player = new Player(null, null, new ChipStack(100))

    test("making normal bet") {
      mockPlayer.setBetSize(60)
      assert(mockPlayer.currentBetSize == 60)
      assert(mockPlayer.chipStack.chipCount == 40)
    }

    test("making additional bet should further decrease stack and increase bet") {
      mockPlayer.setBetSize(80)
      assert(mockPlayer.currentBetSize == 80)
      assert(mockPlayer.chipStack.chipCount == 20)
    }

    test("resetting current bet") {
      mockPlayer.clearCurrentBet()
      assert(mockPlayer.currentBetSize == 0)
    }

    test("making all-in bet") {
      mockPlayer.setBetSize(20)
      assert(mockPlayer.currentBetSize == 20)
      assert(mockPlayer.chipStack.chipCount == 0)
      assert(mockPlayer.isAllIn)
    }

    test("trying to bet more than a player has should throw NegativeChipCountException") {
      assertThrows[NegativeChipCountException](mockPlayer.setBetSize(21))
    }


    test("setting status should change player's status") {
      mockPlayer.setActive()
      assert(mockPlayer.isActive)

      mockPlayer.setAllIn()
      assert(mockPlayer.isAllIn)

      mockPlayer.setFolded()
      assert(mockPlayer.hasFolded)
    }
  }

  {
    val mockPlayer = new Player(null, null, new ChipStack(30))
    test("going all-in should set bet size to all player's chips and change status") {

      mockPlayer.goAllIn()
      assert(mockPlayer.isAllIn)
      assert(mockPlayer.chipStack.chipCount === 0)
      assert(mockPlayer.currentBetSize === 30)
    }

    test("removing chips from bet") {

      mockPlayer.removeChipsFromBet(15)
      assert(mockPlayer.currentBetSize === 15)
    }

    test("removing more chips from bet than present should throw NegativeChipCount exception") {

      assertThrows[NegativeChipCountException](mockPlayer.removeChipsFromBet(16))
    }
  }

  {
    val mockPlayer = new Player(null, null, new ChipStack(30))
    test("posting blind") {

      mockPlayer.postBlind(10)
      assert(mockPlayer.chipStack.chipCount === 20)
      assert(mockPlayer.currentBetSize === 10)
    }

    test("posting blind bigger than player's stack should put player all-in") {

      mockPlayer.clearCurrentBet()
      mockPlayer.postBlind(21)
      assert(mockPlayer.chipStack.chipCount === 0)
      assert(mockPlayer.currentBetSize === 20)
      assert(mockPlayer.isAllIn)
    }
  }

  {
    val mockPlayer = new Player(null, null, new ChipStack(30))
    test("posting blind equal to player's stack should put player all-in") {

      mockPlayer.clearCurrentBet()
      mockPlayer.postBlind(30)
      assert(mockPlayer.chipStack.chipCount === 0)
      assert(mockPlayer.currentBetSize === 30)
      assert(mockPlayer.isAllIn)
    }
  }

  {
    val mockPlayer = new Player(null, null, new ChipStack(30))
    test("initially player should have no hole cards") {

      assert(mockPlayer.holeCards === null)
    }

    test("adding cards should set player's hole cards to particular card instances") {

      mockPlayer.setHoleCards(Card(King, Spades), Card(Queen, Spades))
      assert(mockPlayer.holeCards === (Card(King, Spades), Card(Queen, Spades)))
    }

    test("resetting player's cards should change hole cards back to null") {

      mockPlayer.resetHoleCards()
      assert(mockPlayer.holeCards === null)
    }
  }
}
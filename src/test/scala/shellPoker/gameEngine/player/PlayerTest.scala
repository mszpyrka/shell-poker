package shellPoker.gameEngine.player

import org.scalatest.FunSuite

/** Tests for PositionHelper class. */
class PlayerTest extends FunSuite {

  val mockChipStack: ChipStack = new ChipStack(100)
  val mockPlayer: Player = new Player(mockChipStack)

  test("making normal bet should work"){
    mockPlayer.setBetSize(60)
    assert(mockPlayer.currentBetSize == 60)
    assert(mockPlayer.chipStack.chipCount == 40)
  }

  test("making additional bet should work"){
    mockPlayer.setBetSize(80)
    assert(mockPlayer.currentBetSize == 80)
    assert(mockPlayer.chipStack.chipCount == 20)
  }

  test("resetting current bet size should work"){
    mockPlayer.resetCurrentBet()
    assert(mockPlayer.currentBetSize == 0)
  }

  test("making all-in bet should work"){
    mockPlayer.setBetSize(20)
    assert(mockPlayer.currentBetSize == 20)
    assert(mockPlayer.chipStack.chipCount == 0)
    assert(mockPlayer.isAllIn)
  }

  test("trying to bet more than a player has should throw NegativeChipCountException"){
    assertThrows[NegativeChipCountException](mockPlayer.setBetSize(21))
  }


  test("setting status should work"){
    mockPlayer.setActive()
    assert(mockPlayer.isActive)
    
    mockPlayer.setAllIn()
    assert(mockPlayer.isAllIn)

    mockPlayer.setFolded()
    assert(mockPlayer.hasFolded)
  }

  val mockPlayer2 = new Player(new ChipStack(30))
  test("going all-in should set bet size to all player's chips and change status") {

    mockPlayer2.goAllIn()
    assert(mockPlayer2.isAllIn)
    assert(mockPlayer2.chipStack.chipCount === 0)
    assert(mockPlayer2.currentBetSize === 30)
  }

}
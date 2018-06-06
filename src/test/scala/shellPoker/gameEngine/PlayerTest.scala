package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for PositionHelper class. */
class PlayerTest extends FunSuite {

  val mockChipStack: ChipStack = new ChipStack(100)
  val mockPlayer: Player = new Player(mockChipStack)

  test("making normal bet should work"){
    mockPlayer.makeBet(60)
    assert(mockPlayer.currentBetSize == 60)
    assert(mockPlayer.chipStack.chipCount == 40)
  }

  test("making additional bet should work"){
    mockPlayer.makeBet(20)
    assert(mockPlayer.currentBetSize == 80)
    assert(mockPlayer.chipStack.chipCount == 20)
  }

  test("meseting current bet size should work"){
    mockPlayer.resetCurrentBet()
    assert(mockPlayer.currentBetSize == 0)
  }

  test("making allin bet should work"){
    mockPlayer.makeBet(20)
    assert(mockPlayer.currentBetSize == 20)
    assert(mockPlayer.chipStack.chipCount == 0)
    assert(mockPlayer.isAllIn)
  }

  test("trying to bet more than a player has should throw NegativeChipCountException"){
    assertThrows[NegativeChipCountException](mockPlayer.makeBet(1))
  }


  test("setting status should work"){
    mockPlayer.setActive()
    assert(mockPlayer.isActive)
    
    mockPlayer.setAllIn()
    assert(mockPlayer.isAllIn)

    mockPlayer.setFolded()
    assert(mockPlayer.hasFolded)
  }

}
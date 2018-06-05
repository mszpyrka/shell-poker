package shellPoker.gameEngine

import org.scalatest.FunSuite


/** Tests for PositionHelper class. */
class TableSeatTest extends FunSuite {

  val mockTableSeat: TableSeat = new TableSeat(0)
  val mockChipStack: ChipStack = new ChipStack(100)
  val mockPlayer: Player = new Player(mockChipStack)

  test("Seat should be initally empty"){
    assert(mockTableSeat.player == null)
  }

   test("isEmpty intially test"){
    assert(mockTableSeat.isEmpty)
  }

  test("testing addPlayer"){
    mockTableSeat.addPlayer(mockPlayer)
    assert(mockTableSeat.player != null)
  }

  test("isEmpty with player present seat test"){
    assert(!mockTableSeat.isEmpty)
  }

  test("testing removePlayer"){
    mockTableSeat.removePlayer
    assert(mockTableSeat.player == null)
  }

  test("testing isEmpty after removing player"){
    assert(mockTableSeat.isEmpty)
  }

}
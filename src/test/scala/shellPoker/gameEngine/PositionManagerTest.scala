package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for HighCard class. */
class PositionManagerTest extends FunSuite {



  test("PositionManager should throw NotEnoughPlayersException when there are not enough players at the table.") {

    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seat1 = new TableSeat
    seat1.addPlayer(playerMock)

    val seat2 = new TableSeat

    val seat3 = new TableSeat

    val seat4 = new TableSeat

    val seats = List(seat1, seat2, seat3, seat4)

    val positionManager = new PositionManager
    assertThrows[NotEnoughPlayersException](positionManager.pickRandomPositions(seats))

    seat1.removePlayer()

    assertThrows[NotEnoughPlayersException](positionManager.pickRandomPositions(seats))

    seat2.addPlayer(playerMock)
    seat3.addPlayer(playerMock)

    positionManager.pickRandomPositions(seats)

    seat2.removePlayer()

    assertThrows[NotEnoughPlayersException](positionManager.movePositions(seats))
  }



  test("Button and small blind should be the same position when there are two players at the table.") {

    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seat1 = new TableSeat
    seat1.addPlayer(playerMock)

    val seat2 = new TableSeat
    seat2.addPlayer(playerMock)

    val seat3 = new TableSeat

    val seat4 = new TableSeat

    val seats = List(seat1, seat2, seat3, seat4)

    val positionManager = new PositionManager
    positionManager.pickRandomPositions(seats)

    while(positionManager.dealerButton != seat1)
      positionManager.movePositions(seats)

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat1)
    assert(positionManager.bigBlind === seat2)

    positionManager.movePositions(seats)

    assert(positionManager.dealerButton === seat2)
    assert(positionManager.smallBlind === seat2)
    assert(positionManager.bigBlind === seat1)

    positionManager.movePositions(seats)

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat1)
    assert(positionManager.bigBlind === seat2)
  }



  test("Dealer -> SmallBlind -> BigBlind order should be maintained during standard game.") {

    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seat1 = new TableSeat
    seat1.addPlayer(playerMock)

    val seat2 = new TableSeat
    seat2.addPlayer(playerMock)

    val seat3 = new TableSeat

    val seat4 = new TableSeat
    seat4.addPlayer(playerMock)

    val seat5 = new TableSeat
    seat5.addPlayer(playerMock)

    val seats = List(seat1, seat2, seat3, seat4, seat5)

    val positionManager = new PositionManager
    positionManager.pickRandomPositions(seats)

    while(positionManager.dealerButton != seat1)
      positionManager.movePositions(seats)

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat2)
    assert(positionManager.bigBlind === seat4)

    positionManager.movePositions(seats)

    assert(positionManager.dealerButton === seat2)
    assert(positionManager.smallBlind === seat4)
    assert(positionManager.bigBlind === seat5)

    positionManager.movePositions(seats)

    assert(positionManager.dealerButton === seat4)
    assert(positionManager.smallBlind === seat5)
    assert(positionManager.bigBlind === seat1)
  }



  test("BigBling should be moved always forward, even when players number changes, " +
    "Dealer and SmallBlind should be adjusted according to BigBlind.") {

    ???
  }
}
package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for HighCard class. */
class PositionManagerTest extends FunSuite {



  test("PositionManager should throw NotEnoughPlayersException when there are not enough players at the table.") {

    val tableMock = new PokerTable(4)
    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seats: List[TableSeat] = tableMock.getEmptySeats

    val seat1 = seats(0)
    seat1.addPlayer(playerMock)

    val seat2 = seats(1)

    val seat3 = seats(2)

    val seat4 = seats(3)

    val positionManager = new PositionManager(tableMock)
    assertThrows[NotEnoughPlayersException](positionManager.pickRandomPositions())

    seat1.removePlayer()

    assertThrows[NotEnoughPlayersException](positionManager.pickRandomPositions())

    seat2.addPlayer(playerMock)
    seat3.addPlayer(playerMock)

    positionManager.pickRandomPositions()

    seat2.removePlayer()

    assertThrows[NotEnoughPlayersException](positionManager.movePositions())
  }



  test("Dealer and SmallBlind should indicate the same position when there are two players at the table.") {

    val tableMock = new PokerTable(4)
    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seats: List[TableSeat] = tableMock.getEmptySeats

    val seat1 = seats(0)
    seat1.addPlayer(playerMock)

    val seat2 = seats(1)
    seat2.addPlayer(playerMock)

    val seat3 = seats(2)

    val seat4 = seats(3)

    val positionManager = new PositionManager(tableMock)
    positionManager.pickRandomPositions()

    while(positionManager.dealerButton != seat1)
      positionManager.movePositions()

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat1)
    assert(positionManager.bigBlind === seat2)

    positionManager.movePositions()

    assert(positionManager.dealerButton === seat2)
    assert(positionManager.smallBlind === seat2)
    assert(positionManager.bigBlind === seat1)

    positionManager.movePositions()

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat1)
    assert(positionManager.bigBlind === seat2)
  }



  test("Dealer -> SmallBlind -> BigBlind order should be maintained during standard game.") {

    val tableMock = new PokerTable(5)
    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seats: List[TableSeat] = tableMock.getEmptySeats

    val seat1 = seats(0)
    seat1.addPlayer(playerMock)

    val seat2 = seats(1)
    seat2.addPlayer(playerMock)

    val seat3 = seats(2)

    val seat4 = seats(3)
    seat4.addPlayer(playerMock)

    val seat5 = seats(4)
    seat5.addPlayer(playerMock)

    val positionManager = new PositionManager(tableMock)
    positionManager.pickRandomPositions()

    while(positionManager.dealerButton != seat1)
      positionManager.movePositions()

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat2)
    assert(positionManager.bigBlind === seat4)

    positionManager.movePositions()

    assert(positionManager.dealerButton === seat2)
    assert(positionManager.smallBlind === seat4)
    assert(positionManager.bigBlind === seat5)

    positionManager.movePositions()

    assert(positionManager.dealerButton === seat4)
    assert(positionManager.smallBlind === seat5)
    assert(positionManager.bigBlind === seat1)
  }



  test("BigBling should always move forward, even when players number changes, " +
    "Dealer and SmallBlind should be adjusted according to BigBlind.") {

    val tableMock = new PokerTable(5)
    val stackMock = new ChipStack(0)
    val playerMock = new Player(stackMock)

    val seats: List[TableSeat] = tableMock.getEmptySeats

    val seat1 = seats(0)
    seat1.addPlayer(playerMock)

    val seat2 = seats(1)
    seat2.addPlayer(playerMock)

    val seat3 = seats(2)

    val seat4 = seats(3)
    seat4.addPlayer(playerMock)

    val seat5 = seats(4)
    seat5.addPlayer(playerMock)

    //val seats = List(seat1, seat2, seat3, seat4, seat5)

    val positionManager = new PositionManager(tableMock)
    positionManager.pickRandomPositions()

    while(positionManager.dealerButton != seat1)
      positionManager.movePositions()

    // seats: 1:+(D)  2:+(SB)  3:_  4:+(BB)  5:+

    assert(positionManager.dealerButton === seat1)
    assert(positionManager.smallBlind === seat2)
    assert(positionManager.bigBlind === seat4)

    seat3.addPlayer(playerMock)

    // seats: 1:+(D)  2:+(SB)  3:+  4:+(BB)  5:+

    positionManager.movePositions()

    // seats: 1:+  2:+  3:+(D)  4:+(SB)  5:+(BB)

    assert(positionManager.dealerButton === seat3)
    assert(positionManager.smallBlind === seat4)
    assert(positionManager.bigBlind === seat5)

    seat4.removePlayer()

    // seats: 1:+  2:+  3:+(D)  4:_(SB)  5:+(BB)

    positionManager.movePositions()

    // seats: 1:+(BB)  2:+  3:+  4:_(D)  5:+(SB)

    assert(positionManager.dealerButton === seat4)
    assert(positionManager.smallBlind === seat5)
    assert(positionManager.bigBlind === seat1)

    seat1.removePlayer()
    seat2.removePlayer()
    seat4.addPlayer(playerMock)

    // seats: 1:_(BB)  2:_  3:+  4:+(D)  5:+(SB)

    positionManager.movePositions()

    // seats: 1:_  2:_(SB)  3:+(BB)  4:+  5:+(D)

    assert(positionManager.dealerButton === seat5)
    assert(positionManager.smallBlind === seat2)
    assert(positionManager.bigBlind === seat3)



    positionManager.movePositions()

    // seats: 1:_  2:_(D)  3:+(SB)  4:+(BB)  5:+

    assert(positionManager.dealerButton === seat2)
    assert(positionManager.smallBlind === seat3)
    assert(positionManager.bigBlind === seat4)

    positionManager.movePositions()

    // seats: 1:_  2:_  3:+(D)  4:+(SB)  5:+(BB)

    assert(positionManager.dealerButton === seat3)
    assert(positionManager.smallBlind === seat4)
    assert(positionManager.bigBlind === seat5)

    seat4.removePlayer()

    // seats: 1:_  2:_  3:+(D)  4:_(SB)  5:+(BB)

    positionManager.movePositions()

    // seats: 1:_  2:_  3:+BB  4:_  5:+(SB/D)

    assert(positionManager.dealerButton === seat5)
    assert(positionManager.smallBlind === seat5)
    assert(positionManager.bigBlind === seat3)

    seat2.addPlayer(playerMock)

    // seats: 1:_  2:+  3:+BB  4:_  5:+(SB/D)

    positionManager.movePositions()

    // seats: 1:_  2:+(D)  3:+(SB)  4:_  5:+(BB)

    assert(positionManager.dealerButton === seat2)
    assert(positionManager.smallBlind === seat3)
    assert(positionManager.bigBlind === seat5)

    seat1.addPlayer(playerMock)
    seat2.removePlayer()
    seat3.removePlayer()

    // seats: 1:+  2:_(D)  3:_(SB)  4:_  5:+(BB)

    positionManager.movePositions()

    // seats: 1:+(BB)  2:_  3:_  4:_  5:+(SB/D)

    assert(positionManager.dealerButton === seat5)
    assert(positionManager.smallBlind === seat5)
    assert(positionManager.bigBlind === seat1)

    seat3.addPlayer(playerMock)

    // seats: 1:+(BB)  2:_  3:+  4:_  5:+(SB/D)

    positionManager.movePositions()

    // seats: 1:+(SB)  2:_  3:+(BB)  4:_  5:+(D)

    assert(positionManager.dealerButton === seat5)
    assert(positionManager.smallBlind === seat1)
    assert(positionManager.bigBlind === seat3)

  }
}
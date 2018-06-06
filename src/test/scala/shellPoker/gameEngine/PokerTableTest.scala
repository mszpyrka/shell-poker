package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for PokerTable class. */
class PokerTableTest extends FunSuite {


  test("PokerTable should initialize itself with given number of empty seats.") {

    val tableMock: PokerTable = new PokerTable(5)

    assert(tableMock.takenSeatsNumber === 0)
    assert(tableMock.activePlayersNumber === 0)
    assert(tableMock.getEmptySeats === tableMock.seats)
  }


  test("getEmptySeats method should return all empty seats at the table.") {

    val tableMock: PokerTable = new PokerTable(3)
    val stackMock: ChipStack = new ChipStack(0)
    val playerMock: Player = new Player(stackMock)

    val seats: List[TableSeat] = tableMock.seats

    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)

    assert(tableMock.getEmptySeats === List(seat0, seat1, seat2))

    seat0.addPlayer(playerMock)
    assert(tableMock.getEmptySeats === List(seat1, seat2))

    seat1.addPlayer(playerMock)
    seat2.addPlayer(playerMock)
    assert(tableMock.getEmptySeats === Nil)

    seat0.removePlayer()
    seat2.removePlayer()
    assert(tableMock.getEmptySeats === List(seat0, seat2))
  }


  test("getNetSeat method should iterate through seats in circular manner.") {

    val tableMock: PokerTable = new PokerTable(3)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)

    assert(tableMock.getNextSeat(seat0) === seat1)
    assert(tableMock.getNextSeat(seat1) === seat2)
    assert(tableMock.getNextSeat(seat2) === seat0)
  }


  test("getPreviousSeat method should iterate through seats backwards in circular manner.") {

    val tableMock: PokerTable = new PokerTable(3)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)

    assert(tableMock.getPreviousSeat(seat0) === seat2)
    assert(tableMock.getPreviousSeat(seat1) === seat0)
    assert(tableMock.getPreviousSeat(seat2) === seat1)
  }
}

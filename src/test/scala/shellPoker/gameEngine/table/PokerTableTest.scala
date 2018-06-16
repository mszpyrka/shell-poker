package shellPoker.gameEngine.table

import org.scalatest.FunSuite
import shellPoker.gameEngine.player.{ChipStack, Player}

/** Tests for PokerTable class. */
class PokerTableTest extends FunSuite {


  test("PokerTable should initialize itself with given number of empty seats.") {

    val tableMock: PokerTable = new PokerTable(5)

    assert(tableMock.takenSeatsNumber === 0)
    assert(tableMock.activePlayersNumber === 0)
    assert(tableMock.emptySeats === tableMock.seats)
  }


  test("getEmptySeats method should return all empty seats at the table.") {

    val tableMock: PokerTable = new PokerTable(3)

    val seats: List[TableSeat] = tableMock.seats

    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)

    assert(tableMock.emptySeats === List(seat0, seat1, seat2))

    seat0.createAndAddPlayer("", 0)
    assert(tableMock.emptySeats === List(seat1, seat2))

    seat1.createAndAddPlayer("", 0)
    seat2.createAndAddPlayer("", 0)
    assert(tableMock.emptySeats === Nil)

    seat0.removePlayer()
    seat2.removePlayer()
    assert(tableMock.emptySeats === List(seat0, seat2))
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


  test("getNextTakenSeat method should find next taken seat.") {

    val tableMock: PokerTable = new PokerTable(5)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)
    val seat3 = seats(3)
    val seat4 = seats(4)

    seat3.createAndAddPlayer("", 0)

    assert(tableMock.getNextTakenSeat(seat0) === seat3)
    assert(tableMock.getNextTakenSeat(seat2) === seat3)

    seat0.createAndAddPlayer("", 0)

    assert(tableMock.getNextTakenSeat(seat0) === seat3)
    assert(tableMock.getNextTakenSeat(seat3) === seat0)
    assert(tableMock.getNextTakenSeat(seat4) === seat0)
  }


  test("getNextTakenSeat method should return null if there is no other taken seat.") {

    val tableMock: PokerTable = new PokerTable(5)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)
    val seat3 = seats(3)
    val seat4 = seats(4)

    assert(tableMock.getNextTakenSeat(seat0) === null)
    assert(tableMock.getNextTakenSeat(seat4) === null)

    seat3.createAndAddPlayer("", 0)

    assert(tableMock.getNextTakenSeat(seat3) === null)
  }


  test("getNextActiveSeat method should find next seat containing active player.") {

    val tableMock: PokerTable = new PokerTable(5)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)
    val seat3 = seats(3)
    val seat4 = seats(4)

    seat3.createAndAddPlayer("", 0)
    seat3.player.setActive()

    assert(tableMock.getNextActiveSeat(seat0) === seat3)
    assert(tableMock.getNextActiveSeat(seat2) === seat3)

    seat0.createAndAddPlayer("", 0)
    seat0.player.setActive()
    seat2.createAndAddPlayer("", 0)
    seat2.player.setFolded()

    assert(tableMock.getNextActiveSeat(seat0) === seat3)
    assert(tableMock.getNextActiveSeat(seat3) === seat0)
    assert(tableMock.getNextActiveSeat(seat2) === seat3)
  }


  test("getNextActiveSeat method should return null if there is no other active player at the table.") {

    val tableMock: PokerTable = new PokerTable(5)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)
    val seat3 = seats(3)
    val seat4 = seats(4)

    assert(tableMock.getNextActiveSeat(seat0) === null)

    seat3.createAndAddPlayer("", 0)
    seat3.player.setActive()

    assert(tableMock.getNextActiveSeat(seat3) === null)

    seat4.createAndAddPlayer("", 0)
    seat4.player.setFolded()

    assert(tableMock.getNextActiveSeat(seat3) === null)
  }


  test("takenSeatsNumber method should return the number of taken seats.") {

    val tableMock: PokerTable = new PokerTable(3)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)

    assert(tableMock.takenSeatsNumber === 0)

    seat2.createAndAddPlayer("", 0)

    assert(tableMock.takenSeatsNumber === 1)

    seat0.createAndAddPlayer("", 0)
    seat1.createAndAddPlayer("", 0)

    assert(tableMock.takenSeatsNumber === 3)
  }


  test("activePlayersNumber method should return the number of seats taken by active players.") {

    val tableMock: PokerTable = new PokerTable(5)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)
    val seat3 = seats(3)
    val seat4 = seats(4)

    seat1.createAndAddPlayer("", 0)
    seat1.player.setActive()

    assert(tableMock.activePlayersNumber === 1)

    seat0.createAndAddPlayer("", 0)
    seat0.player.setActive()
    seat2.createAndAddPlayer("", 0)
    seat2.player.setFolded()

    assert(tableMock.activePlayersNumber === 2)

    seat0.player.setFolded()
    seat1.player.setFolded()

    assert(tableMock.activePlayersNumber === 0)
  }


  test("getSeatsRange should return properly created list of seats between two margin points.") {

    val tableMock: PokerTable = new PokerTable(4)

    val seats: List[TableSeat] = tableMock.seats
    val seat0 = seats(0)
    val seat1 = seats(1)
    val seat2 = seats(2)
    val seat3 = seats(3)

    assert(tableMock.getSeatsRange(seat0, seat3) === List(seat1, seat2))
    assert(tableMock.getSeatsRange(seat0, seat1) === Nil)
    assert(tableMock.getSeatsRange(seat3, seat1) === List(seat0))
    assert(tableMock.getSeatsRange(seat0, seat0) === List(seat1, seat2, seat3))
  }
}

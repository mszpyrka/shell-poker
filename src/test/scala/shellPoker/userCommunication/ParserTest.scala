package shellPoker.userCommunication

import shellPoker.gameEngine._



import org.scalatest.FunSuite

/** Tests for Parser object. */
class ParserTest extends FunSuite {


    test("Parser should parse correctly from string to Action") {

        assert(Parser.stringToAction("fold") == Fold)


        // assert(tableMock.takenSeatsNumber === 0)
        // assert(tableMock.activePlayersNumber === 0)
        // assert(tableMock.emptySeats === tableMock.seats)
  }

}
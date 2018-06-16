package shellPoker.userCommunication

import shellPoker.gameEngine._



import org.scalatest.FunSuite

/** Tests for Parser object. */
class ParserTest extends FunSuite {


    test("Parser should parse correctly from string to Action") {

        assert(Parser.stringToAction("fold") === Fold)

        assert(Parser.stringToAction("call") == Call)

        assert(Parser.stringToAction("all-in") == AllIn(0))
        assert(Parser.stringToAction("allin") == AllIn(0))
        assert(Parser.stringToAction("all in") == AllIn(0))

        assert(Parser.stringToAction("bet 100") == Bet(100))

        assert(Parser.stringToAction("raise 1") == Raise(1))

        assert(Parser.stringToAction("check") == Check)
  }

}
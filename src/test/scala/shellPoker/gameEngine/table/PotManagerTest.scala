package shellPoker.gameEngine.table

import org.scalatest.FunSuite
import shellPoker.gameEngine.player.{ChipStack, Player}

/** Tests for ChipStack class. */
class PotManagerTest extends FunSuite {

  val tableMock: PokerTable = new PokerTable(5)
  val player0 = new Player(new ChipStack(500))
  val player1 = new Player(new ChipStack(500))
  val player2 = new Player(new ChipStack(300))
  val player3 = new Player(new ChipStack(500))
  val player4 = new Player(new ChipStack(500))


  val seats = tableMock.seats
  seats(0).addPlayer(player0)
  seats(1).addPlayer(player1)
  seats(2).addPlayer(player2)
  seats(3).addPlayer(player3)
  seats(4).addPlayer(player4)

  val potManager = tableMock.potManager

  test("Collecting bets when no one is all in should reset players' bets " +
      "and not produce single pot.") {

    player0.setBetSize(50)
    player0.setFolded()

    player1.setBetSize(100)

    player2.setBetSize(100)

    player3.setFolded()

    player4.setBetSize(100)

    potManager.collectBets()

    assert(player0.currentBetSize === 0)
    assert(player0.chipStack.chipCount === 450)
    assert(player1.currentBetSize === 0)
    assert(player1.chipStack.chipCount === 400)
    assert(player2.currentBetSize === 0)
    assert(player2.chipStack.chipCount === 200)
    assert(player3.currentBetSize === 0)
    assert(player3.chipStack.chipCount === 500)
    assert(player4.currentBetSize === 0)
    assert(player4.chipStack.chipCount === 400)

    val pots = potManager.pots

    assert(pots.length === 1)

    val mainPot = pots.head

    assert(mainPot.size === 350)

    assert(mainPot.entitledPlayers === List(player1, player2, player4))
  }


  test("Collecting bets when some players with different stack are all in " +
    "should reset players' bets and produce side pot.") {

    player1.goAllIn()

    player2.goAllIn()

    player4.goAllIn()

    potManager.collectBets()

    assert(player0.currentBetSize === 0)
    assert(player0.chipStack.chipCount === 450)
    assert(player1.currentBetSize === 0)
    assert(player1.chipStack.chipCount === 0)
    assert(player2.currentBetSize === 0)
    assert(player2.chipStack.chipCount === 0)
    assert(player3.currentBetSize === 0)
    assert(player3.chipStack.chipCount === 500)
    assert(player4.currentBetSize === 0)
    assert(player4.chipStack.chipCount === 0)

    val pots = potManager.pots
    assert(pots.length === 2)

    val mainPot = pots.head
    assert(mainPot.size === 950)

    val sidePot = pots(1)
    assert(sidePot.size === 400)

    assert(mainPot.entitledPlayers === List(player1, player2, player4))
    assert(sidePot.entitledPlayers === List(player1, player4))
  }
}


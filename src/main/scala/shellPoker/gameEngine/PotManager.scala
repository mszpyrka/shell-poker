package shellPoker.gameEngine

/** Responsible for collecting bets from all players at the table after each finished betting round,
  * as well as managing main pot and potential side pots (also stores information about which pot
  * every player is entitled to take in case of winning the hand).
  *
  * @param table PokerTable instance at which the manager is working.
  */
class PotManager(table: PokerTable) {

  def pots: List[Pot] = _pots

  // Array of pots - the first element indicates the amount of chips in the main pot, other elements relate to side pots.
  private var _pots: List[Pot] = Nil

  /* Points to the pot that the chips should go into. */
  private var currentPot: Pot = _

  /* Collects all players' bets and places them in proper pots. */
  def collectBets(): Unit = {

    if (_pots == Nil)
      potsExtend()

    if (thereAreBetsToCollect) {
      val mainPotBet = inGamePlayerLowestBet
      collectCurrentPotBets(mainPotBet)
    }

    // Extends pots list until all players' bets are collected.
    while(thereAreBetsToCollect) {

      val sidePotBet = inGamePlayerLowestBet

      potsExtend()
      collectCurrentPotBets(sidePotBet)
    }

  }


  /* Checks if there are any bets left to collect. */
  private def thereAreBetsToCollect: Boolean = table.players.exists(_.currentBetSize > 0)


  /* Finds the lowest bet made by still active player. */
  private def inGamePlayerLowestBet: Int =
    table.players.filter(!_.hasFolded).
      map(_.currentBetSize).filter(_ > 0).min


  /* Creates new pot and initializes it with the list of players whose bets will go into it. */
  private def potsExtend(): Unit = {

    currentPot = new Pot(table.players.filter(_.currentBetSize > 0))
    _pots = _pots ++ List(currentPot)
  }


  /* Collects chips from all players that go into current pot. */
  private def collectCurrentPotBets(currentPotBet: Int): Unit = {

    for (player <- table.players) {

      if (player.currentBetSize > currentPotBet) {

        currentPot.addChips(currentPotBet)
        player.removeChipsFromBet(currentPotBet)
      }

      else {

        currentPot.addChips(player.currentBetSize)
        player.resetCurrentBet()
      }
    }
  }
}

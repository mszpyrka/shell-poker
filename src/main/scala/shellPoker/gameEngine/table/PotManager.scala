package shellPoker.gameEngine.table

import shellPoker.gameEngine.player.Player

/** Responsible for collecting bets from all players at the table after each finished betting round,
  * as well as managing main pot and potential side pots (also stores information about which pot
  * every player is entitled to take in case of winning the hand).
  *
  * @param table PokerTable instance at which the manager is working.
  */
class PotManager(table: PokerTable) {

  /* Array of pots - extended every time new side pot needs to be created. */
  private var _pots: List[Pot] = Nil

  /* Points to the pot that the chips should go into. */
  private var currentPot: Pot = _


  def pots: List[Pot] = _pots



  // ===================================================================================================================
  // Main API:
  // ===================================================================================================================


  /* Collects all players' bets and places them in proper pots. */
  def collectBets(): Unit = {

    // Extends pots list until all players' bets are collected.
    while(thereAreBetsToCollect) {

      val potBet = inGamePlayerLowestBet

      potsExtend()
      collectCurrentPotBets(potBet)
      potsReduce()
    }

  }

  /* Collects all players' bets and places them in proper pots. */
  def collectBets2(): Unit = {

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



  // ===================================================================================================================
  // Helper methods:
  // ===================================================================================================================

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



  private def potsReduce(): Unit = {

    // Checks if two lists contain the same players
    def containSamePlayers(playersA: List[Player], playersB: List[Player]): Boolean =
      playersA.toSet.equals(playersB.toSet)

    // Merges two consecutive pots into one
    def mergeWithNext(pot: Pot): Unit = {

      val potIndex = pots.indexOf(pot)
      val nextPot = pots(potIndex + 1)
      pot.addChips(nextPot.size)

      val leftPart = pots.take(potIndex)
      val rightPart = pots.drop(potIndex + 2)

      _pots = leftPart ++ List(pot) ++ rightPart
      currentPot = pots.last
    }

    // Finds a pot that can be merged with it's neighbour
    def potToMerge: Pot = {

      for ((potA, potB) <- pots zip pots.drop(1))
        if (containSamePlayers(potA.entitledPlayers, potB.entitledPlayers))
          return potA

      null
    }

    while (potToMerge != null)
      mergeWithNext(potToMerge)
  }


  /* Collects given amount of chips from all players and puts them into current pot. */
  private def collectCurrentPotBets(currentPotBet: Int): Unit = {

    val bettingPlayers = table.players.filter(_.currentBetSize > 0)

    if (bettingPlayers.length == 1) {

      bettingPlayers.head.regainBet()
      return
    }

    for (player <- bettingPlayers) {

      if (player.currentBetSize > currentPotBet) {

        currentPot.addChips(currentPotBet)
        player.removeChipsFromBet(currentPotBet)
      }

      else {

        currentPot.addChips(player.currentBetSize)
        player.clearCurrentBet()
      }
    }
  }
}

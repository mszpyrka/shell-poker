package shellPoker.gameEngine

/** Responsible for collecting bets from all players at the table after each finished betting round,
  * as well as managing main pot and potential side pots (also stores information about which pot
  * every player is entitled to take in case of winning the hand).
  *
  * @param table PokerTable instance at which the manager is working.
  */
class PotManager(table: PokerTable) {

  // Array of pots - the first element indicates the amount of chips in the main pot, other elements relate to side pots.
  private var _pots: List[Pot] = Nil

  // Index of currently considered pot (0 at the beginning, increments with every new side pot creation.
  private var currentPotIndex: Int = 0



  def collectBets(): Unit = ???
  ???
}

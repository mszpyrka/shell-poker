package shellPoker.gameEngine

/** Represents some settings used to initialize and manage poker game.
  *
  * @param startingStack Chips amount that each player gets when joining the table.
  * @param playersNumber Maximum number of players that can play.
  * @param smallBlindValue Value of small blind.
  * @param bigBlindValue Value of big blind.
  * @param decisionTime Time (in seconds) that each player is given in order to make single decision during the game.
  */
case class GameSettings (
  startingStack: Int,
  playersNumber: Int,
  smallBlindValue: Int,
  bigBlindValue: Int,
  decisionTime: Int
)

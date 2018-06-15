package shellPoker.gameEngine.handEnding

/** Case classes representing different types of ending single hand. */

sealed trait HandEndingType
case object ClassicShowdown extends HandEndingType
case object PlayersAllIn extends HandEndingType
case object AllFoldedToBet extends HandEndingType
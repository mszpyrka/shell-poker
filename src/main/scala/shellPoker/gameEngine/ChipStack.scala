package shellPoker.gameEngine

/** Represents a single chip stack.
  * Responsible for adding and removing chips from
  * player's chip stack
  */
class ChipStack(var initChipCount: Int){

  /*Adds given amount of chips to the chip stack*/
  def addChips(amount: Int): Unit = initChipCount += amount

  /*Removes given amount of chips from the chip stack*/
  def removeChips(amount: Int): Unit = initChipCount -= amount
}
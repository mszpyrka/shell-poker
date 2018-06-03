package shellPoker.gameEngine

class ChipStack(var chipCount: Int){

  def addChips(amount: Int): Unit = chipCount += amount

  def removeChops(amount: Int): Unit = chipCount -= amount
}
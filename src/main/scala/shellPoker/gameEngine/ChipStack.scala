package shellPoker.gameEngine

class ChipStack(var chipCount: Int){

  def addChips(amount: Int) = chipCount += amount

  def removeChops(amount: Int) = chipCount -= amount
}
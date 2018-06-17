package shellPoker.gameEngine.player

import shellPoker.gameEngine.table.TableSeat

class LocalTestPlayer(
    override val name: String,
    override val seat: TableSeat,
    override val chipStack: ChipStack)
  extends Player(name, seat, chipStack) {

  override def showCards(): Unit = {
    super.showCards()
    println(name + " shows " + holeCards)
  }

  override def muckCards(): Unit = {
    super.muckCards()
    println(name + " mucks")
  }
}

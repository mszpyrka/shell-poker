package shellPoker.gameEngine

sealed trait ShowdownAction
case object Mocked extends ShowdownAction
case object Showed extends ShowdownAction

class ShowdownStatus(seat: TableSeat, showdownAction: ShowdownAction, chipsWon: Int)

/** Responsible for ???
  */
class ShowdownManager {

  def getShowdownStatuses(table: PokerTable): List[ShowdownStatus] = ???
}

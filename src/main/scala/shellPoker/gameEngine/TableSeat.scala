package shellPoker.gameEngine

class TableSeat{
    var player: Option[Player] = None
    
    def isEmpty: Boolean = if (player == None) true else false
    
    def addPlayer(player: Player) = this.player = Some(player)

    def removePlayer = this.player = None
}
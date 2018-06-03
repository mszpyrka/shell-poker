package shellPoker.gameEngine

class TableSeat{
    private var player: Player = _
    
    def isEmpty: Boolean = player == null
    
    def addPlayer(player: Player): Unit = this.player = player

    def removePlayer(): Unit = this.player = null
}
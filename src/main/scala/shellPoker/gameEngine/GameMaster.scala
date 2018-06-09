package shellPoker.gameEngine

/** Main supervisor of a poker game.
  * Initializes the game and manages all game elements at single poker table.
  *
  * @param settings Settings of the game.
  */
class GameMaster(settings: GameSettings) {

  val actionManager = new ActionManager(initState)

  def stop(): Unit = ???
  def resume(): Unit = ???
  def restart(): Unit = ???


  private def initState: GameState = ???
}

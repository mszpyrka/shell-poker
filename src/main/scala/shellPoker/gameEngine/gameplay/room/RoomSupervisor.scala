package shellPoker.gameEngine.gameplay.room

import shellPoker.gameEngine.gameplay.hand.HandSupervisor
import shellPoker.gameEngine.table._


case class PlayerId(name: String, seatNumber: Int)


abstract class RoomSupervisor(gameSettings: GameSettings) {

    //Buffers needed to maintain 
    var currentPlayers: List[PlayerId] = Nil
    // var pendingPlayers: List[PlayerId] = Nil


    // ===================================================================================================================
    // Abstract API:
    // ===================================================================================================================

    // /** Adds player to the pendingPlayers list. */
    // def addPendingPlayer(pendingPlayer: PlayerId): Unit

    // /** Gets updated gameState including pending players, excluding no chip players
    //   * and updates currentPlayers and pendingPlayers buffers
    //   */
    protected def getUpdatedGameState(previousGameState: GameState, pendingPlayers: List[PlayerId]): GameState

    /** Gets initial state of the game, including initialPlayers list.
      * and updates currentPlayers and pendingPlayers buffers
     */
    protected def getInitialGameState(initialPlayers: List[PlayerId]): GameState

    /** Gets new supervisor for this room supervisor. */
    protected def getNewSupervisor(gameState: GameState): HandSupervisor


    // ===================================================================================================================
    // Concrete API:
    // ===================================================================================================================
    
    /** Runs main loop of the game */
    def run(initialPlayers: List[PlayerId]): Unit = {

        //Get initial game state
        val initialGameState: GameState = getInitialGameState(initialPlayers)

        //Get initial hand supervisor
        var handSupervisor: HandSupervisor = getNewSupervisor(initialGameState)


        //Run the game
        while(true) {
            //Run a single hand, and return ending GameState
            val handEndingState: GameState = handSupervisor.playSingleHand()

            //Get an updated game state, adding pending players and removing no chip players
            val updatedGameState = getUpdatedGameState(handEndingState)

            //Create new hand supervisor
            handSupervisor = getNewSupervisor(updatedGameState)
        }
        
    }
}
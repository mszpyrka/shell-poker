package shellPoker.gameEngine.player

import shellPoker.core.cards.Card

trait PlayerCommunicator {

  def showCards(): Unit = ??? //playerActor ! ShowCards // to be implemented xD
  def muckCards(): Unit = ??? //playerActor ! MuckCards  // this one as well LOL
}

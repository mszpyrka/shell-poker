package shellPoker.core.pokerHands

/** Used by classes which evaluate poker hands. */
trait HandEvaluator {

  /** Returns true only if given cards make expected hand. */
  def isMadeUpOf(cards: List[Card]): Boolean

}

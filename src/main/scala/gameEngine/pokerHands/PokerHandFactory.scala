package main.scala.gameEngine.pokerHands

object PokerHandFactory {

  val evaluators: List[HandEvaluator] = List(HighCard,
    Pair
  )
}

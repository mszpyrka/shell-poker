// package shellPoker.gameEngine

// import org.scalatest.FunSuite

// /** Tests for PositionHelper class. */
// class PositionHelperTest extends FunSuite {

//   val seatsAmount: Int = 4;
//   val mockTable: List[TableSeat] = (for(_ <- 0 to seatsAmount) yield new TableSeat).toList
//   val player1 = new Player(new ChipStack(1000))
//   val player2 = new Player(new ChipStack(1000))
//   val player3 = new Player(new ChipStack(1000))
//   mockTable(0).addPlayer(player1)
//   mockTable(1).addPlayer(player2)
//   mockTable(2).addPlayer(player3)

//   test("getNextSeat should return next seat"){
//     assert(PositionHelper.getNextSeat(mockTable.head, mockTable) == mockTable(1))
//     assert(PositionHelper.getNextSeat(mockTable(1), mockTable) == mockTable(2))
//     assert(PositionHelper.getNextSeat(mockTable.last, mockTable) == mockTable(0))
//   }

// }
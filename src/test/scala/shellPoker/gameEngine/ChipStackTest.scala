package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for Pair class. */
class ChipStackTest extends FunSuite {

  test("Removing more chips than present in the stack should throw NegativeChipCountException.") {

    val stack = new ChipStack(20)
    assertThrows[NegativeChipCountException](stack.removeChips(21))
    stack.removeChips(20) // This should work just fine.
    assert(stack.chipCount === 0)
  }

  test("Removing chips from stack should decrease chip count.") {

    val stack = new ChipStack(20)
    stack.removeChips(13)
    assert(stack.chipCount === 7)
  }

  test("Adding chips from stack should increase chip count.") {

    val stack = new ChipStack(20)
    stack.addChips(13)
    assert(stack.chipCount === 33)
  }

  test("Removing all chips should empty whole stack.") {

    val stack = new ChipStack(20)
    stack.removeAllChips()
    assert(stack.chipCount === 0)
  }
}


package shellPoker.gameEngine

import org.scalatest.FunSuite

/** Tests for ChipStack class. */
class PotTest extends FunSuite {

  val player1 = new Player(new ChipStack(100))
  val player2 = new Player(new ChipStack(100))
  val player3 = new Player(new ChipStack(100))

  val players = List(player1, player2, player3)


  val ru = scala.reflect.runtime.universe

  val mirror = ru.runtimeMirror(getClass.getClassLoader)
  val potMirror = mirror.reflect(new Pot(players))
  val _sizeTerm = ru.typeOf[Pot].decl(ru.TermName("_size")).asTerm.accessed.asTerm

  val _sizeFieldMirror = potMirror.reflectField(_sizeTerm)

  println(_sizeFieldMirror.get)
/*
  scala> val fmX = im.reflectField(fieldX)
  fmX: scala.reflect.runtime.universe.FieldMirror = field mirror for C.x (bound to C@5f0c8ac1)

  scala> fmX.get
  res0: Any = 2

  scala> fmX.set(3)

  val potClass:  = typeTag[Pot]

  val method1 = printerClass.getDeclaredMethod("printCodeName") // no parameters
  method1.setAccessible(true)
  method1.invoke(printer)

  val field = printerClass.getDeclaredField("codeName")
  field.setAccessible(true)
  field.set(printer, "Rejewski")

  method1.invoke(printer)

  val method2 = printerClass.getDeclaredMethod("printItem", classOf[Object], classOf[Boolean]) // Type T is object
  method2.setAccessible(true)
  method2.invoke(printer, text.asInstanceOf[Object], break.asInstanceOf[Object])
*/
  //output:
  //Scherbius
  //Rejewski
  //access granted
}


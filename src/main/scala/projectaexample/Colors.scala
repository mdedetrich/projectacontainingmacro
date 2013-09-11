package projectaexample

import sealedcontents.SealedContents

sealed abstract class Colors(val id:Long, val formattedName:String)
case object Red extends Colors(1,"Red")
case object Green extends Colors(2,"Green")
case object Yellow extends Colors(3,"Yellow")


object Colors {
  val all:Set[Colors] = SealedContents.values[Colors]
}

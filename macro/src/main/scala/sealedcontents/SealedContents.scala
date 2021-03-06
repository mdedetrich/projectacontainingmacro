package sealedcontents
import language.experimental.macros
import scala.reflect.macros.Context

/**
 * This provides a method to get all enumerations from an ADT (which is
 * an alternative from using the Enumeration type). Note that since this
 * is a macro, you have to explicitly supply the type signature
 *
 * Taken from http://stackoverflow.com/questions/13671734/iteration-over-a-sealed-trait-in-scala
 */

object SealedContents {
  def values[A]: Set[A] = macro values_impl[A]
  def values_impl[A: c.WeakTypeTag](c: Context) = {
    import c.universe._

    val symbol = weakTypeOf[A].typeSymbol

    if (!symbol.isClass) c.abort(
      c.enclosingPosition,
      "Must be a class or a trait."
    ) else if (!symbol.asClass.isSealed) c.abort(
      c.enclosingPosition,
      "Can only enumerate values of a sealed trait or class."
    ) else {
      val children = symbol.asClass.knownDirectSubclasses.toList

      if (!children.forall(_.isModuleClass)) c.abort(
        c.enclosingPosition,
        "All children must be objects."
      ) else c.Expr[Set[A]](
        Apply(Select(reify(Set).tree, newTermName("apply")), children.map(New(_)))
      )
    }
  }
}
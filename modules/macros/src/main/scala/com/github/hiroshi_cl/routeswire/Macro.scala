package routeswire

import play.api.http.HttpErrorHandler
import play.api.mvc.ControllerHelpers
import play.core.routing.GeneratedRouter

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

class Macro(val c: blackbox.Context) {

  import c.universe._

  /**
   * Routerの引数をwireする
   * @note debug出力やエラーが見やすいように無駄にA-正規形で出力している
   */
  private def wireRouterArgs(routerType: Type, errorHandler: Tree, prefix: Tree): Seq[Tree] = {
    val (heads, args) =
      routerType.decls.collectFirst({
        case m: MethodSymbol if m.isPrimaryConstructor => m.paramLists.flatten
      }).toSeq.flatten.map({
        case s if s.info.weak_<:<(typeOf[ControllerHelpers]) =>
          val controllerType = s.info
          val name = TermName(c.freshName(controllerType.toString)).encodedName.toTermName
          Seq(q"val $name: $controllerType = com.softwaremill.macwire.wire[$controllerType]") -> q"$name"
        case s if s.info.weak_<:<(typeOf[GeneratedRouter]) =>
          val subrouterType = s.info
          val heads :+ router = wireRouterArgs(subrouterType, errorHandler, prefix)
          val name = TermName(c.freshName(subrouterType.toString)).encodedName.toTermName
          (heads :+ q"val $name: $subrouterType = $router") -> q"$name"
        case s if s.name.toString == "errorHandler" => Seq.empty[Tree] -> errorHandler
        case s if s.name.toString == "prefix" => Seq.empty[Tree] -> prefix
      }).unzip
    heads.flatten :+ q"new $routerType(..$args)"
  }

  def wireRoutesImpl[R: WeakTypeTag](errorHandler: Tree, prefix: Tree): Tree = {
    val routerType = weakTypeOf[R]
    require(routerType.typeSymbol.isClass)
    q"{..${wireRouterArgs(routerType, errorHandler, prefix)}}"
  }
}

object Macro {
  // IntelliJは相変わらずmacro bundleをサポートしていないので赤くなる
  def wireRoutes[R <: GeneratedRouter](errorHandler: HttpErrorHandler, prefix: String): R = macro Macro.wireRoutesImpl[R]
}

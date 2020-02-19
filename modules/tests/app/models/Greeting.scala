package models

import play.api.libs.json.{Json, OFormat}

case class Greeting(id: Int = -1, message: String, name: String)

object Greeting {
  implicit val GreetingFormat: OFormat[Greeting] = Json.format[Greeting]
}

package services

class GreetingService {

  def greetingMessage(language: String): String = language match {
    case "it" => "Messi"
    case _ => "Hello"
  }

}

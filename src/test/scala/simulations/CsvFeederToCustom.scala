package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

<<<<<<< HEAD
class CsvFeederToCustom extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  var idNumbers = (1 to 10).iterator

  val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))


  def getSpecificVideoGame() = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Get specific video game")
          .get("videogames/${gameId}")
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Csv Feeder test")
    .exec(getSpecificVideoGame())



  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
=======
class CsvFeederToCustom extends Simulation{


  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")
    .proxy(Proxy("localhost", 9090)) // use the proxy to verify that different calls are made to the videogame endpoint

  var idNumbers = (1 to 10).iterator //creates a param with nums 1 through 10

  //this takes the value of the iterator variable and applies it to the gameId variable
  val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))



  def getSpecificVideoGame() = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Get Specific Video Game")
          .get("videogames/${gameId}")
          .check(status.is(200)))
        .pause(3)
    }
  }

  val scn = scenario("CSV Feeder Test")
    .exec(getSpecificVideoGame())

    setUp(
      scn.inject(atOnceUsers(users = 1))
    ).protocols(httpConf)


>>>>>>> temp-branch

}

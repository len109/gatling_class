package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

<<<<<<< HEAD
class CodeReuseWithObjects extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")


  def getAllVideoGames() = {
    repeat(3) {
      exec(http("Get all video games - 1st call")
=======
class CodeReuseWithObjects extends Simulation{


  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
  .header(name="Accept", value="application/json")

  /*
  val scn = scenario(scenarioName="Video Game DB 3 Calls")

  .exec(http("Get All Video Games First Call")
  .get("videogames"))

  .exec(http("Get Specific Video Game")
  .get("videogames/1"))

  .exec(http("Get All Video Games Second Call")
  .get("videogames"))*/



  def getAllVideoGames() = {
    repeat(3){
      exec(http("Get All Video Games First Call")
>>>>>>> temp-branch
        .get("videogames")
        .check(status.is(200)))
    }
  }

<<<<<<< HEAD
  def getSpecificVideoGame() = {
    repeat(5) {
      exec(http("Get specific game")
=======
  def getSpecificGame() = {
    repeat(2) {
      exec(http("Get Specific Video Game")
>>>>>>> temp-branch
        .get("videogames/1")
        .check(status.in(200 to 210)))
    }
  }

<<<<<<< HEAD
  val scn = scenario("Code reuse")
      .exec(getAllVideoGames())
      .pause(5)
      .exec(getSpecificVideoGame())
      .pause(5)
      .exec(getAllVideoGames())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

=======
  val scn = scenario ("Code Resuse")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificGame())
    .pause(5)
    .exec(getAllVideoGames())



  setUp(
  scn.inject(atOnceUsers(users = 1))
  ).protocols(httpConf)


>>>>>>> temp-branch
}

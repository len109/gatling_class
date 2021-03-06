package simulations

import io.gatling.core.Predef._
<<<<<<< HEAD
=======
import io.gatling.core.structure.ChainBuilder
>>>>>>> temp-branch
import io.gatling.http.Predef._

import scala.concurrent.duration._

<<<<<<< HEAD
class FixedDurationLoadSimulation extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  def getAllVideoGames() = {
    exec(
      http("Get all video games")
=======
class FixedDurationLoadSimulation extends Simulation{


  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")


  def getAllVideoGames(): ChainBuilder = {
    exec(
      http("Get All Video Games")
>>>>>>> temp-branch
        .get("videogames")
        .check(status.is(200))
    )
  }

<<<<<<< HEAD
  def getSpecificGame() = {
    exec(
      http("Get Specific Game")
=======
  def getSpecificGame(): ChainBuilder = {
    exec(
      http("Get Specific Name")
>>>>>>> temp-branch
        .get("videogames/2")
        .check(status.is(200))
    )
  }

<<<<<<< HEAD
  val scn = scenario("Fixed Duration Load Simulation")
    .forever() {
=======


  val scn2 = scenario("Basic2")


  val scn = scenario("Fixed Duration Load Simulation")
    .forever(){
>>>>>>> temp-branch
      exec(getAllVideoGames())
        .pause(5)
        .exec(getSpecificGame())
        .pause(5)
        .exec(getAllVideoGames())
    }


  setUp(
    scn.inject(
      nothingFor(5 seconds),
      atOnceUsers(10),
<<<<<<< HEAD
      rampUsers(50) during (30 second)
    ).protocols(httpConf.inferHtmlResources())
  ).maxDuration(1 minute)

=======
      rampUsers(50) during (30 seconds)
    ).protocols(httpConf.inferHtmlResources())
  ).maxDuration(1 minute)


>>>>>>> temp-branch
}

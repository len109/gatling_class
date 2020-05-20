package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
<<<<<<< HEAD

import scala.concurrent.duration._

class RuntimeParameters extends Simulation {

  private def getProperty(propertyName: String, defaultValue: String) = {
=======
import scala.concurrent.duration._

class RuntimeParameters extends Simulation{

  private def getProperty(propertyName: String, defaultValue: String): String = {
>>>>>>> temp-branch
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

<<<<<<< HEAD
  def userCount: Int = getProperty("USERS", "5").toInt
  def rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  def testDuration: Int = getProperty("DURATION", "60").toInt
=======
>>>>>>> temp-branch

  before {
    println(s"Running test with ${userCount} users")
    println(s"Ramping users over ${rampDuration} seconds")
    println(s"Total test duration: ${testDuration} seconds")
  }

<<<<<<< HEAD
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  def getAllVideoGames() = {
    exec(
      http("Get all video games")
        .get("videogames")
        .check(status.is(200))
    )
  }

  val scn = scenario("Get all video games")
=======
  def userCount: Int = getProperty("USERS", "5").toInt
  def rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  def testDuration: Int = getProperty("DURATION", "60").toInt



  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")


  def getAllVideoGames() = {
    exec(
      http("Get All Video Games")
        .get("videogames/")
        .check(status.is (200))
    )
  }


  val scn = scenario("Get All Video Games")
>>>>>>> temp-branch
    .forever() {
      exec(getAllVideoGames())
    }

<<<<<<< HEAD
=======
  //This is the original setup with hardcoded properties of the test.
  //  setUp(
  //    scn.inject(
  //      nothingFor(5 seconds),
  //      rampUsers(1) during (1 second)
  //    )
  //  ).protocols(httpConf).maxDuration(20 seconds)

  //This setup uses the params specified above and in the command line
>>>>>>> temp-branch
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(userCount) during (rampDuration second)
    )
<<<<<<< HEAD
  ).protocols(httpConf)
    .maxDuration(testDuration seconds)
=======
  ).protocols(httpConf).maxDuration(testDuration seconds)

>>>>>>> temp-branch

}

package simulations

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class FinalChallenge extends Simulation {


  private def getProperty(propertyName: String, defaultValue: String): String = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }


  before {
    println(s"Running test with ${userCount} users")
    println(s"Ramping users over ${rampDuration} seconds")
    println(s"Total test duration: ${testDuration} seconds")
  }

  def userCount: Int = getProperty("USERS", "1").toInt
  def rampDuration: Int = getProperty("RAMP_DURATION", "1").toInt
  def testDuration: Int = getProperty("DURATION", "25").toInt


  val httpConf = http.baseUrl(url = "http://localhost:8080/app/")
    .header(name = "Accept", value = "application/json")


  var idNumbers = (26 to 30).iterator

  val rnd = new Random() //need to import the random util for this. This is a random number generator.
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val now = LocalDate.now()

  ////this returns the number of characters in the random number
  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate : LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(3000)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumbers.next(), //next takes the next value in the list
    "name" -> ("Game-" + randomString(5)), //the name will be "Game-5 random numbers
    //  "releaseDate" -> getRandomDate(now, rnd),
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(6)),
    "rating" -> ("Rating-" + randomString(4))
  ))



  def getAllVideoGames(): ChainBuilder = {
    exec(
      http("Get All Video Games")
        .get("videogames/")
        .check(status.is("200"))
        .check(jsonPath("$[1].id").saveAs("gameid"))
    )
  }


 // def addVideoGame(): ChainBuilder = {
  //  exec(
  //    http("Add Video Game")
  //      .post("videogames/")
  //      .check(status.is(200))
  //  )
 // }

  def postNewGame() = {
    repeat(1) {
      feed(customFeeder)
        .exec(http("Post Game")
          .post("videogames/")
          .body(ElFileBody("bodies/newgametemplate.json")).asJson
          .check(status.is(200)))
        .pause(1)

    }
  }

  def getSpecificGame(): ChainBuilder = {
    exec(
      http("Get Specific Game")
        .get("videogames/${gameid}")
        .check(status.is("200"))
    )
  }

  def deleteSpecificGame(): ChainBuilder = {
    exec(
      http("Delete Specific Game")
        .delete("videogames/${gameid}")
        .check(status.is("200"))
    )
  }


  after {
    println("LALALA")
    exec{ session => println(session("""${gameid}""").as[String]); session}
  }



  val scn = scenario("Final Challenge Scenario")
    .exec(getAllVideoGames())
    .pause(5 seconds)
    .exec(postNewGame())
    .pause(2 seconds)
    .exec(getSpecificGame())
    .exec{ session => println(session("gameid").as[String]); session}
    .pause(3)
    .exec(deleteSpecificGame())
    .pause(1)



//  setUp(
 //   scn.inject(atOnceUsers(users = 1))
  //).protocols(httpConf)

  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(userCount) during (rampDuration second)
    )
  ).protocols(httpConf).maxDuration(testDuration seconds)




}

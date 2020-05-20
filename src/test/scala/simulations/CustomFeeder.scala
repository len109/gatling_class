package simulations

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

<<<<<<< HEAD
class CustomFeeder extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  var idNumbers = (11 to 20).iterator
  val rnd = new Random()
  val now = LocalDate.now()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

=======

class CustomFeeder extends Simulation{

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")

  var idNumbers = (11 to 20).iterator

  val rnd = new Random() //need to import the random util for this. This is a random number generator.
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val now = LocalDate.now()

  ////this returns the number of characters in the random number
>>>>>>> temp-branch
  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

<<<<<<< HEAD
  def getRandomDate(startDate: LocalDate, random: Random): String = {
=======
  def getRandomDate(startDate : LocalDate, random: Random): String = {
>>>>>>> temp-branch
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
<<<<<<< HEAD
    "gameId" -> idNumbers.next(),
    "name" -> ("Game-" + randomString(5)),
=======
    "gameId" -> idNumbers.next(), //next takes the next value in the list
    "name" -> ("Game-" + randomString(5)), //the name will be "Game-5 random numbers
  //  "releaseDate" -> getRandomDate(now, rnd),
>>>>>>> temp-branch
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(6)),
    "rating" -> ("Rating-" + randomString(4))
  ))

<<<<<<< HEAD
//  def postNewGame() = {
  ////    repeat(5) {
  ////      feed(customFeeder)
  ////        .exec(http("Post New Game")
  ////        .post("videogames/")
  ////        .body(StringBody(
  ////                      "{" +
  ////                      "\n\t\"id\": ${gameId}," +
  ////                      "\n\t\"name\": \"${name}\"," +
  ////                      "\n\t\"releaseDate\": \"${releaseDate}\"," +
  ////                      "\n\t\"reviewScore\": ${reviewScore}," +
  ////                      "\n\t\"category\": \"${category}\"," +
  ////                      "\n\t\"rating\": \"${rating}\"\n}")
  ////        ).asJson
  ////        .check(status.is(200)))
  ////        .pause(1)
  ////    }
  ////  }
=======
/*  def postNewGame() = {
    repeat(5) {
      feed(customFeeder)
        .exec(http("Post Game")
        .post("videogames/")
            .body(StringBody(
                         "{" +
                          "\n\t\"id\": ${gameId}," +
                          "\n\t\"name\": \"${name}\"," +
                          "\n\t\"releaseDate\": \"${releaseDate}\"," +
                          "\n\t\"reviewScore\": ${reviewScore}," +
                          "\n\t\"category\": \"${category}\"," +
                          "\n\t\"rating\": \"${rating}\"\n}")
            ).asJson
              .check(status.is(200)))
             .pause(1)

    }
  }*/
>>>>>>> temp-branch

  def postNewGame() = {
    repeat(5) {
      feed(customFeeder)
<<<<<<< HEAD
        .exec(http("Post New Game")
          .post("videogames/")
            .body(ElFileBody("bodies/NewGameTemplate.json")).asJson
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Post new games")
      .exec(postNewGame())


  setUp(
    scn.inject(atOnceUsers(1))
=======
        .exec(http("Post Game")
          .post("videogames/")
            .body(ElFileBody("bodies/newgametemplate.json")).asJson
          .check(status.is(200)))
        .pause(1)

    }
  }





  val scn = scenario("Post New Games")
      .exec(postNewGame())

  setUp(
    scn.inject(atOnceUsers(users = 1))
>>>>>>> temp-branch
  ).protocols(httpConf)

}

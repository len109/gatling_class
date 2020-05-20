package simulations

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random


class CustomFeeder extends Simulation{

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")

  var idNumbers = (11 to 20).iterator

  val rnd = new Random() //need to import the random util for this. This is a random number generator.
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val now = LocalDate.now()

  ////this returns the number of characters in the random number
  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate : LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
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

  def postNewGame() = {
    repeat(5) {
      feed(customFeeder)
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
  ).protocols(httpConf)

}

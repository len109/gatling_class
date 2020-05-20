package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends Simulation{

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")

  val csvFeeder=csv("data/gameCsvfile.csv").circular

  def getSpecificVideoGame() = {
  repeat(10) {
    feed(csvFeeder)
      .exec(http("Get Specific Video Game")
        .get("videogames/${gameId}")
        .check(jsonPath("$.name").is("${gameName}"))
      .check(status.is(200)))
      .pause(3)
  }
  }

  val scn = scenario("CSV Feeder Test")
      .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpConf)


}

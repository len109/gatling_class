package simulations

<<<<<<< HEAD
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class CheckResponseCode extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val scn = scenario("Video Game DB - 3 calls")

    .exec(http("Get all video games - 1st call")
=======

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt


class CheckResponseCode extends Simulation{

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")


  val scn = scenario(scenarioName="Video Game DB 3 Calls")

    .exec(http("Get All Video Games First Call")
>>>>>>> temp-branch
      .get("videogames")
        .check(status.is(200)))
    .pause(5)

<<<<<<< HEAD
    .exec(http("Get specific game")
      .get("videogames/1")
        .check(status.in(200 to 210)))
    .pause(1, 20)

    .exec(http("Get all Video games - 2nd call")
      .get("videogames")
        .check(status.not(404), status.not(500)))
    .pause(3000.milliseconds)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
=======
    .exec(http("Get Specific Video Game")
      .get("videogames/1")
        .check(status.in(200 to 210)))
    .pause(1,20)

    .exec(http("Get All Video Games Second Call")
      .get("videogames")
        .check(status.not(404), status.not(503)))
    .pause(3000.milliseconds)

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpConf)

}
>>>>>>> temp-branch

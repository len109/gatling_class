package simulations


import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt


class CheckResponseCode extends Simulation{

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")


  val scn = scenario(scenarioName="Video Game DB 3 Calls")

    .exec(http("Get All Video Games First Call")
      .get("videogames")
        .check(status.is(200)))
    .pause(5)

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

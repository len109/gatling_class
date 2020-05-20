package simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class BasicLoadSimulation extends Simulation {

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")


    def getAllVideoGames(): ChainBuilder = {
    exec(
      http("Get All Video Games")
        .get("videogames")
        .check(status.is(200))
    )
  }

    def getSpecificGame(): ChainBuilder = {
      exec(
        http("Get Specific Name")
          .get("videogames/2")
          .check(status.is(200))
      )
    }

    val scn2 = scenario("Basic2")


    val scn = scenario("Basic Load Simulation")
      .exec(getAllVideoGames())
      .pause(5)
      .exec(getSpecificGame())
      .pause(5)
      .exec(getAllVideoGames())


    setUp(
      scn.inject(
        nothingFor(5 seconds),
        atOnceUsers(5),
        rampUsers(10) during (10 seconds)
      ).protocols(httpConf.inferHtmlResources())
    )


}

package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBodyAndExtract extends Simulation{

  val httpConf = http.baseUrl(url="http://localhost:8080/app/")
    .header(name="Accept", value="application/json")

val scn = scenario("Check JSON Path")

    .exec(http("Get Specific Game")
    .get("videogames/1")
    .check(jsonPath("$.name").is("Resident Evil 4")))


    .exec(http("Get All Video Games")
    .get("videogames")
    .check(jsonPath("$[1].id").saveAs("gameid")))
 //   .exec{ session => println(session); session}
     .exec{ session => println(session("gameid").as[String]); session}

    .exec(http("Get Specific Game")
    .get("videogames/${gameid}")
      .check(jsonPath("$.name").is("Gran Turismo 3"))
    .check(bodyString.saveAs("responsebody")))
    .exec { session => println(session("responsebody").as[String]); session}



  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpConf)


}

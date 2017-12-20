package com.fabian.akka.untyped

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.TestKit
import akka.util.Timeout
import com.fabian.akka.untyped.primes.PrimeFinder
import com.fabian.akka.untyped.primes.PrimeFinder.Messages.Start
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import akka.pattern.ask

class PrimeFinderSpec(_system: ActorSystem)
    extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll
    with Eventually
    with ScalaFutures {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(Span(2, Seconds))

  "A primer calculator" must {

    val primesUntil100 =
      List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
        89, 97)

    "should handle values upper than 2" in {
      val primeFinder: ActorRef = system.actorOf(PrimeFinder.props())
      (primeFinder ? Start(100)).mapTo[List[Int]].futureValue should be(primesUntil100)
    }

  }

  def this() = this(ActorSystem("eratosthenes"))

  override def afterAll: Unit = {
    shutdown(system)
  }

}

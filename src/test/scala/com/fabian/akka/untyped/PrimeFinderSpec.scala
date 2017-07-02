package com.fabian.akka.untyped

import akka.actor.ActorSystem
import akka.testkit.TestKit
import akka.util.Timeout
import com.fabian.akka.untyped.primes.PrimeFinder
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class PrimeFinderSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll
    with Eventually {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(Span(20, Seconds))

  "A primer calculator" must {

    val primesUntil100 = List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)

    "should handle values upper than 2" in {
      PrimeFinder(100).map {
        _ should be(primesUntil100)
      }
    }

  }

  def this() = this(ActorSystem("eratosthenes"))

  override def afterAll: Unit = {
    shutdown(system)
  }

}

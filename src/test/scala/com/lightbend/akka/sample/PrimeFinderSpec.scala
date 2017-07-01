//#full-example
package com.lightbend.akka.sample

import akka.actor.ActorSystem
import akka.testkit.TestKit
import akka.util.Timeout
import fr.xebia.csp.eratosthenes.primes.PrimeFinderCalculator
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, FunSpecLike, Matchers}

class PrimeFinderSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with FunSpecLike
    with BeforeAndAfterAll
    with Eventually {

  def this() = this(ActorSystem("eratosthenes"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(Span(20, Seconds))

  describe("A primer calculator") {

    it("should handle values upper than 2") {
      val expected = List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)
      PrimeFinderCalculator(100).map {
        _ should be(expected)
      }
    }
  }

}

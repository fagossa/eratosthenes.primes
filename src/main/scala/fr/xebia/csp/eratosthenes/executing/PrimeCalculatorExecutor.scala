package fr.xebia.csp.eratosthenes.executing

import akka.actor.ActorSystem
import akka.pattern.after
import akka.util.Timeout
import fr.xebia.csp.eratosthenes.primes.PrimeFinderCalculator

import scala.concurrent.duration._
import scala.concurrent.{Future, TimeoutException}
import scala.util.{Failure, Success}

object PrimeCalculatorExecutor extends App {

  implicit val system = ActorSystem("EratosthenesSystem")

  implicit val ec = system.dispatcher

  private val duration = 20 seconds
  implicit val timeout = Timeout(duration)

  val upper = 100
  val eventualPrimes = PrimeFinderCalculator(upper)

  lazy val t = after(
    duration = duration,
    using = system.scheduler)(Future.failed(new TimeoutException("Timed out!")))

  val response = Future firstCompletedOf Seq(eventualPrimes, t)

  response.onComplete {
    case Success(primes) =>
      println(primes)
      system.terminate()

    case Failure(error) =>
      Console.err.println(error)
      system.terminate()
  }
}

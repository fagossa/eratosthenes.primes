package com.fabian.akka.untyped.executing

import akka.actor.ActorSystem
import akka.pattern.after
import akka.util.Timeout
import com.fabian.akka.untyped.primes.PrimeFinder

import scala.concurrent.duration._
import scala.concurrent.{Future, TimeoutException}
import scala.util.{Failure, Success}

object PrimeCalculatorExecutor extends App {

  implicit val system = ActorSystem("EratosthenesSystem")

  implicit val ec = system.dispatcher

  private val duration = 20 seconds
  implicit val timeout = Timeout(duration)

  val upper = 1000
  val resultPrimes = PrimeFinder(upper)

  resultPrimes.onComplete {
    case Success(primes) =>
      println(primes)
      system.terminate()

    case Failure(error) =>
      Console.err.println(error)
      system.terminate()
  }
}

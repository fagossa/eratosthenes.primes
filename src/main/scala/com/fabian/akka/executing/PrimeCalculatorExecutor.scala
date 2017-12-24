package com.fabian.akka.executing

import akka.actor.{ActorRef, ActorSystem}
import akka.util.Timeout
import com.fabian.akka.primes.PrimeFinder

import scala.concurrent.duration._
import scala.util.{Failure, Success}
import akka.pattern.ask

object PrimeCalculatorExecutor extends App {

  implicit val system = ActorSystem("EratosthenesSystem")

  implicit val ec = system.dispatcher

  private val duration = 20 seconds
  implicit val timeout = Timeout(duration)

  val upper = 1000
  val primeFinder: ActorRef = system.actorOf(PrimeFinder.props())

  val resultPrimes = (primeFinder ? PrimeFinder.Messages.Start(upper)).mapTo[List[Int]]

  resultPrimes.onComplete {
    case Success(primes) =>
      println(primes)
      system.terminate()

    case Failure(error) =>
      Console.err.println(error)
      system.terminate()
  }
}

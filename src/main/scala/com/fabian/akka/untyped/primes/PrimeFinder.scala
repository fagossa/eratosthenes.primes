package com.fabian.akka.untyped.primes

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future

object PrimeFinder {

  def apply(upper: Int)(implicit system: ActorSystem, timeout: Timeout): Future[List[Int]] = {
    if (upper < 2) {
      Future.successful(Nil)
    } else {
      val master = system.actorOf(Master.props(upper), name = Master.name)
      system.actorOf(Eratosthenes.props(), name = Eratosthenes.name)

      (master ? Master.Messages.FindPrimes).mapTo[List[Int]]
    }
  }

}

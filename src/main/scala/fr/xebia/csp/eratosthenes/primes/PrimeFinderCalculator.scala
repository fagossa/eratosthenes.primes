package fr.xebia.csp.eratosthenes.primes

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import fr.xebia.csp.eratosthenes.primes.Master.Messages._

import scala.concurrent.Future

object PrimeFinderCalculator {

  def apply(upper: Int, nrOfWorkers: Int = 2)
           (implicit system: ActorSystem, timeout: Timeout): Future[List[Int]] = {
    if (upper < 2) {
      Future.successful(Nil)
    } else {
      val master = system.actorOf(Master.props(upper), name = "master")
      val queuer = system.actorOf(Queuer.props(upper), name = "queuer")
      val _ = system.actorOf(Worker.props(upper, nrOfWorkers), name = "worker")

      (master ? Find).mapTo[List[Int]]
    }
  }

}
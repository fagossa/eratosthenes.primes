package fr.xebia.csp.eratosthenes.primes

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import fr.xebia.csp.eratosthenes.primes.Messages._

import scala.collection.mutable
import scala.concurrent.Future


object PrimeFinderCalculator {

  def apply(upper: Int, nrOfWorkers: Int = 2)
           (implicit system: ActorSystem, timeout: Timeout): Future[List[Int]] = {
    if (upper < 2) {
      Future.successful(Nil)
    } else {
      val actors = new mutable.HashMap[String, ActorRef]

      val master = system.actorOf(Master.props(upper, actors), name = "master")
      val queuer = system.actorOf(Queuer.props(upper, actors), name = "queuer")
      val worker = system.actorOf(Worker.props(upper, nrOfWorkers, actors), name = "worker")

      actors += ("master" -> master, "queuer" -> queuer, "worker" -> worker)

      (master ? Find).mapTo[List[Int]]
    }
  }

}
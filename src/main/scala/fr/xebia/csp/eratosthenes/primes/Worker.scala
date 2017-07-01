package fr.xebia.csp.eratosthenes.primes

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool
import fr.xebia.csp.eratosthenes.primes.Messages.{Enqueue, Filter, Merge}

import scala.collection.mutable

class Worker(upper: Int, actors: ActorRefs) extends Actor {
  def receive: Receive = {
    case Filter(n) =>
      actors(Queuer.name) ! Enqueue((2 to upper).toList filter (x => x == n || x % n != 0))

    case Merge(left, right) =>
      actors(Queuer.name) ! Enqueue(left intersect right)
  }
}

object Worker {
  val name: String = "worker"

  def props(upper: Int, nrOfWorkers: Int, actors: mutable.HashMap[String, ActorRef]) =
    Props(new Worker(upper, actors)) withRouter RoundRobinPool(nrOfWorkers)

}

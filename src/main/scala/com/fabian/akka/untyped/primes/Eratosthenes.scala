package com.fabian.akka.untyped.primes

import Eratosthenes.Messages.{EratosthenesProtocol, Sieve}
import akka.typed.scaladsl.Actor
import akka.typed.{ActorRef, Behavior}
import com.fabian.akka.untyped.primes.Master.Messages.MasterProtocol

object Eratosthenes {
  val name: String = "eratosthenes"

  def apply(): Behavior[EratosthenesProtocol] = {
    Actor.immutable {
      case (context, Sieve(parent, primes, remaining)) =>
        remaining match {
          case Nil =>
            parent ! Master.Messages.Result(primes)
            Actor.same

          case h :: tail =>
            context.self ! Sieve(parent, primes ++ List(h), tail.filter(notMultiplesOf(h)))
            Actor.same
        }
    }
  }

  def notMultiplesOf(h: Int)(x: Int): Boolean = x % h != 0

  object Messages {

    sealed trait EratosthenesProtocol extends Product with Serializable

    final case class Sieve(
      master: ActorRef[MasterProtocol],
      primes: List[Int],
      remaining: List[Int]
    ) extends EratosthenesProtocol

  }

}

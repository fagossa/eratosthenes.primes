package com.fabian.akka.primes

import Eratosthenes.Messages.Sieve
import akka.typed.scaladsl.Actor
import akka.typed.{ActorRef, Behavior}
import com.fabian.akka.primes.Master.Messages.MasterProtocol

object Eratosthenes {
  val name: String = "eratosthenes"

  def apply(): Behavior[Sieve] = {
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

    final case class Sieve(
      master: ActorRef[MasterProtocol],
      primes: List[Int],
      remaining: List[Int]
    )

  }

}

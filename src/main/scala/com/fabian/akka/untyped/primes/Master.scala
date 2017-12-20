package com.fabian.akka.untyped.primes

import akka.typed.scaladsl.Actor
import akka.typed.{ActorRef, Behavior}
import com.fabian.akka.untyped.primes.Master.Messages.{FindPrimes, MasterProtocol, Result}

object Master {

  val name: String = "master"

  def apply(): Behavior[MasterProtocol] = {
    Actor.deferred { context =>
      val eratosthenesRef = context.spawn(Eratosthenes(), name = Eratosthenes.name)

      val self = context.self

      var clientRef: Option[ActorRef[List[Int]]] = None

      Actor.immutable {
        case (_, FindPrimes(upper, client)) =>
          eratosthenesRef ! Eratosthenes.Messages.Sieve(self, Nil, (2 to upper).toList)
          clientRef = Some(client)
          Actor.same

        case (_, Result(list)) =>
          clientRef.foreach(_ ! list)
          Actor.same

      }
    }
  }

  object Messages {

    sealed trait MasterProtocol extends Product with Serializable

    final case class FindPrimes(upper: Int, client: ActorRef[Any]) extends MasterProtocol

    final case class Result(list: List[Int]) extends MasterProtocol

  }

}

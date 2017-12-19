package com.fabian.akka.untyped.primes

import akka.actor.ActorSystem
import akka.typed.scaladsl.Actor
import akka.typed.{ActorRef, Behavior}
import com.fabian.akka.untyped.primes.Master.Messages.{FindPrimes, MasterProtocol, Result}

object Master {

  val name: String = "master"

  def apply(): Behavior[MasterProtocol] = {
    Actor.deferred { context =>
      import akka.typed.scaladsl.adapter._
      implicit val s: ActorSystem = context.system.toUntyped

      val eratosthenesRef = context.spawn(Eratosthenes(), name = Eratosthenes.name)

      val self = context.self

      var client: Option[ActorRef[List[Int]]] = None

      Actor.immutable {
        case (_, FindPrimes(upper, _client)) =>
          eratosthenesRef ! Eratosthenes.Messages.Sieve(self, Nil, (2 to upper).toList)
          client = Some(_client)
          Actor.same

        case (_, Result(list)) =>
          client.foreach(_ ! list)
          Actor.same

      }
    }
  }

  object Messages {

    sealed trait MasterProtocol

    final case class FindPrimes(upper: Int, client: ActorRef[Any]) extends MasterProtocol

    final case class Result(list: List[Int]) extends MasterProtocol

  }

}

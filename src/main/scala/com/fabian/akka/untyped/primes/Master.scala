package com.fabian.akka.untyped.primes

import akka.actor.{Actor, ActorRef, Props}
import Master.Messages.{FindPrimes, Result}

class Master(upper: Int) extends Actor {
  var client: Option[ActorRef] = None

  def receive: Receive = {
    case FindPrimes =>
      client = Some(sender)
      val eratosthenesRef = context.actorSelection(s"/user/${Eratosthenes.name}")
      eratosthenesRef ! Eratosthenes.Messages.Sieve(self, Nil, (2 to upper).toList)

    case Result(list) =>
      client.foreach(_ ! list)
  }

}

object Master {

  def props(upper: Int) =
    Props(new Master(upper))

  val name: String = "master"

  object Messages {

    final case object FindPrimes

    final case class Result(list: List[Int])

  }

}

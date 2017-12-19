package com.fabian.akka.untyped.primes

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import Eratosthenes.Messages.Sieve

class Eratosthenes extends Actor with ActorLogging {

  def receive: Receive = {
    case Sieve(parent, primes, remaining) =>
      remaining match {
        case Nil =>
          parent ! Master.Messages.Result(primes)

        case h :: tail =>
          self ! Sieve(parent, primes ++ List(h), tail.filter(notMultiplesOf(h)))
      }
  }

  def notMultiplesOf(h: Int)(x: Int): Boolean = x % h != 0

}

object Eratosthenes {
  val name: String = "eratosthenes"

  def props() =
    Props(new Eratosthenes)

  object Messages {

    final case class Sieve(master: ActorRef, primes: List[Int], remaining: List[Int])

  }

}

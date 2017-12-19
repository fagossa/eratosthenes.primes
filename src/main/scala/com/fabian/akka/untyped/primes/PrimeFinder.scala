package com.fabian.akka.untyped.primes

import akka.actor.{Actor, ActorLogging, Props}
import com.fabian.akka.untyped.primes.PrimeFinder.Messages.Start

class PrimeFinder extends Actor with ActorLogging {

  import akka.typed.scaladsl.adapter._

  val master = context.spawn(Master(), name = Master.name)

  override def receive: Receive = {
    case Start(upper) =>
      if (upper < 2) sender ! Nil
      else master ! Master.Messages.FindPrimes(upper, sender())
  }

}

object PrimeFinder {
  def props(): Props = Props(new PrimeFinder())

  object Messages {
    case class Start(upper: Int)
  }
}

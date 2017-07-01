package fr.xebia.csp.eratosthenes

import akka.actor.ActorRef

package object primes {
  type ActorRefs = collection.mutable.Map[String, ActorRef]
}

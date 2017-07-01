package fr.xebia.csp.eratosthenes.primes

sealed trait Messages

object Messages {

  final case object Find extends Messages

  final case class Filter(divisor: Int) extends Messages

  final case class Enqueue(list: List[Int]) extends Messages

  final case class Merge(left: List[Int], right: List[Int]) extends Messages

  final case class Result(list: List[Int]) extends Messages

}

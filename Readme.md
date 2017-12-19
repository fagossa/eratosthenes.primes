Sieve of Eratosthenes using akka-typed
==========

# Akka type

> You only write behaviors!!! the actual actors are under the hood

## Example

```
object TypedHello {

  // protocol
  sealed trait Command
  final object SayHello extends Command

  // bevavior returns the next behavior (becoming something)
  def apply(): Behavior[SayHello.type] {
      Actor.immutable {
        // the first parameter is an actor context
        case (_, SayHello) =>
          println("Hello!")
          Actor.same // same actor as it does not become to some other state
      }
  }
}
```

## Creating immutable behaviors

Equivalent to the previous received
now we get the context with each message

```
Actor.immutable {
  case (context, message) =>
    ...
}
```

## 1. Creating deferred

creates the actor representation **BEFORE** and then creates the actor

```
Actor.deferred { context => ... }
```

## 2. Creating actors

```
def aBehavior(): Behavior[Command] = {
  Actor.immutable {
    case (context, message) =>
      println(s"Hello $message")
      Actor.same
  }
}
context.spawn(aBehavior())
```

## Additional behaviors

These are returned at the end of a message handling (e.g. graceful shutdown)
(alternatives to `Actor.same`)
```
Actor.stopped // this actor is marked as stopped
Actor.ignore // when the actor should no receive any messages
```

## Replies

The protocol contains references to other actors ...
```
final case class AddUser(user: User, replyTo: ActorRef[AnotherCommand])
```
## Supervision

> Akka Untyped = default supervision behavior = Restarted
> Akka Typed = default supervision behavior = Stopped

```
val theBehavior = supervise(aBehavior())
 .onFailure[MyLaunchedException](
    restartWithBackoff(minBackoff, maxBackoff, 0)
  )
context.spawn(theBehavior, "a cool name")
```

----------


## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with
any pull requests, please state that the contribution is your original work and that you license
the work to the project under the project's open source license. Whether or not you state this
explicitly, by submitting any copyrighted material via pull request, email, or other means you
agree to license the material under the project's open source license and warrant that you have the
legal authority to do so.

## License ##

This code is open source software licensed under the
[Apache-2.0](http://www.apache.org/licenses/LICENSE-2.0) license.

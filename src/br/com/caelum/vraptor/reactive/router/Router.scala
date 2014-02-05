package br.com.caelum.vraptor.reactive.router

import akka.actor.Actor
import akka.actor.Props
import br.com.caelum.vraptor.reactive.controller.HelloController
import javax.servlet.AsyncContext
import scala.concurrent.Future
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.util.Failure
import scala.util.Failure
import scala.util.Success
import javax.servlet.http.HttpServletResponse

class Router extends Actor {

  val controller = context.actorOf(Props[HelloController])
  implicit val timeout = Timeout(10 seconds)
  implicit val ex = ExecutionContext.Implicits.global

  def receive = {
    case (uri: String, ctx: AsyncContext) => {
      val future = (controller ? (uri, ctx)).mapTo[Any]

      future.onComplete {
        case Failure(error) => {
          println(error)
          ctx.getResponse().asInstanceOf[HttpServletResponse].setStatus(500)
          ctx.complete()
        }
        case Success(result) => {
          println(result)
          ctx.complete()
        }
      }
    }
  }

}

case class End(ctx: AsyncContext)
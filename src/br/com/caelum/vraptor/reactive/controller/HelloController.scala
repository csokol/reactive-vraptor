package br.com.caelum.vraptor.reactive.controller

import akka.event.Logging
import akka.actor.Actor
import javax.servlet.AsyncContext
import br.com.caelum.vraptor.reactive.router.End
import br.com.caelum.vraptor.reactive.router.End
import scala.concurrent.{Future, ExecutionContext}
import akka.pattern.pipe
import java.util.concurrent.Executors

class HelloController extends Actor {
  implicit val ex = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

  def receive = {
    case ("/oi", ctx: AsyncContext) => {
      val future = Future {
    	  Thread.sleep(5000)
    	  "oi"
      }
      ctx.getResponse().getWriter().print("oi")
      future pipeTo sender
    }

    case ("/vai", ctx: AsyncContext) => {
      ctx.getResponse().getWriter().print("vai")
      sender ! "fim"
    }
    
    case m => println("" + m)
      
  }
}
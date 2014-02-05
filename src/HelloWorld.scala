import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.annotation.WebServlet
import akka.actor.ActorSystem
import akka.actor.Props
import br.com.caelum.vraptor.reactive.controller.HelloController
import br.com.caelum.vraptor.reactive.router.Router
import java.util.concurrent.atomic.AtomicInteger

@WebServlet(urlPatterns = Array("/vraptor/*"), asyncSupported = true)
class HelloWorld extends HttpServlet {

  val system: ActorSystem = ActorSystem("vraptor")  
  val router = system.actorOf(Props[Router])

  override def service(req: HttpServletRequest, res: HttpServletResponse) {
    val context = req.startAsync()
    context.start(new Runnable {
      def run {    	
        val uri = req.getRequestURI().substring(req.getContextPath().concat(req.getServletPath()).length())
        println(uri)
        router ! (uri, context)
      }
    })        

  }
}
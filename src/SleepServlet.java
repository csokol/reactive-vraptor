import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns="/sleep",asyncSupported=true)
public class SleepServlet extends HttpServlet{
	
	private AtomicInteger atomicInteger = new AtomicInteger();

	@Override
	protected void service(HttpServletRequest req, final HttpServletResponse res)
			throws ServletException, IOException {
			AsyncContext context = req.startAsync();
			context.start(new Runnable() {
				
				@Override
				public void run() {
					int id = atomicInteger.getAndIncrement();
					System.out.println("inicio do request "+id);
					try {
						Thread.sleep(3000);
						res.getWriter().print("acordou "+id);
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
					
				}
			});
	}
}

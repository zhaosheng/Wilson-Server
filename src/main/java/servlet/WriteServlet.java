package servlet;


import database.Database;
import database.SQLite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Message;


/**
 * Created by shezhao on 2/27/14.
 */
public class WriteServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException
  {
    super.doGet(req, resp);
  }

  @Override
  public void init()
      throws ServletException
  {
    Database database = new SQLite();
    database.createTableIfNotExists();
    database.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException
  {
    req.setCharacterEncoding("UTF-8");
    Map<String,String[]> parameterMap = req.getParameterMap();
    for (String s : parameterMap.keySet())
    {
      System.err.println("Key: " + s + " Value: " + parameterMap.get(s)[0]);
    }
    // TODO: Use thread pool later
    Database database = new SQLite();

    List<Message> messageList = new ArrayList<Message>();
    messageList.add(new Message(req.getParameter("name"), req.getParameter("message")));
    database.insertMessage(messageList);
    database.close();
    resp.sendRedirect("/call?user=" + req.getParameter("name"));
  }
}

package servlet;


import database.Database;
import database.SQLite;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;


/**
 * Created by shezhao on 2/27/14.
 */
public class CallServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException
  {
    // Only process Web browser request.
    Map<String,String[]> parameterMap = req.getParameterMap();
    for (String s : parameterMap.keySet())
    {
      System.err.println("Key: " + s + "Value: " + parameterMap.get(s)[0]);
    }
    Database database = new SQLite();
    String user = req.getParameter("user");
    List<Message> messageList = null;
    if (user == null)
    {
      // Query All
      messageList = database.selectAllMessages();
    } else
    {
      messageList = database.selectMessage(new Message(user, "dummy"));
    }
    HttpSession session = req.getSession();
    session.setAttribute("msg", messageList);
    resp.sendRedirect("/show.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException
  {
    // Only process Application requests.
    Database database = new SQLite();
    String user = req.getParameter("user");
    List<Message> messageList = null;
    if (user == null)
    {
      // Query All
      messageList = database.selectAllMessages();
    } else
    {
      messageList = database.selectMessage(new Message(user, "dummy"));
    }
//    ObjectOutputStream objectOutputStream = new ObjectOutputStream(resp.getOutputStream());
//    objectOutputStream.writeObject(messageList);

    JSONArray jsonArray = new JSONArray();
    for (Message message : messageList)
    {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("user", message.getUser());
      jsonObject.put("message", message.getMessage());
      jsonArray.put(jsonObject);
    }

    resp.setCharacterEncoding("UTF-8");
    new JSONWriter(resp.getWriter())
        .object()
        .key("messageArray")
        .value(jsonArray)
        .endObject();

//    resp.sendRedirect("/");
  }
}

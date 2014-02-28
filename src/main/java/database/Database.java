package database;


import java.util.List;
import model.Message;


/**
 * Created by shezhao on 2/27/14.
 */
public interface Database
{
  public List<Message> selectMessage(Message in);
  public List<Message> selectAllMessages();
  public boolean insertMessage(List<Message> msgs);
  public void createTableIfNotExists();
  public void dropTableIfExists();

  public void init();
  public void close();
}

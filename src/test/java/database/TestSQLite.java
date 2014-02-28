package database;


import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * Created by shezhao on 2/27/14.
 */
public class TestSQLite
{
  private SQLite _sqLiteDB;

  @Before
  public void setUp()
      throws SQLException, FileNotFoundException
  {
    _sqLiteDB = new SQLite();
    _sqLiteDB.set_msgTable("testTable");
    _sqLiteDB.dropTableIfExists();
    _sqLiteDB.createTableIfNotExists();
  }

  @Test
  public void testCreateDBIfNotExist()
      throws SQLException
  {
     _sqLiteDB.createTableIfNotExists();
  }

  @Test
  public void testInsert3Records()
  {
    Message message1 = new Message("user1", "i'm working in Sunnyvale");
    Message message2 = new Message("user2", "i'm going to work in Sunnyvale tomorrow");
    Message message3 = new Message("user2", "I worked in Sunnyvale yesterday");

    ArrayList<Message> messages = new ArrayList<Message>(3);
    messages.add(message1);
    messages.add(message2);
    messages.add(message3);

    boolean actual = _sqLiteDB.insertMessage(messages);
    assertEquals("Insert failed", true, actual);

    List<Message> selectMessage = _sqLiteDB.selectMessage(message2);
    assertEquals("Unexpected return count", 2, selectMessage.size());

    assertEquals("Unexpected compare result", message2, selectMessage.get(0));
    assertEquals("Unexpected compare result", message3, selectMessage.get(1));

  }

  @After
  public void tearDown()
  {
    _sqLiteDB.close();
  }

}

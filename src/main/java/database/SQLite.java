package database;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import model.Message;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;


/**
 * Created by shezhao on 2/27/14.
 */
public class SQLite implements Database
{
  private static final Logger _logger = Logger.getLogger(SQLite.class);
  private static final String _dbFile = "/tmp/wilson_server.db";
  private static String _msgTable = "messages";
  private Connection _connection;

  private Connection getConnection()
      throws ClassNotFoundException, SQLException
  {
    Class.forName("org.sqlite.JDBC");
    return  DriverManager.getConnection("jdbc:sqlite:" + _dbFile);
  }

  public synchronized boolean insertMessage(List<Message> msgs)
  {
    String insertPattern = "insert into " + _msgTable + "(name, message) values (?, ?);";
    int cnt = 0;
    for (Message msg : msgs)
    {
      try
      {
        PreparedStatement preparedStatement = _connection.prepareStatement(insertPattern);
        preparedStatement.setString(1, msg.getUser());
        preparedStatement.setString(2, msg.getMessage());
        preparedStatement.executeUpdate();
        cnt++;
      }
      catch (SQLException e)
      {
        e.printStackTrace();
        _logger.error("Failed to insert MSG (" + msg + ")");
        return false;
      }
    }
    _logger.info("Successfully inserted " + cnt + " records into " + _msgTable);
    return true;
  }

  public synchronized List<Message> selectMessage(Message in)
  {
    String selectPattern = "select name, message from " + _msgTable + " where name = ?;";
    ArrayList<Message> messages;
    try
    {
      PreparedStatement preparedStatement = _connection.prepareStatement(selectPattern);
      preparedStatement.setString(1, in.getUser());
      ResultSet resultSet = preparedStatement.executeQuery();
      messages = new ArrayList<Message>();
      while (resultSet.next())
      {
        messages.add(new Message(resultSet.getString(1), resultSet.getString(2)));
      }
      resultSet.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      _logger.error(e.getMessage());
      return null;
    }
    return messages;
  }

  public void createTableIfNotExists()
  {
    String select = "select * from " + _msgTable + " where 1 = 0;";
    Statement statement = null;
    try
    {
      statement = _connection.createStatement();
    }
    catch (SQLException e)
    {
      _logger.error(e.getMessage());
    }
    try
    {
      statement.executeQuery(select);
    }
    catch (SQLException e)
    {
      _logger.info("Table doesn't exist... Creating...");
      String create = "create table " + _msgTable + " (name varchar(256), message varchar(1024));";
      try
      {
        statement.execute(create);
      }
      catch (SQLException e1)
      {
        _logger.error(e1.getMessage());
      }
      try
      {
        statement.executeQuery(select);
        _logger.info("Table created.");
      }
      catch (SQLException e1)
      {
        _logger.fatal("Failed to create table!");
      }
    }
    _logger.info("Table exists...");
  }

  public void dropTableIfExists()
  {
    String drop = "drop table " + _msgTable + ";";
    try
    {
      Statement statement = _connection.createStatement();
      statement.execute(drop);
      _logger.info("Table " + _msgTable + " has been dropped!");
    }
    catch (SQLException e)
    {
//      e.printStackTrace();
      _logger.warn(e.getMessage());
      _logger.warn("Table " + _msgTable + " doesn't exist.");
    }

  }

  @Override
  public List<Message> selectAllMessages()
  {
    String select = "select name, message from " + _msgTable + ";";
    List<Message> messageList = new ArrayList<Message>();
    try
    {
      Statement statement = _connection.createStatement();
      ResultSet resultSet = statement.executeQuery(select);
      while (resultSet.next())
      {
        messageList.add(new Message(resultSet.getString(1), resultSet.getString(2)));
      }
      resultSet.close();
    }
    catch (SQLException e)
    {
      _logger.error(e.getMessage());
    }
    return messageList;
  }

  @Override
  public void init()
  {
    try
    {
      _connection = getConnection();
      _logger.info("DB connection initialized.");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
      _logger.fatal("SQLite Driver is missing!");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      _logger.fatal("Failed to open DB!");
    }
  }

  @Override
  public void close()
  {
    if (_connection != null)
    {
      try
      {
        _connection.close();
        _logger.info("DB connection closed.");
      }
      catch (SQLException e)
      {
        _logger.error(e.getMessage());
      }
    }
  }

  public SQLite()
  {
//    PropertyConfigurator.configure(new FileInputStream("log.properties"));
    try
    {
      PropertyConfigurator.configure(ClassLoader.getSystemResourceAsStream("log.properties"));
    }
    catch (NullPointerException e)
    {
    }

//    ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout("%-4r [%t] %-5p %c %x - %m%n"));
//    Enumeration allAppenders = _logger.getAllAppenders();
//
//    if (! _logger.isAttached(consoleAppender)) _logger.addAppender(consoleAppender);
    init();
  }

  /**
   * For test usage to override the messages table
   * @param _msgTable
   */
  public static void set_msgTable(String _msgTable)
  {
    SQLite._msgTable = _msgTable;
  }
}

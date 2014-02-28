package model;


import java.io.Serializable;


/**
 * Created by shezhao on 2/27/14.
 */
public class Message implements Serializable
{
  private String user;
  private String message;

  public Message(String user, String message)
  {
    this.user = user;
    this.message = message;
  }

  public String getUser()
  {
    return user;
  }

  public void setUser(String user)
  {
    this.user = user;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  /**
   * Returns a string representation of the object. In general, the <code>toString</code> method returns a string that
   * "textually represents" this object. The result should be a concise but informative representation that is easy for
   * a person to read. It is recommended that all subclasses override this method.
   * <p/>
   * The <code>toString</code> method for class <code>Object</code> returns a string consisting of the name of the class
   * of which the object is an instance, the at-sign character `<code>@</code>', and the unsigned hexadecimal
   * representation of the hash code of the object. In other words, this method returns a string equal to the value of:
   * <blockquote>
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre></blockquote>
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString()
  {
    return "User: " + user + " Message: " + message;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * <p/>
   * The <code>equals</code> method implements an equivalence relation on non-null object references: <ul> <li>It is
   * <i>reflexive</i>: for any non-null reference value <code>x</code>, <code>x.equals(x)</code> should return
   * <code>true</code>. <li>It is <i>symmetric</i>: for any non-null reference values <code>x</code> and <code>y</code>,
   * <code>x.equals(y)</code> should return <code>true</code> if and only if <code>y.equals(x)</code> returns
   * <code>true</code>. <li>It is <i>transitive</i>: for any non-null reference values <code>x</code>, <code>y</code>,
   * and <code>z</code>, if <code>x.equals(y)</code> returns <code>true</code> and <code>y.equals(z)</code> returns
   * <code>true</code>, then <code>x.equals(z)</code> should return <code>true</code>. <li>It is <i>consistent</i>: for
   * any non-null reference values <code>x</code> and <code>y</code>, multiple invocations of <tt>x.equals(y)</tt>
   * consistently return <code>true</code> or consistently return <code>false</code>, provided no information used in
   * <code>equals</code> comparisons on the objects is modified. <li>For any non-null reference value <code>x</code>,
   * <code>x.equals(null)</code> should return <code>false</code>. </ul>
   * <p/>
   * The <tt>equals</tt> method for class <code>Object</code> implements the most discriminating possible equivalence
   * relation on objects; that is, for any non-null reference values <code>x</code> and <code>y</code>, this method
   * returns <code>true</code> if and only if <code>x</code> and <code>y</code> refer to the same object (<code>x ==
   * y</code> has the value <code>true</code>).
   * <p/>
   * Note that it is generally necessary to override the <tt>hashCode</tt> method whenever this method is overridden, so
   * as to maintain the general contract for the <tt>hashCode</tt> method, which states that equal objects must have
   * equal hash codes.
   *
   * @param obj the reference object with which to compare.
   *
   * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
   *
   * @see #hashCode()
   * @see java.util.Hashtable
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Message)
    {
      Message ob = (Message) obj;
      if (this.user.equals(ob.getUser()) && this.message.equals(ob.getMessage()))
        return true;
      else return false;
    } else return false;
  }
}

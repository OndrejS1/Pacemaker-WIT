package models;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

import utils.ToJsonString;

public class User 
{
  static Long   counter = 0l;

  public Long   id;
  public String firstName;
  public String lastName;
  public String email;
  public String password;
  public String role;
  
  public Map<Long, Activity> activities = new HashMap<>();
  
  public User()
  {
  }
  
 
  
  public User(String firstName, String lastName, String email, String password, String role)
  {
    this.id        = counter++;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }
  
  public User(String firstName, String last, String email, String password)
  {
    this(firstName,last, email, password, "default");
  }
  
  
  public String toString()
  {
    return new ToJsonString(getClass(), this).toString();
  }
  
  @Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.lastName, this.firstName, this.email, this.password);  
  }  
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof User)
    {
      final User other = (User) obj;
      return Objects.equal(firstName, other.firstName) 
          && Objects.equal(lastName,  other.lastName)
          && Objects.equal(email,     other.email)
          && Objects.equal(password,  other.password)
          &&  Objects.equal(activities,  other.activities);
    }
    else
    {
      return false;
    }
  }
}
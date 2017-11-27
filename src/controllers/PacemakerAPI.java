package controllers;

import java.util.Collection;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

import utils.FileLogger;
import utils.Serializer;
import models.Activity;
import models.Location;
import models.User;

public class PacemakerAPI
{
  private Serializer serializer;
  
  private Map<Long,   User>   userIndex       = new HashMap<>();
  private Map<String, User>   emailIndex      = new HashMap<>();
  private Map<Long, Activity> activitiesIndex = new HashMap<>();
  public Optional<User> currentUser;
      
  
  
  public boolean login(String email, String password) {
	    Optional<User> user = Optional.fromNullable(emailIndex.get(email));
	    if (user.isPresent() && user.get().password.equals(password)) {
	      currentUser = user;
	      FileLogger.getLogger().log(currentUser.get().email + " logged in...");
	      return true;
	    }
	    return false;
	  }
  
  
  
//simplified and generalized version of my logout method
 public void logout() {
   Optional<User> user = currentUser;
   if (user.isPresent()) {
     FileLogger.getLogger().log(currentUser.get().firstName + " logged out...");
     currentUser = Optional.absent();
   }
 }
  
  
  
  
  
  public PacemakerAPI()
  {}
  
  public PacemakerAPI(Serializer serializer)
  {
    this.serializer = serializer;
  }
  
  @SuppressWarnings("unchecked")
  public void load() throws Exception
  {
    serializer.read();
    activitiesIndex = (Map<Long, Activity>) serializer.pop();
    emailIndex      = (Map<String, User>)   serializer.pop();
    userIndex       = (Map<Long, User>)     serializer.pop();
  }
  
  public void store() throws Exception
  {
    serializer.push(userIndex);
    serializer.push(emailIndex);
    serializer.push(activitiesIndex);
    serializer.write(); 
  }
  
  public Collection<User> getUsers ()
  {
    return userIndex.values();
  }
  
  public  void deleteUsers() 
  {
    userIndex.clear();
    emailIndex.clear();
  }
  
  public User createUser(String firstName, String lastName, String email, String password) 
  {
    User user = new User (firstName, lastName, email, password);
    userIndex.put(user.id, user);
    emailIndex.put(email, user);
    return user;
  }
  
  public User getUserByEmail(String email) 
  {
    return emailIndex.get(email);
  }

  public User getUser(Long id) 
  {
    return userIndex.get(id);
  }

  public void deleteUser(Long id) 
  {
    User user = userIndex.remove(id);
    emailIndex.remove(user.email);
  }
  
  public Activity createActivity(Long id, String type, String location, double distance)
  {
    Activity activity = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent())
    {
      activity = new Activity (type, location, distance);
      user.get().activities.put(activity.id, activity);
      activitiesIndex.put(activity.id, activity);
    }
    return activity;
  }
  
  public Activity getActivity (Long id)
  {
    return activitiesIndex.get(id);
  }
  
  public void addLocation (Long id, float latitude, float longitude)
  {
    Optional<Activity> activity = Optional.fromNullable(activitiesIndex.get(id));
    if (activity.isPresent())
    {
      activity.get().route.add(new Location(latitude, longitude));
    }
  }
}
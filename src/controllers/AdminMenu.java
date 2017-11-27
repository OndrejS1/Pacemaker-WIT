package controllers;

import java.util.Collection;

import com.google.common.base.Optional;

import models.Activity;
import models.User;
import asg.cliche.Command;
import asg.cliche.Param;

public class AdminMenu {

	  private String name;
	  private PacemakerAPI paceApi;

	  public AdminMenu(PacemakerAPI paceApi, String userName) {

	    this.paceApi = paceApi;
	    this.setName(userName);
	  }

	  @Command(description = "Get all users details")
	  public void getUsers() {

	    Collection<User> users = paceApi.getUsers();
	    System.out.println(users);

	  }
	  public String getName() {
	    return name;
	  }
	  public void setName(String name) {
	    this.name = name;
	  }
	  @Command(description = "Create a new User")
	  public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
	      @Param(name = "email") String email, @Param(name = "password") String password) {

	    paceApi.createUser(firstName, lastName, email, password);

	  }
	  @Command(description = "Get a Users detail")
	  public void getUser(@Param(name = "email") String email) {

	    User user = paceApi.getUserByEmail(email);
	    System.out.println(user);

	  }
	  @Command(description = "Delete a User")
	  public void deleteUser(@Param(name = "email") String email) {

	    Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
	    if (user.isPresent()) {
	      paceApi.deleteUser(user.get().id);
	    }
	  }
	  @Command(description = "Add an activity")
	  public void addActivity(@Param(name = "id") long id, @Param(name = "type") String type,
	      @Param(name = "location") String location, @Param(name = "distance") double distance) {
	    paceApi.createActivity(id, type, location, distance);
	  }
	  @Command(description = "Add Location to an activity")
	  public void addLocation(@Param(name = "activity-id") Long id, @Param(name = "latitude") float latitude,
	      @Param(name = "longitude") float longitude) {
	    Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(id));
	    if (activity.isPresent()) {
	      paceApi.addLocation(activity.get().id, latitude, longitude);
	    }
	  }
	}
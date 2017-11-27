package controllers;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import models.Activity;
import models.User;

public class DefaultMenu {

	  private String name;
	  private User user;
	  private PacemakerAPI paceApi;

	  public DefaultMenu(PacemakerAPI paceApi, User user) {
	    this.paceApi = paceApi;
	    this.setName(user.firstName);
	    this.user = user;
	  }
	  @Command(description = "Get a Users detail")
	  public void getUser(@Param(name = "email") String email) {
	    User user = paceApi.getUserByEmail(email);
	    System.out.println(user);
	  }
	  @Command(description = "Add an activity")
	  public void addActivity(@Param(name = "type") String type, @Param(name = "location") String location,
	      @Param(name = "distance") double distance) {
	    paceApi.createActivity(user.id, type, location, distance);
	  }
	  @Command(description = "Add Location to an activity")
	  public void addLocation(@Param(name = "activity-id") Long id, @Param(name = "latitude") float latitude,
	      @Param(name = "longitude") float longitude) {
	    Optional<Activity> activity = Optional.fromNullable(paceApi.getActivity(id));
	    if (activity.isPresent()) {
	      paceApi.addLocation(activity.get().id, latitude, longitude);
	    }
	  }
	  public String getName() {
	    return name;
	  }
	  public void setName(String name) {
	    this.name = name;
	  }
	}
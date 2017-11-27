package controllers;

import java.io.File;
import java.io.IOException;
import utils.Serializer;
import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;
import asg.cliche.ShellFactory;
import models.User;


public class Main implements ShellDependent {
	  private static final String ADMIN = "admin";
	  public PacemakerAPI paceApi;
	  private Shell theShell;

	  public Main() throws Exception {
	    File datastore = new File("datastore.xml");
	    Serializer serializer = new XMLSerializer(datastore);
	    paceApi = new PacemakerAPI(serializer);
	    if (datastore.isFile()) {
	      paceApi.load();
	    }
	  }

	  public void cliSetShell(Shell theShell) {
	    this.theShell = theShell;
	  }

	  @Command(description = "Log in")
	  public void logIn(@Param(name = "user name") String userName, @Param(name = "password") String pass)
	      throws IOException {

	    if (paceApi.login(userName, pass) && paceApi.currentUser.isPresent()) {
	      User user = paceApi.currentUser.get();
	      System.out.println("You are logged in as " + user.email);
	      if (user.role!=null && user.role.equals("admin")) {
	    	  
	        AdminMenu adminMenu = new AdminMenu(paceApi, user.firstName);
	        ShellFactory.createSubshell(user.firstName, theShell, "Admin", adminMenu).commandLoop();
	      } else {
	        DefaultMenu defaultMenu = new DefaultMenu(paceApi, user);
	        ShellFactory.createSubshell(user.firstName, theShell, "Default", defaultMenu).commandLoop();
	      }
	    } else
	      System.out.println("Unknown username/password.");
	  }

	  public static void main(String[] args) throws Exception {
	    Main main = new Main();
	    Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to pacemaker-console - ?help for instructions",
	        main);
	    shell.commandLoop();
	    main.paceApi.store();
	  }
	}



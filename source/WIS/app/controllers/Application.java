package controllers;

import java.util.ArrayList;
import java.util.List;

import model.Seat;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.main;

public class Application extends Controller {
	
  public static Result index() {
	  main.render("", new Html(""));
    return ok(index.render("abc!"));
  }
  
  public static Result seats() {
	  return ok(index.render("Sitzverteilung!"));
  }
  
}
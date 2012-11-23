package controllers;

import java.util.ArrayList;
import java.util.List;

import common.Granularity;

import model.SeatAggregate;
import play.api.templates.Html;
import play.Play;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Function1;
import scala.collection.script.Index;

import common.db.*;

import views.html.*;

public class Application extends Controller {

	public static Result seatdistribution(String granularity) {
        Configuration conf = play.Play.application().configuration();
		Granularity gran = null;
		try {
			gran = Granularity.valueOf(granularity.toUpperCase());
		} catch (Exception e) {
			return badRequest("Invalid parameter.");
		}
		
		String title = "Sitzveteilung fuer " + gran;
        try { 
            DBConnect db = new DBConnect(conf.getString("wisdb.connectstring"), conf.getString("wisdb.username"), conf.getString("wisdb.password"));
            List<SeatAggregate> seats = db.getSeatAggregation();
		    return ok(seatdistribution.render(title, seats));
        } catch (DatabaseException e) {
            return badRequest("Database error");
        }
	}

}

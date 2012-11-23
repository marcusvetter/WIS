package controllers;

import java.util.ArrayList;
import java.util.List;

import common.Granularity;

import play.api.templates.Html;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Function1;
import scala.collection.script.Index;

import model.*;

import views.html.*;

public class Application extends Controller {

	public static Result seatdistribution(String granularity) {
		Granularity gran = null;
		try {
			gran = Granularity.valueOf(granularity.toUpperCase());
		} catch (Exception e) {
			return badRequest("Invalid parameter.");
		}

        DataCache.updateCache();
		
		String title = "Sitzveteilung fuer " + gran;
        List<SeatAggregate> seats = DataCache.getSeatAggregates();
		return ok(seatdistribution.render(title, seats));

	}

}

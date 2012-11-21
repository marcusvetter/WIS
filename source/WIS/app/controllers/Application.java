package controllers;

import java.util.ArrayList;
import java.util.List;

import common.Granularity;

import model.SeatAggregate;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Function1;
import scala.collection.script.Index;

import views.html.*;

public class Application extends Controller {

	public static Result seatdistribution(String granularity) {
		Granularity gran = null;
		try {
			gran = Granularity.valueOf(granularity.toUpperCase());
		} catch (Exception e) {
			return badRequest("Invalid parameter.");
		}
		
		String title = "Sitzveteilung fuer " + gran;
		
		List<SeatAggregate> seats = new ArrayList<SeatAggregate>();
		seats.add(new SeatAggregate("SPD", 23));
		seats.add(new SeatAggregate("CSU", 12));
		seats.add(new SeatAggregate("Die Gruenen", 47));
		seats.add(new SeatAggregate("CDU", 55));

		return ok(seatdistribution.render(title, seats));
	}

}
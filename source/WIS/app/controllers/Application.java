package controllers;

import java.util.List;

import model.DataCache;
import model.SeatAggregate;
import model.VoteAggregate;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.overview;
import views.html.seatdistribution;

public class Application extends Controller {

	/**
	 * Index
	 */
	public static Result index() {
		return redirect("/ueberblick?sicht=deutschland");
	}

	/**
	 * Overview
	 */
	public static Result overview(String view) {
		if (!(view.equals("deutschland") || view.equals("bundesland") || view
				.equals("wahlkreis"))) {
			return badRequest("Invalid parameter.");
		}

		DataCache.updateCache();

		List<VoteAggregate> votes = DataCache.getVoteAggregates(view);
		return ok(overview
				.render("Überblick: " + view.substring(0, 1).toUpperCase()
						+ view.substring(1), votes));
	}

	/**
	 * Seat distribution
	 */
	public static Result seatdistribution() {
		DataCache.updateCache();

		List<SeatAggregate> seats = DataCache.getSeatAggregates();
		return ok(seatdistribution.render("Sitzveteilung", seats));
	}
}

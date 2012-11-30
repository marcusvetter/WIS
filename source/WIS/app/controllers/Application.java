package controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import model.DataCache;
import model.VoteAggregate;
import model.Party;
import model.NarrowWinner;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.bundestagmembers;
import views.html.constituencywinners;
import views.html.narrowwinners;
import views.html.narrowwinners_json;
import views.html.overview;
import views.html.seatdistribution;
import views.html.excessmandates;

import common.DataManagerThread;
import common.db.DBConnect;

public class Application extends Controller {

	/**
	 * The interval time of updating the data cache (in seconds) [10min = 600s]
	 * 
	 */
	private static final int CACHE_UPDATE_INTERVAL = 600;

	/**
	 * The thread for managing data
	 */
	private static DataManagerThread dataManager = null;

	/**
	 * Index
	 */
	public static Result index() {
		initializeDataManager();
		return redirect("/ueberblick?sicht=deutschland");
	}

	/**
	 * Overview
	 */
	public static Result overview(String view) {
		initializeDataManager();

		if (view.equals("deutschland")) {
			List<VoteAggregate> votes = DataCache.getVoteAggregates();
			return ok(overview.render("Überblick: "
					+ view.substring(0, 1).toUpperCase() + view.substring(1),
					votes));
		} else if (view.equals("bundesland")) {
			// TODO
			List<VoteAggregate> votes = DataCache.getVoteAggregates();
			return ok(overview.render("Überblick: "
					+ view.substring(0, 1).toUpperCase() + view.substring(1),
					votes));
		} else if (view.equals("wahlkreis")) {
			// TODO
			List<VoteAggregate> votes = DataCache.getVoteAggregates();
			return ok(overview.render("Überblick: "
					+ view.substring(0, 1).toUpperCase() + view.substring(1),
					votes));
		}
		return badRequest("Invalid parameter.");

	}

	/**
	 * Constituency winners
	 */
	public static Result constituencywinners() {
		initializeDataManager();
		return ok(constituencywinners.render("Wahlkreissieger",
				DataCache.getConstituencyWinners()));
	}

    /**
     * Narrow Winners and Losers
     */
    public static Result narrowwinners() {
        initializeDataManager();
        List<Party> parties = DataCache.getParties();
        return ok(narrowwinners.render("Knappe Gewinner", parties));
    }

    public static Result narrowwinners_json(int party) {
        initializeDataManager();
        if (party == 0) {
            return badRequest("no party ID was provided");
        }
        return ok(narrowwinners_json.render(DataCache.getNarrowWinners(party)));
    }
    
    public static Result narrowlosers_json(int party) {
        initializeDataManager();
        if (party == 0) {
            return badRequest("no party ID was provided");
        }
        return ok(narrowwinners_json.render(DataCache.getNarrowLosers(party)));
    }



	/**
	 * Seat distribution
	 */
	public static Result seatdistribution() {
		initializeDataManager();
		return ok(seatdistribution.render("Sitzverteilung",
				DataCache.getSeatAggregates()));
	}

	/**
	 * Excess mandates
	 */
	public static Result excessmandates() {
		initializeDataManager();
		return ok(excessmandates.render("Überhangmandate",
				DataCache.getExcessMandates()));
	}

	/**
	 * Members of the bundestag
	 */
	public static Result bundestagmembers() {
		initializeDataManager();
		return ok(bundestagmembers.render("Bundestagsmitglieder",
				DataCache.getBundestagMembers()));
	}

	/**
	 * Initialize the data manager thread
	 */
	private static void initializeDataManager() {
		if (Application.dataManager == null) {

			// Create the database connection
			Configuration conf = play.Play.application().configuration();
			DBConnect db = new DBConnect(conf.getString("wisdb.connectstring"),
					conf.getString("wisdb.username"),
					conf.getString("wisdb.password"));

			// Interrupt the current data manager, if one exists
			if (Application.dataManager != null)
				Application.dataManager.interrupt();

			/*
			 * Create and aquire a semaphore, because the data cache is empty.
			 * The data manager will release the semaphore as soon as the data
			 * cache was updated.
			 */
			Semaphore semaphore = new Semaphore(0);
			Application.dataManager = new DataManagerThread(db,
					Application.CACHE_UPDATE_INTERVAL, semaphore);
			Application.dataManager.start();
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

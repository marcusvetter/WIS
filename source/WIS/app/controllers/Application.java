package controllers;

import java.util.List;
import java.util.concurrent.Semaphore;

import common.DataManagerThread;
import common.db.DBConnect;

import model.DataCache;
import model.VoteAggregate;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.overview;
import views.html.seatdistribution;

public class Application extends Controller {

	/**
	 * The interval time of updating the data cache (in seconds) [10min = 600s]
	 * 
	 */
	private static final int CACHE_UPDATE_INTERVAL = 60;

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
		if (!(view.equals("deutschland") || view.equals("bundesland") || view
				.equals("wahlkreis"))) {
			return badRequest("Invalid parameter.");
		}

		List<VoteAggregate> votes = DataCache.getVoteAggregates(view);
		return ok(overview
				.render("Überblick: " + view.substring(0, 1).toUpperCase()
						+ view.substring(1), votes));
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

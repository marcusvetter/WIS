package controllers;

import java.util.List;

import play.Configuration;

import model.DataCache;
import model.ElectoralDistrictWinner;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.electoraldistrictwinners;
import common.db.DBConnect;

public class ElectoralDistrict extends Controller {

	/**
	 * Electoral District Winners
	 */
	public static Result electoraldistrictwinners() {
	    Configuration conf = play.Play.application().configuration();
        DBConnect db = new DBConnect(conf.getString("wisdb.connectstring"), conf.getString("wisdb.username"), conf.getString("wisdb.password"));
	    List<ElectoralDistrictWinner> districts = db.getElectoralDistrictWinners();
        return ok(electoraldistrictwinners.render("Wahlkreissieger", districts));
	}
}

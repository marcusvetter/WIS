package model;

import java.util.ArrayList;
import java.util.List;

import play.Configuration;

import common.db.*;

public class DataCache {

    /**
     * Cached list of seats
     */
    private static List<SeatAggregate> seats = new ArrayList<SeatAggregate>();

    public static List<SeatAggregate> getSeatAggregates() {
        return DataCache.seats;
    }

    /**
     * Update the cache
     * @return false, if a database error occurred.
     */
    public static boolean updateCache() {
        try {
            Configuration conf = play.Play.application().configuration();
            DBConnect db = new DBConnect(conf.getString("wisdb.connectstring"), conf.getString("wisdb.username"), conf.getString("wisdb.password"));
            DataCache.seats = db.getSeatAggregation();
            return true;
        } catch (DatabaseException e) {
            return false;
        }
    }

}

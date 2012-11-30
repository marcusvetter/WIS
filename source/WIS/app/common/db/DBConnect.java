package common.db;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import common.IDataProvider;

import model.SeatAggregate;
import model.VoteAggregate;

public class DBConnect implements IDataProvider {
	private Connection con;

	public DBConnect(String connectstring, String username, String password) {
		try {
			Class.forName("org.postgresql.Driver");
			// con =
			// DriverManager.getConnection(conf.getString("wisdb.connectstring"),
			// conf.getString("wisdb.username"),
			// conf.getString("wisdb.password"));
			con = DriverManager
					.getConnection(connectstring, username, password);
		} catch (ClassNotFoundException e1) {
			throw new DatabaseException("driver not found");
		} catch (SQLException e2) {
			e2.printStackTrace();
			throw new DatabaseException("Connection failed");
		}
	}

	public ResultSet executeStatement(String stmt) throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(stmt);
		return rs;
	}

	public List<SeatAggregate> getSeatAggregation() {
		List<SeatAggregate> seats = new ArrayList<SeatAggregate>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM sitzverteilung ORDER BY partei");
			while (rs.next()) {
				seats.add(new SeatAggregate(rs.getString("name"), rs
						.getInt("sitze"), rs.getString("displaycolor")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return seats;
	}

	public List<VoteAggregate> getVoteAggregation() {
		List<VoteAggregate> votes = new ArrayList<VoteAggregate>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM partei_erststimmen_zweitstimmen_deutschland ORDER BY erststimmen DESC, zweitstimmen DESC");
			while (rs.next()) {
				votes.add(new VoteAggregate(rs.getString("partei"), rs
						.getInt("erststimmen"), rs.getInt("zweitstimmen"), 2009));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return votes;
	}
}

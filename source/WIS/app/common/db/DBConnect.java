package common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Party;
import model.BundestagMember;
import model.ConstituencyWinner;
import model.NarrowWinner;
import model.SeatAggregate;
import model.VoteAggregate;

import common.IDataProvider;

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

	public List<ConstituencyWinner> getConstituencyWinners() {
		List<ConstituencyWinner> winners = new ArrayList<ConstituencyWinner>(
				299);
		try {
			ResultSet rs = executeStatement("SELECT * FROM alle_wahlkreise_sieger WHERE wahl = 2009 order by wahlkreis");
			while (rs.next()) {
				winners.add(new ConstituencyWinner(rs.getInt("wahlkreis"), rs
						.getString("wahlkreisname"), rs
						.getString("erststimmensieger"), rs
						.getString("zweitstimmensieger")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return winners;

	}

	@Override
	public List<BundestagMember> getBundestagMembers() {
		List<BundestagMember> bundestagmembers = new ArrayList<BundestagMember>();

		// TODO

		return bundestagmembers;
	}

    public List<Party> getParties() {
        List<Party> parties = new ArrayList<Party>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM wis_partei order by id");
			while (rs.next()) {
				parties.add(new Party(rs.getInt("id"), rs.getString("name")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return parties;
    }
    
    
    public List<NarrowWinner> getNarrowWinners(int party) {
        List<NarrowWinner> nwinners = new ArrayList<NarrowWinner>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM alle_wahlkreise_knappste_gewinner WHERE wahl = 2009 AND partei = "+party+" order by diffreihenfolge");
			while (rs.next()) {
				nwinners.add(new NarrowWinner(rs.getInt("wahlkreis"), rs.getString("wahlkreisname"), rs.getString("vorname"), rs.getString("nachname"), rs.getString("parteiname"), rs.getInt("stimmdifferenz")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return nwinners;
    }
    
    
    public List<NarrowWinner> getNarrowLosers(int party) {
        List<NarrowWinner> nlosers = new ArrayList<NarrowWinner>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM alle_wahlkreise_knappste_verlierer WHERE wahl = 2009 AND partei = "+party+" order by diffreihenfolge");
			while (rs.next()) {
				nlosers.add(new NarrowWinner(rs.getInt("wahlkreis"), rs.getString("wahlkreisname"), rs.getString("vorname"), rs.getString("nachname"), rs.getString("parteiname"), rs.getInt("stimmdifferenz")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return nlosers;
    }
}

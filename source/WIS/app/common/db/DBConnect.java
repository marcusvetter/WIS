package common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import model.BallotEntry;
import model.BundestagMember;
import model.Constituency;
import model.ConstituencyInfo;
import model.ConstituencyWinner;
import model.ExcessMandate;
import model.NarrowWinner;
import model.Party;
import model.PartyVote;
import model.SeatAggregate;
import model.VoteAggregate;
import play.db.DB;

import common.IDataProvider;

public class DBConnect implements IDataProvider {
	private Connection con;

	public DBConnect() {
        con = DB.getConnection();
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
            e.printStackTrace();
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

	public List<BundestagMember> getBundestagMembers() {
		List<BundestagMember> bundestagmembers = new ArrayList<BundestagMember>();

		try {
			ResultSet rs = executeStatement("SELECT * FROM gewaehlte_bewerber");
			while (rs.next()) {

				// Set wahlkreis to '-', if the database returns '0'
				String wahlkreis = "-";
				if (rs.getInt("wahlkreis") != 0) {
					wahlkreis = String.valueOf(rs.getInt("wahlkreis"));
				}

				// Set the listenplatz to '-', if the database reutrns '0'
				String listenplatz = "-";
				if (rs.getInt("listenplatz") != 0) {
					listenplatz = String.valueOf(rs.getInt("listenplatz"));
				}

				bundestagmembers.add(new BundestagMember(rs
						.getString("bundesland"), rs.getString("partei"), rs
						.getString("vorname"), rs.getString("nachname"),
						wahlkreis, listenplatz));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}

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


    public List<ExcessMandate> getExcessMandates() {
		List<ExcessMandate> excessmandates = new ArrayList<ExcessMandate>();

		try {
			ResultSet rs = executeStatement("SELECT * FROM ueberhangmandate order by bundesland, partei");
			while (rs.next()) {
				excessmandates.add(new ExcessMandate(
						rs.getString("bundesland"), rs.getString("partei"), rs
								.getString("anz_ueberhangmandate")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return excessmandates;
	}
    
    public List<Constituency> getConstituencies() {
        List<Constituency> constituencies = new ArrayList<Constituency>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM wis_wahlkreis order by id");
			while (rs.next()) {
				constituencies.add(new Constituency(rs.getInt("id"), rs.getString("name")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return constituencies;
    }

    public ConstituencyInfo getConstituencyInfo(int constituency) {
		ConstituencyInfo info = null;
        try {
			ResultSet rs = executeStatement("select * from alle_wahlkreise_informationen where wahl = 2009 and wahlkreis = "+constituency);
			if (rs.next()) {
				info = new ConstituencyInfo(rs.getInt("wahlkreis"), rs.getString("wahlkreisname"), rs.getFloat("wahlbeteiligung"), rs.getString("vorname"), rs.getString("nachname"), rs.getString("parteiname"));
			} else {
			    rs.close();
                throw new DatabaseException("Wahlkreis nicht gefunden");
            }
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return info;
    } 


    public List<PartyVote> getPartySecondVotes(int constituency) {
        String stmt;
        if (213 > constituency || constituency > 217) {
            //stmt = "select * from wahlkreise_parteistimmen_beidejahre where wahlkreis = "+constituency+" order by partei";
            stmt = "select * from wahlkreise_parteistimmen_beidejahre("+constituency+") order by partei";
        } else {
            stmt = "select * from wahlkreise_parteistimmen_beidejahre_einzelstimmen where wahlkreis = "+constituency+" order by partei";
        }
        List<PartyVote> votes = new ArrayList<PartyVote>();
		try {
			ResultSet rs = executeStatement(stmt);
			while (rs.next()) {
				votes.add(new PartyVote(rs.getString("parteiname"), rs.getInt("absolut2009"), rs.getFloat("prozent2009"), rs.getInt("absolut2005"), rs.getFloat("prozent2005")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return votes;
    }

    public List<PartyVote> getPartyFirstVotes(int constituency) {
        String stmt;
        if (213 > constituency || constituency > 217) {
            //stmt = "select * from wahlkreise_erststimmen_beidejahre where wahlkreis = "+constituency+" order by partei";
            stmt = "select * from wahlkreise_erststimmen_beidejahre("+constituency+") order by partei";
        } else {
            stmt = "select * from wahlkreise_erststimmen_beidejahre_einzelstimmen where wahlkreis = "+constituency+" order by partei";
        }
        List<PartyVote> votes = new ArrayList<PartyVote>();
		try {
			ResultSet rs = executeStatement(stmt);
			while (rs.next()) {
				votes.add(new PartyVote(rs.getString("parteiname"), rs.getInt("absolut2009"), rs.getFloat("prozent2009"), rs.getInt("absolut2005"), rs.getFloat("prozent2005")));
			}
			rs.close();
		} catch (SQLException e) {
            e.printStackTrace();
			throw new DatabaseException("SELECT failed");
		}
		return votes;
    }
    
    public List<BallotEntry> getBallot(int constituency) {
        List<BallotEntry> ballot = new ArrayList<BallotEntry>();
		try {
			ResultSet rs = executeStatement("SELECT * FROM func_wahlkreis_stimmzettel("+constituency+")");
			while (rs.next()) {
				ballot.add(new BallotEntry(rs.getInt("kandidatid"), rs.getString("kandidatname"), rs.getString("kandidatpartei"), rs.getInt("parteiid"), rs.getString("parteikurzname"), rs.getString("listenkandidaten")));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException("SELECT failed");
		}
		return ballot; 
    }

    public int checkBallotCode(String ballotcode) {
        try {
        PreparedStatement stmt = con.prepareStatement("SELECT wahlkreis FROM wis_wahlzettel WHERE code = ? and abgestimmtam is null");
        stmt.setString(1, ballotcode);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("wahlkreis");
        } else {
            return -1;
        }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean checkBallot(int constituency, int candidateid, int partyid) {
        try {
        PreparedStatement stmtfirst = con.prepareStatement("select * from wis_kandidatur k where id = ? and wahlkreis = ? and wahl = 2");
        PreparedStatement stmtsecond = con.prepareStatement("select *from wis_landesliste ll, wis_wahlkreis wk where ll.id = ? and ll.wahl = 2 and wk.id = ? AND wk.bundesland = ll.bundesland");
        stmtfirst.setInt(1, candidateid);
        stmtfirst.setInt(2, constituency);
        stmtsecond.setInt(1, partyid);
        stmtsecond.setInt(2, constituency);
        ResultSet rsfirst = stmtfirst.executeQuery();
        ResultSet rssecond = stmtsecond.executeQuery();
        if (candidateid < 1 && partyid < 1)
            return true;
        else if (candidateid > 0 && partyid > 0)
            return rsfirst.next() && rssecond.next();
        else if (candidateid > 0)
            return rsfirst.next();
        else if (partyid > 0)
            return rssecond.next();
        else
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertBallot(String ballotcode, int candidateid, int partyid) {
        int constituency = checkBallotCode(ballotcode);
        if (constituency < 1 || !checkBallot(constituency, candidateid, partyid)) {
            //System.out.println("Constituency: "+constituency + ", Ballot nicht in Ordnung");
            return false;
        } else {
            try {
            con.setAutoCommit(false);
            if (candidateid > 0) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO wis_erststimme(fuerkandidat, abgegebenin) VALUES (?, ?)");
                stmt.setInt(1, candidateid);
                stmt.setInt(2, constituency);
                stmt.executeUpdate();
            }
            if (partyid > 0) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO wis_zweitstimme(fuerliste, abgegebenin) VALUES (?, ?)");
                stmt.setInt(1, partyid);
                stmt.setInt(2, constituency);
                stmt.executeUpdate();
            }
            PreparedStatement stmt = con.prepareStatement("update wis_wahlzettel set abgestimmtam = ('now'::text)::timestamp without time zone where code = ?");
            stmt.setString(1, ballotcode);
            stmt.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
            return true;
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                } catch (SQLException e2) {}
                return false;
            }
        }
    }
}

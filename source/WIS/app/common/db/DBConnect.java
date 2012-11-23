package common.db;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import model.SeatAggregate;

public class DBConnect {
    private Connection con;

    public DBConnect(String connectstring, String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            //con = DriverManager.getConnection(conf.getString("wisdb.connectstring"), conf.getString("wisdb.username"), conf.getString("wisdb.password"));
            con = DriverManager.getConnection(connectstring, username, password);
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
            ResultSet rs = executeStatement("SELECT * FROM sitzverteilung order by partei");
            while (rs.next()) {
                seats.add(new SeatAggregate(rs.getString("name"), rs.getInt("sitze"), rs.getString("displaycolor")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseException("SELECT failed");
        }
        return seats;
    }
}



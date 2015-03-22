/*package mtss.Gui.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.log4j.Logger;

import mtss.Gui.Entry;

import com.safeinstruments.bo.Entry;
import com.weighbridge.WeighingMachineSimpleRead;

public class JdbcGlobal {
	  Get actual class name to be printed on 
	  static Logger log = LogsMtss.getLogger(JdbcGlobal.class.getName());

	static public String url = "jdbc:mysql://localhost:3306/weighbridgesystem";
	static public String user = "root";
	static public String password = "root";
	static public void insert(Entry e) {
		Connection connection;
		Statement statement;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			statement.executeUpdate("insert into entry (epcnum, weight) values ('"
				+ e.getEpcNum() + "', '" + e.getWeight() + "')");

			statement.close();
			connection.close();

		} catch (Exception es) {
			log.debug(es.getMessage());
		}
	}

	public void update(Entry e) {
		Connection connection;
		Statement statement;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception es) {
			log.debug(es.getMessage());
		}
	}

	public void delete(Entry e) {
		Connection connection;

		Statement statement;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			statement.executeUpdate();
			statement.close();
			connection.close();

		} catch (Exception es) {
			log.debug(es.getMessage());
		}
	}

}
*/
package Penyebrangan_Kapal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SQL_Connection {
	Connection con=null;
	public Connection getKoneksi() {
		if(con==null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://"
						+ "localhost/bongkarmuat","root","");
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, ex.getMessage());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return con;
	}
}

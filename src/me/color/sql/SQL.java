package me.color.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.color.main.main;
import me.color.managers.TeamManager;
import me.color.teams.ConfigTeam;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SQL {
	
	private Connection c;
	private static SQL instance;
	public static SQL getInstance() {
		return instance;
	}
	
	public SQL() {
		instance = this;
	}
	
	public static boolean isEnabled = false;
	
	@SuppressWarnings("deprecation")
	public void connect() {
		String host = main.getInstance().getConfig().getString("database.host");
		String port = main.getInstance().getConfig().getString("database.port");
		String db = main.getInstance().getConfig().getString("database.database");
		String user = main.getInstance().getConfig().getString("database.user");
		String pass = main.getInstance().getConfig().getString("database.pass");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, pass);
		} 
		catch (ClassNotFoundException | SQLException e) {
		}
		createTables();
		for(Player pls : Bukkit.getOnlinePlayers()) {
			if(!isSet(pls)) {
				initializePlayer(pls);
			}
		}
	}
	
	public Connection getConnection() {
		return c;
	}
	
	public void close() {
		try {
			c.close();
		}
		catch (SQLException e) {}
	}
	
	public void createTables() {
		try {
			Statement stmt = c.createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Teams (Name VARCHAR(255), Prefix VARCHAR(255), Suffix VARCHAR(255))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TPlayers (UUID VARCHAR(255), Name VARCHAR(30), Rank VARCHAR(30))");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TList (Name VARCHAR(255), List VARCHAR(255))");
			stmt.executeUpdate("INSERT INTO `TList` VALUES ('test', 'standart')");
			stmt.close();
		} catch (SQLException e) {
		}
	}
	
	public boolean isSet(Player p) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TPlayers WHERE UUID = '" + p.getUniqueId().toString() + "'");
			return rs.next();
		} catch (SQLException e) {
			
		}
		return false;
	}
	
	public boolean isSet(String uuid) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TPlayers WHERE UUID = '" + uuid + "'");
			return rs.next();
		} catch (SQLException e) {
			
		}
		return false;
	}
	
	public void initializePlayer(Player p) {
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("INSERT INTO `TPlayers` VALUES ('" + p.getUniqueId().toString() + "', '" + p.getName() + "', 'standart')");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getTeams() {
		try {
			try {
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Tlist WHERE Name = 'test'");
				if(rs.next()) {
					String s = rs.getString("List");
					String[] array = s.split("|");
					return array;
				}
			} 
			catch (SQLException e) {
				
			}
		}
		catch(Exception ex) {
			
		}
		return new String[]{"standart"};
	}
	
	public void saveTeams() {
		String s = "";
		for(ConfigTeam t : TeamManager.getInstance().getTeams()) {
			s = s + "|" + t.getName();
		}
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("UPDATE TList SET List = '" + s + "' WHERE Name = 'test'");
		}
		catch(SQLException e) {
		}
	}
	
	public String getPrefix(String s) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Teams WHERE Name = '" + s + "'");
			if(rs.next()) {
				return rs.getString("Prefix");
			}
		}
		catch (SQLException e) {
			
		}
		return "";
	}
	
	public String getSuffix(String s) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Teams WHERE Name = '" + s + "'");
			if(rs.next()) {
				return rs.getString("Suffix");
			}
		}
		catch (SQLException e) {
			
		}
		return "";
	}
	
	public void setPrefix(String rank, String prefix) {
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("UPDATE Teams SET Prefix = '" + prefix + "' WHERE Name = '" + rank + "'");
		} catch (SQLException e) {
			
		}
	}
	
	public void setSuffix(String rank, String suffix) {
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("UPDATE Teams SET Suffix = '" + suffix + "' WHERE Name = '" + rank + "'");
		} catch (SQLException e) {
			
		}
	}
	
	public String getRank(Player p) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TPlayers WHERE UUID = '" + p.getUniqueId().toString() + "'");
			if(rs.next()) {
				return rs.getString("Rank");
			}
		}
		catch (SQLException e) {
			
		}
		return "";
	}
	
	public void setRank(Player p, String s) {
		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("UPDATE TPlayers SET Rank = '" + s + "' WHERE UUID = '" + p.getUniqueId().toString() + "'");
		} catch (SQLException e) {
			
		}
	}
	
}
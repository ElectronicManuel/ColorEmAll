package me.color.teams;

import me.color.main.main;
import me.color.managers.TeamManager;
import me.color.sql.SQL;

import org.bukkit.entity.Player;

public class TeamUser {
	
	private Player p;
	
	
	public TeamUser(Player p) {
		this.p = p;
	}
	
	public ConfigTeam getRank() {
		String name = "";
		if(SQL.isEnabled) {
			name = SQL.getInstance().getRank(p);
		}
		else {
			name = main.getInstance().users.getString("user." + p.getUniqueId().toString() + ".rank");
		}
		if(TeamManager.getInstance().getTeam(name) != null) {
			return TeamManager.getInstance().getTeam(name);
		}
		else {
			return null;
		}
	}
	
	public void setRank(ConfigTeam t) {
		if(SQL.isEnabled) {
			SQL.getInstance().setRank(p, t.getName());
		}
		else {
			main.getInstance().users.set("user." + p.getUniqueId().toString() + ".rank", t.getName());
			main.getInstance().saveUsers();
		}
	}

}

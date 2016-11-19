package me.color.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.color.main.main;
import me.color.sql.SQL;
import me.color.teams.ConfigTeam;
import me.color.teams.TeamUser;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {
	
	private static TeamManager instance;
	public static TeamManager getInstance() {
		return instance;
	}
	
	private ArrayList<ConfigTeam> teams = new ArrayList<ConfigTeam>();
	private Scoreboard sb = null;
	public HashMap<String, TeamUser> users = new HashMap<String, TeamUser>();
	
	public TeamManager() {
		instance = this;
		sb = main.getInstance().getServer().getScoreboardManager().getNewScoreboard();
		loadTeams();
		initializeTeams();
	}
	
	public Scoreboard getScoreboard() {
		return sb;
	}
	
	public ArrayList<ConfigTeam> getTeams() {
		return teams;
	}
	
	public void setTeams(ArrayList<ConfigTeam> list) {
		teams = list;
	}
	
	public void loadTeams() {
		List<String> array = main.getInstance().ranks.getStringList("list");
		if(SQL.isEnabled) {
			if(SQL.getInstance().getTeams().length > 0) {
				for(String s : SQL.getInstance().getTeams()) {
					try {
						teams.add(new ConfigTeam(s.toLowerCase(), SQL.getInstance().getPrefix(s), SQL.getInstance().getSuffix(s)));
					}
					catch(NullPointerException ex) {}
				}
			}
		}
		else {
			for(String s : array) {
				try {
					teams.add(new ConfigTeam(s.toLowerCase(), main.getInstance().ranks.getString("rank." + s.toLowerCase() + ".prefix"), main.getInstance().ranks.getString("rank." + s.toLowerCase() + ".suffix")));
				}
				catch(NullPointerException ex) {}
			}
		}
	}
	
	public void initializeTeams() {
		for(ConfigTeam ct : teams) {
			Team t = sb.getTeam(ct.getName());
			if(t == null) {
				t = sb.registerNewTeam(ct.getName());
			}
			if(ct.getPrefix() != null) {
				t.setPrefix(ct.getPrefix());
			}
			if(ct.getSuffix() != null) {
				t.setSuffix(ct.getSuffix());
			}
			ct.setTeam(t);
		}
	}
	
	public void addToTeam(Player p) {
		ConfigTeam team = null;
		p.setScoreboard(sb);
		String group = "";
		if(SQL.isEnabled) {
			group = SQL.getInstance().getRank(p);
		}
		else {
			if(getTeamUser(p).getRank() != null) {
				group = getTeamUser(p).getRank().getName();
			}
			else {
				team = getTeam("standart");
			}
		}
		if(getTeam(group) != null) {
			team = getTeam(group);
		}
		else {
			team = getTeam("standart");
		}
		if(!team.getTeam().hasPlayer(p)) team.getTeam().addPlayer(p);
		p.setDisplayName(team.getPrefix() + p.getName() + team.getSuffix());
		String tabname = team.getPrefix() + p.getName() + team.getSuffix();
		if(tabname.length() > 16) {
			tabname = tabname.substring(0, 15);
		}
		p.setPlayerListName(tabname);
	}
	
	@SuppressWarnings("deprecation")
	public void reload() {
		getInstance().setTeams(new ArrayList<ConfigTeam>());
		getInstance().loadTeams();
		getInstance().initializeTeams();
		for(Player pls : Bukkit.getOnlinePlayers()) {
			getInstance().addToTeam(pls);
		}
	}
	
	public void removeFromTeam(Player p) {
		for(ConfigTeam team : teams) {
			if(team.getTeam().hasPlayer(p)) {
				main.getInstance().users.set("user." + p.getUniqueId().toString() + ".rank", team.getName());
				main.getInstance().saveUsers();
				team.getTeam().removePlayer(p);
			}
		}
		p.setDisplayName(p.getName());
	}

	public ConfigTeam getTeam(String string) {
		for(ConfigTeam ct : teams) {
			if(ct.getName().equalsIgnoreCase(string)) {
				return ct;
			}
		}
		return null;
	}
	
	public TeamUser getTeamUser(Player p) {
		TeamUser tu;
		if(!users.containsKey(p.getName())) {
			tu = new TeamUser(p);
			users.put(p.getName(), tu);
		}
		else {
			tu = users.get(p.getName());
		}
		return tu;
	}

}

package me.color.teams;

import java.util.ArrayList;
import java.util.List;

import me.color.main.main;
import me.color.managers.TeamManager;
import me.color.sql.SQL;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public class ConfigTeam {
	
	private String name;
	private String prefix;
	private String suffix;
	private Team team;
	
	public ConfigTeam(String name, String prefix, String suffix) {
		this.name = name.toLowerCase();
		
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix).replaceAll("_", " ");
		this.suffix = ChatColor.translateAlternateColorCodes('&', suffix).replaceAll("_", " ");
	}
	
	public String getName() {
		return name;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setPrefix(String s) {
		prefix = ChatColor.translateAlternateColorCodes('&', s).replaceAll("_", " ");
	}
	
	public void setSuffix(String s) {
		suffix = ChatColor.translateAlternateColorCodes('&', s).replaceAll("_", " ");
	}
	
	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team t) {
		team = t;
	}
	
	public void save() {
		if(SQL.isEnabled) {
			SQL.getInstance().setPrefix(getName(), getPrefix());
			SQL.getInstance().setSuffix(getName(), getSuffix());
			SQL.getInstance().saveTeams();
		}
		else {
			main.getInstance().ranks.set("rank." + name + ".prefix", prefix);
			main.getInstance().ranks.set("rank." + name + ".suffix", suffix);
			List<String> list = new ArrayList<String>();
			if(main.getInstance().ranks.isSet("list")) list = main.getInstance().ranks.getStringList("list");
			if(!list.contains(name)) {
				list.add(name);
				main.getInstance().ranks.set("list", list);
			}
			main.getInstance().saveRanks();
		}
	}
	
	public void delete() {
		ArrayList<ConfigTeam> teams = TeamManager.getInstance().getTeams();
		teams.remove(this);
		TeamManager.getInstance().setTeams(teams);
		if(SQL.isEnabled) {
			SQL.getInstance().saveTeams();
		}
		else {
			main.getInstance().ranks.set("rank." + name, null);
			List<String> list = new ArrayList<String>();
			if(main.getInstance().ranks.isSet("list")) list = main.getInstance().ranks.getStringList("list");
			if(list.contains(name)) {
				list.remove(name);
				main.getInstance().ranks.set("list", list);
				
			}
			main.getInstance().saveRanks();
		}
	}

}

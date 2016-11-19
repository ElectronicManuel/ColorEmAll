package me.color.main;

import java.io.File;
import java.io.IOException;

import me.color.listeners.MainListener;
import me.color.managers.CommandManager;
import me.color.managers.TeamManager;
import me.color.sql.SQL;
import me.color.teams.ConfigTeam;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	private static main instance;
	public static main getInstance() {
		return instance;
	}
	
	public FileConfiguration users;
	public FileConfiguration ranks;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		new SQL();
		if(SQL.isEnabled) {
			SQL.getInstance().connect();
			SQL.getInstance().createTables();
		}
		CommandManager cm = new CommandManager();
		cm.setup();
		getCommand("colorthemall").setExecutor(cm);
		getServer().getPluginManager().registerEvents(new MainListener(), this);
		handleFiles();
		new TeamManager();
		saveStandart();
		for(Player pls : Bukkit.getOnlinePlayers()) {
			if(SQL.isEnabled) {
				SQL.getInstance().initializePlayer(pls);
			}
			pls.setScoreboard(TeamManager.getInstance().getScoreboard());
			TeamManager.getInstance().addToTeam(pls);
		}
		System.out.println(getDescription().getName() + " v" + getDescription().getVersion() + " enabled!");
	}
	
	public void onDisable() {
		for(ConfigTeam t : TeamManager.getInstance().getTeams()) {
			t.save();
		}
		if(SQL.isEnabled) {
			SQL.getInstance().close();
		}
		System.out.println(getDescription().getName() + " v" + getDescription().getVersion() + " disabled!");
	}
	
	public void saveUsers() {
		try {
			users.save(new File(getDataFolder(), "users.yml"));
		} catch (IOException e) {
		}
	}
	
	public void saveRanks() {
		try {
			ranks.save(new File(getDataFolder(), "ranks.yml"));
		} catch (IOException e) {
		}
	}
	
	public void handleFiles() {
		users = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "users.yml"));
		saveUsers();
		ranks = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "ranks.yml"));
		saveRanks();
		saveDefaultConfig();
		if(getConfig().getBoolean("usesql")) {
			SQL.isEnabled = true;
		}
	}
	
	public void saveStandart() {
		if(!ranks.isSet("rank.standart")) {
			ConfigTeam standart = new ConfigTeam("standart", "&2", "");
			standart.save();
		}
	}
	
}

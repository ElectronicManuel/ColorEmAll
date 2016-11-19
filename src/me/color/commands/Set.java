package me.color.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.color.main.main;
import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;

public class Set extends TCommand {

	public Set() {
		super("set a user's rank", "<player> <rankname>", "set");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.set")) return;
		if(args.length < 2) {
			MessageManager.getInstance().message(p, MessageType.BAD, "You have to enter a valid name and a rank");
			return;
		}
		if(TeamManager.getInstance().getTeam(args[1]) == null) {
			MessageManager.getInstance().message(p, MessageType.BAD, "The rank '" + args[1] + "' was not found!");
			return;
		}
		try {
			@SuppressWarnings("deprecation")
			OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
			if(pl.isOnline()) {
				TeamManager.getInstance().getTeamUser(Bukkit.getPlayer(args[0])).setRank(TeamManager.getInstance().getTeam(args[1]));
			}
			else {
				main.getInstance().users.set("user." + pl.getUniqueId().toString() + ".rank", args[1]);
			}
			TeamManager.getInstance().reload();
			MessageManager.getInstance().message(p, MessageType.GOOD, "The rank '" + args[1] + "' has been set to '" + args[0] + "'!");
		}
		catch(Exception ex) {
			MessageManager.getInstance().message(p, MessageType.BAD, "An error occured, maybe the rank or the player was not found?");
		}
	}

}

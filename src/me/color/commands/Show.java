package me.color.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.color.main.main;
import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;

public class Show extends TCommand {

	public Show() {
		super("Shows the rank of a user", "[username]", "show");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.show")) return;
		if(args.length < 1) {
			try {
				MessageManager.getInstance().message(p, MessageType.INFO, "Your rank: §d" + TeamManager.getInstance().getTeamUser(p).getRank().getName());
			}
			catch(NullPointerException ex) {
				MessageManager.getInstance().message(p, MessageType.INFO, "Your rank: §4NONE");
			}
			return;
		}
		try {
			MessageManager.getInstance().message(p, MessageType.INFO, args[0] + "'s rank: §d" + TeamManager.getInstance().getTeamUser(Bukkit.getPlayer(args[1])).getRank().getName());
		}
		catch(Exception ex) {
			try {
				MessageManager.getInstance().message(p, MessageType.INFO, args[0] + "'s rank: §d" + main.getInstance().users.getString("user." + Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString() + ".rank"));
			}
			catch(Exception e) {
				MessageManager.getInstance().message(p, MessageType.BAD, "Player not found!");
			}
		}
	}

}

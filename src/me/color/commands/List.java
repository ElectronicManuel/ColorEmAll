package me.color.commands;

import org.bukkit.entity.Player;

import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;
import me.color.teams.ConfigTeam;

public class List extends TCommand {

	public List() {
		super("list all the ranks", "", "list");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.list")) return;
		p.sendMessage(" ");
		MessageManager.getInstance().message(p, MessageType.INFO, "§aThe following ranks are loaded:");
		for(ConfigTeam t : TeamManager.getInstance().getTeams()) {
			MessageManager.getInstance().message(p, MessageType.INFO, "Rank: §b" + t.getName());
			MessageManager.getInstance().message(p, MessageType.INFO, "  Prefix: §d" + t.getPrefix().replaceAll("§", "&"));
			MessageManager.getInstance().message(p, MessageType.INFO, "  Suffix: §d" + t.getSuffix().replaceAll("§", "&"));
			p.sendMessage(" ");
		}
	}

}

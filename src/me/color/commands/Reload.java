package me.color.commands;

import org.bukkit.entity.Player;

import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;

public class Reload extends TCommand {
	
	public Reload() {
		super("reload the plugin", "", "reload");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.reload")) return;
		TeamManager.getInstance().reload();
		MessageManager.getInstance().message(p, MessageType.GOOD, "Plugin successfully reloaded!");
	}

}

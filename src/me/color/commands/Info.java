package me.color.commands;

import me.color.main.main;
import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.MessageManager.MessageType;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Info extends TCommand {
	
	public Info() {
		super("informations", "", "info");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.info")) return;
		p.sendMessage(" ");
		MessageManager.getInstance().message(p, MessageType.INFO, "§3[ColorThemAll v" + main.getInstance().getDescription().getVersion() + " developed by Emanuel149i]");
		MessageManager.getInstance().message(p, MessageType.INFO, "tip: you can use & to color the ranks!");
		MessageManager.getInstance().message(p, MessageType.INFO, "tip: _ stands for space");
		MessageManager.getInstance().message(p, MessageType.INFO, "§5Permissionlist:");
		for(Permission perm : main.getInstance().getDescription().getPermissions()) {
			MessageManager.getInstance().message(p, MessageType.INFO, "§8" + perm.getName() + " - " + perm.getDescription());
		}
		p.sendMessage(" ");
	}

}

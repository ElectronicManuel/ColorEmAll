package me.color.commands;

import org.bukkit.entity.Player;

import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;
import me.color.teams.ConfigTeam;

public class Create extends TCommand {

	public Create() {
		super("Create a rank", "<rankname>", "create");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.create")) return;
		if(args.length < 1) {
			MessageManager.getInstance().message(p, MessageType.BAD, "You have to enter a valid name");
			return;
		}
		if(TeamManager.getInstance().getTeam(args[0]) != null) {
			MessageManager.getInstance().message(p, MessageType.BAD, "The rank '" + args[0] + "' does already exist!");
			return;
		}
		ConfigTeam t = new ConfigTeam(args[0], "", "");
		t.save();
		TeamManager.getInstance().reload();
		MessageManager.getInstance().message(p, MessageType.GOOD, "The rank '" + args[0] + "' has been created!");
	}

}

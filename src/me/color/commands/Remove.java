package me.color.commands;

import org.bukkit.entity.Player;

import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;
import me.color.teams.ConfigTeam;

public class Remove extends TCommand {

	public Remove() {
		super("Remove a rank", "<rankname>", "remove");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.remove")) return;
		if(args.length < 1) {
			MessageManager.getInstance().message(p, MessageType.BAD, "You have to enter a valid name");
			return;
		}
		if(TeamManager.getInstance().getTeam(args[0]) == null) {
			MessageManager.getInstance().message(p, MessageType.BAD, "The rank '" + args[0] + "' does not exist!");
			return;
		}
		ConfigTeam t = TeamManager.getInstance().getTeam(args[0]);
		t.delete();
		TeamManager.getInstance().reload();
		MessageManager.getInstance().message(p, MessageType.GOOD, "The rank '" + args[0] + "' has been deleted!");
	}

}

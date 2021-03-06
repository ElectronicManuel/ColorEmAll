package me.color.commands;

import org.bukkit.entity.Player;

import me.color.managers.MessageManager;
import me.color.managers.TCommand;
import me.color.managers.TeamManager;
import me.color.managers.MessageManager.MessageType;
import me.color.teams.ConfigTeam;

public class Suffix extends TCommand {

	public Suffix() {
		super("set a suffix", "<rankname> <suffix (leave blank to remove suffix)>", "suffix");
	}

	@Override
	public void onCommand(Player p, String[] args) {
		if(!p.hasPermission("cta.suffix")) return;
		if(args.length < 1) {
			MessageManager.getInstance().message(p, MessageType.BAD, "You have to enter a valid name");
			return;
		}
		if(TeamManager.getInstance().getTeam(args[0]) == null) {
			MessageManager.getInstance().message(p, MessageType.BAD, "The rank '" + args[0] + "' does not exist!");
			return;
		}
		ConfigTeam t = TeamManager.getInstance().getTeam(args[0]);
		if(args.length < 2) {
			t.setSuffix("");
		}
		else {
			if(args[1].length() > 15) {
				MessageManager.getInstance().message(p, MessageType.BAD, "Your suffix was too long");
				return;
			}
			else {
				t.setSuffix(args[1]);
			}
		}
		t.save();
		TeamManager.getInstance().reload();
		MessageManager.getInstance().message(p, MessageType.GOOD, "The rank '" + args[0] + "' has received its new suffix!");
	}

}

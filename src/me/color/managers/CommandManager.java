package me.color.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import me.color.commands.Create;
import me.color.commands.Info;
import me.color.commands.List;
import me.color.commands.Prefix;
import me.color.commands.Reload;
import me.color.commands.Remove;
import me.color.commands.Set;
import me.color.commands.Show;
import me.color.commands.Suffix;
import me.color.managers.MessageManager.MessageType;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

	private ArrayList<TCommand> cmds = new ArrayList<TCommand>();

	public void setup() {
		cmds.add(new Set());
		cmds.add(new Show());
		cmds.add(new Create());
		cmds.add(new Remove());
		cmds.add(new Prefix());
		cmds.add(new Suffix());
		cmds.add(new List());
		cmds.add(new Info());
		cmds.add(new Reload());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			MessageManager.getInstance().message(sender, MessageType.BAD, "Only players can use commands!");
			return true;
		}

		Player p = (Player) sender;
		
		if (args.length == 0) {
			if(!p.hasPermission("cta.info")) return true;
			for (TCommand mc : cmds) MessageManager.getInstance().message(p, MessageType.INFO, "/cta " + aliases(mc) + " " + mc.getUsage() + " - " + mc.getMessage());
			return true;
		}

		TCommand c = getCommand(args[0]);

		if (c == null) {
			if(!p.hasPermission("cta.info")) return true;
			MessageManager.getInstance().message(sender, MessageType.BAD, "This command does not exist!");
			return true;
		}
		
		Vector<String> a = new Vector<String>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);

		c.onCommand(p, args);

		return true;
	}

	private String aliases(TCommand cmd) {
		String fin = "";

		for (String a : cmd.getAliases()) {
			fin += a + " | ";
		}

		return fin.substring(0, fin.lastIndexOf(" | "));
	}

	private TCommand getCommand(String name) {
		for (TCommand cmd : cmds) {
			if (cmd.getClass().getSimpleName().equalsIgnoreCase(name)) return cmd;
			for (String alias : cmd.getAliases()) if (name.equalsIgnoreCase(alias)) return cmd;
		}
		return null;
	}
}
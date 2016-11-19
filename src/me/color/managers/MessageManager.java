package me.color.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

	 private static MessageManager instance = new MessageManager();
	 public String prefix = "§bColorThemAll§8\u00BB " + ChatColor.RESET;

	 public void message(CommandSender sender, MessageType type, String... messages) {
	    for (String m : messages)
	      sender.sendMessage(this.prefix + type.getColor() + m);
	  }

	 public void broadcast(MessageType type, String... messages) {
	   for (String m : messages)
	     Bukkit.broadcastMessage(this.prefix + type.getColor() + m);
	  }

	 public void setPrefix(String s) {
	   this.prefix = s;
	 }

	 public static MessageManager getInstance() {
	   return instance;
	 }

	 public static enum MessageType {
	   INFO(ChatColor.GRAY), 
	   GOOD(ChatColor.GOLD), 
	   BAD(ChatColor.RED);

	   private ChatColor color;

	   private MessageType(ChatColor color) {
	     this.color = color;
	   }

	   public ChatColor getColor() {
	     return this.color;
	   }
   }
}

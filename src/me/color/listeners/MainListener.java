package me.color.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.color.managers.TeamManager;

public class MainListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		TeamManager.getInstance().addToTeam(e.getPlayer());
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		TeamManager.getInstance().removeFromTeam(e.getPlayer());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		TeamManager.getInstance().removeFromTeam(e.getPlayer());
	}
}

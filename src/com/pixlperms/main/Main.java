package com.pixlperms.main;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener
{
	@SuppressWarnings("unused")
	private String pluginPrefix;
	private final String PERMISSIONMESSAGE = ChatColor.RED + "You do not have permission to do that";
	
	private HashMap<UUID, PermissionAttachment> playerPermissions = new HashMap<>();
	
	private void setupPermissions(Player p)
	{
		PermissionAttachment attach = p.addAttachment(this);
		
		for (String permission : getConfig().getStringList("groups." + API.getGroup(p.getUniqueId()) + ".permissions"))
			attach.setPermission(permission, true);
		for (String permission : getConfig().getStringList("players." + p.getUniqueId().toString() + ".permissions"))
			attach.setPermission(permission, true);
		
		playerPermissions.put(p.getUniqueId(), attach);
	}
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		
		if (!new File(getDataFolder(), "config.yml").exists())
		{
			getConfig().options().copyDefaults();
			saveConfig();
		}
		
		pluginPrefix = API.color(getConfig().getString("pluginPrefix"));
		
		for (Player p : getServer().getOnlinePlayers())
			setupPermissions(p);
	}
	
	@Override
	public void onDisable()
	{
		playerPermissions.clear();
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		if (!p.hasPermission("blockbreak"))
		{
			e.setCancelled(true);
			p.sendMessage(PERMISSIONMESSAGE);
		}
	}
	
	@EventHandler
	public void placeBlock(BlockPlaceEvent e)
	{
		Player p = e.getPlayer();
		if (!p.hasPermission("blockplace"))
		{
			e.setCancelled(true);
			p.sendMessage(PERMISSIONMESSAGE);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		setupPermissions(p);
		p.setPlayerListName(API.color(API.getPlayerName(p)));
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		playerPermissions.remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		e.setFormat(API.color(API.getPlayerDisplayName(p) + " &8> " + API.getPlayerChatColor(p.getUniqueId()) + e.getMessage()));
		p.setPlayerListName(API.color(API.getPlayerName(p)));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("pixlperms"))
		{
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("reload"))
				{
					reloadConfig();
					API.config = getConfig();
					
					for (Player p : getServer().getOnlinePlayers())
						setupPermissions(p);
					
					sender.sendMessage(API.color("&cReloaded PixlPerms"));
					return true;
				}
				else if (args[0].equalsIgnoreCase("setrank"))
				{
					
				}
			}
			sender.sendMessage(API.color("&4/perms:"));
			sender.sendMessage(API.color(" - &creload"));
			return true;
		}
		
		return false;
	}
}










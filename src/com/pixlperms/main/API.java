package com.pixlperms.main;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class API
{
	public static FileConfiguration config = Bukkit.getServer().getPluginManager().getPlugin("PixlPerms").getConfig();
	
	public static String color(String arg0)
	{
		return ChatColor.translateAlternateColorCodes('&', arg0);
	}
	
	public static void broadcast(String arg0)
	{
		Bukkit.getServer().broadcastMessage(arg0);
	}
	
	public static String getPlayerPrefix(UUID id)
	{
		String prefix = config.getString("players." + id.toString() + ".prefix");
		return prefix == null ? getGroupPrefix(getGroup(id)) : prefix;
	}
	
	public static String getGroupPrefix(String group)
	{
		String prefix = config.getString("groups." + group + ".prefix");
		return prefix == null ? config.getString("defaults.prefix") : prefix;
	}
	
	public static String getPlayerSuffix(UUID id)
	{
		String suffix = config.getString("players." + id.toString() + ".suffix");
		return suffix == null ? getGroupSuffix(getGroup(id)) : suffix;
	}
	
	public static String getGroupSuffix(String group)
	{
		String suffix = config.getString("groups." + group + ".suffix");
		return suffix == null ? config.getString("defaults.suffix") : suffix;
	}
	
	public static String getPlayerChatColor(UUID id)
	{
		String chatColor = config.getString("players." + id.toString() + ".chatColor");
		return chatColor == null ? getGroupChatColor(getGroup(id)) : chatColor;
	}
	
	public static String getGroupChatColor(String group)
	{
		String chatColor = config.getString("groups." + group + ".chatColor");
		return chatColor == null ? config.getString("defaults.chatColor") : chatColor;
	}
	
	public static String getPlayerNameColor(UUID id)
	{
		String nameColor = config.getString("players." + id.toString() + ".nameColor");
		return nameColor == null ? getGroupNameColor(getGroup(id)) : nameColor;
	}
	
	public static String getGroupNameColor(String group)
	{
		String nameColor = config.getString("groups." + group + ".nameColor");
		return nameColor == null ? config.getString("defaults.nameColor") : nameColor;
	}
	
	public static String getGroup(UUID id)
	{
		String group = config.getString("players." + id.toString() + ".group");
		return group == null ? "member" : group;
	}
	
	public static String getPlayerName(Player p)
	{
		String result = "";
		
		String prefix = getPlayerPrefix(p.getUniqueId());
		String suffix = getPlayerSuffix(p.getUniqueId());
		
		if (prefix != "") result += prefix + " ";
		result += getPlayerNameColor(p.getUniqueId()) + p.getName();
		if (suffix != "") result += " " + suffix;
		
		return result;
	}

	public static String getPlayerDisplayName(Player p)
	{
		String result = "";
		
		String prefix = getPlayerPrefix(p.getUniqueId());
		String suffix = getPlayerSuffix(p.getUniqueId());
		
		if (prefix != "") result += prefix + " ";
		result += getPlayerNameColor(p.getUniqueId()) + p.getDisplayName();
		if (suffix != "") result += " " + suffix;
		
		return result;
	}
}

package fr.vaevictis.vveconomy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VVEconomy extends JavaPlugin
{
	
	@Override
	public void onEnable()
	{
		
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static int getArgent(Player p)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("argent." + p.getName()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argent." + p.getName(), 0);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
		return Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("argent." + p.getName());
	}
	public static void payer(Player p1, Player p2, int as)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("argent." + p1.getName()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argent." + p1.getName(), 0);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("argent." + p2.getName()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argent." + p2.getName(), 0);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("argent." + p1.getName()) >= as)
		{
			setArgent(p1, getArgent(p1) - as);
			setArgent(p2, getArgent(p2) + as);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
	}
	public static void setArgent(Player p, int as)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("argent." + p.getName()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argent." + p.getName(), 0);
		}
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argent." + p.getName(), as);
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label,String[] args)
	{
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("argent"))
		{
			if (args.length == 0)
			{
				p.sendMessage(ChatColor.GOLD + "Vous possédez " + getArgent(p) + " As.");
				return true;
			}
			else if (args.length == 1)
			{
				Player p2 = Bukkit.getServer().getPlayer(args[0]);
				if (p2 != null)
				{
					p.sendMessage(ChatColor.GOLD + p2.getDisplayName() + " possède " + getArgent(p2) + " As.");
				}
			}
		}
		else if (label.equalsIgnoreCase("payer") && args.length == 2)
		{
			Player p2 = Bukkit.getServer().getPlayer(args[0]);
			if (p2 != null && Integer.valueOf(args[1]) > 0)
			{
				if (Integer.valueOf(args[1]) > getArgent(p))
				{
					p.sendMessage(ChatColor.GOLD + "Vous n'avez pas assez d'argent !");
					return true;
				}
				payer(p, p2, Integer.valueOf(args[1]));
				p.sendMessage(ChatColor.GOLD + "Vous avez envoyé " + Integer.valueOf(args[1]) + " As à " + p2.getDisplayName() + ".");
				p2.sendMessage(ChatColor.GOLD + "Vous avez reçu " + Integer.valueOf(args[1]) + " As de " + p.getDisplayName() + ".");
				return true;
			}
		}
		else if (label.equalsIgnoreCase("mettreargent") && args.length == 2)
		{
			Player p2 = Bukkit.getServer().getPlayer(args[0]);
			if(p2 != null && Integer.valueOf(args[1]) > 0)
			{
				setArgent(p2, Integer.valueOf(args[1]));
				p.sendMessage(ChatColor.GOLD + p2.getDisplayName() + " a désormais " + Integer.valueOf(args[1]) + " As.");
				return true;
			}
		}
			
		return false;
	}
}

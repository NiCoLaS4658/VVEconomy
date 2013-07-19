package fr.vaevictis.vveconomy;

import java.util.Hashtable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class VVEconomy extends JavaPlugin
{
	
	public static Hashtable<String, Location> joueursInBank;
	
	@Override
	public void onEnable()
	{
		joueursInBank = new Hashtable<String, Location>();
		PluginManager pm = getServer().getPluginManager();		
		pm.registerEvents(new VVEconomyListener(this), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public static ItemStack renvoiIngot(ItemStack clickedBlock)
	{
		if (clickedBlock.getTypeId() == 233)
			return new ItemStack(Material.COAL);
		else if (clickedBlock.getType() == Material.IRON_BLOCK)
			return new ItemStack(Material.IRON_INGOT);
		else if (clickedBlock.getType() == Material.GOLD_BLOCK)
			return new ItemStack(Material.GOLD_INGOT);
		else
			return new ItemStack(0);
	}
		
	public void setLocationBanque(Location l)
	{
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("banqueWorld", l.getWorld().getName());
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("banqueX", l.getX());
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("banqueY", l.getY());
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("banqueZ", l.getZ());
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
	}
	
	public static Location getBanqueLocation()
	{
		int x = Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("banqueX");
		double xdouble = (double) x;
		int y = Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("banqueY");
		double ydouble = (double) y;
		int z = Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("banqueZ");
		double zdouble = (double) z;
		Location pos = new Location(Bukkit.getServer().getWorld(Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getString("banqueWorld")), xdouble, ydouble, zdouble);
		return pos;
	}
	
	public static int getArgentBanque()
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("argentBanque") == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argentBanque", 0);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
		return Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("argentBanque");
	}
	
	public static void ajouterArgentBanque(int somme)
	{
		int argentActuel = VVEconomy.getArgentBanque();
		int argentApres = argentActuel + somme;
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argentBanque", argentApres);
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
	}
	
	public static boolean soustraireArgentBanque(int somme)
	{
		int argentActuel = VVEconomy.getArgentBanque();
		int argentApres = argentActuel - somme;
		if (argentApres >= 0)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("argentBanque", argentApres);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
			return true;
		}
		else
		{
			return false;
		}
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
	
	public static int getStock(ItemStack item)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("stock." + item.getType().toString()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("stock." + item.getType().toString(), 0);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
		return Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("stock." + item.getType().toString());
	}
	
	public static boolean soustraireStock(int quantite, ItemStack item)
	{
			int stockActuel = VVEconomy.getStock(item);
			int stockApres = stockActuel - quantite;
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("stock." + item.getType().toString(), stockApres);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
			return true;
	}
	
	public void ajouterStock(int quantite, ItemStack item)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("stock." + item.getType().toString()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("stock." + item.getType().toString(), 0);
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
		}
		int stockActuel = VVEconomy.getStock(item);
		int stockApres = stockActuel + quantite;
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("stock." + item.getType().toString(), stockApres);
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
	}
	
	public void setCurrency(ItemStack item, int currency)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("currency." + item.getType().toString()) == null)
		{
			Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("currency." + item.getType().toString(), 0);
		}
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().set("currency." + item.getType().toString(), currency);
		Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").saveConfig();
	}
	
	//Donne la valeur en as associée à un ID
	public static int getCurrency(ItemStack item)
	{
		if (Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().get("currency." + item.getType().toString()) == null)
		{
			return -1;
		}
		return Bukkit.getServer().getPluginManager().getPlugin("VVEconomy").getConfig().getInt("currency." + item.getType().toString());
	}
	
	//Donne la valeur que devra payer le client pour acheter la quantite de blocs qu'il a demandé
	public static int getPrixAchat(ItemStack item)
	{
		int currency = VVEconomy.getCurrency(item);
		int stock = VVEconomy.getStock(item);
		int prix = ((-(3/172) * currency * stock) + ((4 * currency) + ((3/172) * currency))) * 4;
		if (currency == -1)
		{
			return -1;
		}
		else if (prix <= 1 && currency != -1)
		{
			return 1;
		}
		else if (prix >= 1 && currency != -1)
		{
			return prix;
		}
		else 
		{
			return -1;
		}
	}
	
	//Donne la valeur que devra payer la banque au client lui vendant des blocs
	public int getPrixVente(ItemStack item)
	{
		int currency = VVEconomy.getCurrency(item);
		int stock = VVEconomy.getStock(item);
		int prix = (-(3/172) * currency * stock) + ((4 * currency) + ((3/172) * currency));
		if (currency == -1)
		{
			return -1;
		}
		else if (prix <= 1 && currency != -1)
		{
			return 1;
		}
		else if (prix >= 1 && currency != -1)
		{
			return prix;
		}
		else 
		{
			return -1;
		}
	
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
		
		if (label.equalsIgnoreCase("argent") && p.hasPermission("vveconomy.basic"))
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
		else if (label.equalsIgnoreCase("payer") && args.length == 2 && p.hasPermission("vveconomy.basic"))
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
		else if (label.equalsIgnoreCase("mettreargent") && args.length == 2 && p.hasPermission("vveconomy.admin"))
		{
			Player p2 = Bukkit.getServer().getPlayer(args[0]);
			if(p2 != null && Integer.valueOf(args[1]) > 0)
			{
				setArgent(p2, Integer.valueOf(args[1]));
				p.sendMessage(ChatColor.GOLD + p2.getDisplayName() + " a désormais " + Integer.valueOf(args[1]) + " As.");
				return true;
			}
		}
		else if (label.equalsIgnoreCase("banque" ) && p.hasPermission("vveconomy.basic"))
		{
			if (args[0].equalsIgnoreCase("tp"))
					{
				joueursInBank.put(p.getName(), p.getLocation());
				p.teleport(VVEconomy.getBanqueLocation());
					}
			else if (args[0].equalsIgnoreCase("quitter"))
			{
				if (joueursInBank.containsKey(p.getName()))
				{
					p.teleport(joueursInBank.get(p.getName()));
					joueursInBank.remove(p.getName());
				}
				else
				{
					p.sendMessage(ChatColor.GOLD + "Vous n'êtes pas venu à la banque via la téléportation, nous ne pouvons pas vous téléporter");
				}
			}
			else if (args[0].equalsIgnoreCase("vendre") || args[0].equalsIgnoreCase("prixvente"))
			{
				if (p.getItemInHand().getType() == Material.STONE || p.getItemInHand().getType() == Material.DIRT || p.getItemInHand().getType() == Material.COBBLESTONE || p.getItemInHand().getType() == Material.SAND || p.getItemInHand().getType() == Material.SANDSTONE || p.getItemInHand().getType() == Material.OBSIDIAN || p.getItemInHand().getType() == Material.NETHER_BRICK || p.getItemInHand().getType() == Material.NETHERRACK || p.getItemInHand().getType() == Material.GLOWSTONE_DUST || p.getItemInHand().getType() == Material.SOUL_SAND || p.getItemInHand().getType() == Material.REDSTONE)
				{
					int prixVente = this.getPrixVente(p.getItemInHand());
					if (prixVente != -1 && args[0].equalsIgnoreCase("vendre"))
					{
						if (VVEconomy.soustraireArgentBanque(prixVente) == true)
						{
							if (p.getItemInHand().getAmount() >= 64)
							{
								this.ajouterStock(64, p.getItemInHand());
								p.setItemInHand(new ItemStack(0));
								int argentJoueurActuel = VVEconomy.getArgent(p);
								int argentJoueur = argentJoueurActuel + prixVente;
								VVEconomy.setArgent(p, argentJoueur);
							}
							else
							{
								p.sendMessage(ChatColor.GOLD + "Vous ne tenez pas assez d'items dans votre main");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "La banque ne dispose pas d'assez d'argent pour acheter vos biens");
						}
					}
					else if (prixVente != -1 && args[0].equalsIgnoreCase("prixvente"))
					{
						p.sendMessage(ChatColor.GOLD + "Le prix que vous obtiendrez pour 64 " + p.getItemInHand().getType().toString() + " est " + prixVente + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
				
				}
				else if (p.getItemInHand().getType() == Material.COAL || p.getItemInHand().getType() == Material.IRON_INGOT || p.getItemInHand().getType() == Material.GOLD_INGOT)
				{
					int prixVente = this.getPrixVente(p.getItemInHand());
					if (prixVente != -1 && args[0].equalsIgnoreCase("vendre"))
					{
						if (VVEconomy.soustraireArgentBanque(prixVente) == true)
						{
							if (p.getItemInHand().getAmount() >= 16)
							{
								this.ajouterStock(16, p.getItemInHand());
								int reste = p.getItemInHand().getAmount() - 16;
								if (reste != 0) 
								{
									p.setItemInHand(new ItemStack(p.getItemInHand().getType(), reste));
								}
								else
								{
									p.setItemInHand(new ItemStack(0));
								}
								int argentJoueurActuel = VVEconomy.getArgent(p);
								int argentJoueur = argentJoueurActuel + prixVente;
								VVEconomy.setArgent(p, argentJoueur);
							}
							else
							{
								p.sendMessage(ChatColor.GOLD + "Vous ne tenez pas assez d'items dans votre main");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "La banque ne dispose pas d'assez d'argent pour acheter vos biens");
						}
					}
					else if (prixVente != -1 && args[0].equalsIgnoreCase("prixvente"))
					{
						p.sendMessage(ChatColor.GOLD + "Le prix que vous obtiendrez pour 16 " + p.getItemInHand().getType().toString() + " est " + prixVente + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
					
				}
				else if (p.getItemInHand().getType() == Material.DIAMOND)
				{
					int prixVente = this.getPrixVente(p.getItemInHand());
					if (prixVente != -1 && args[0].equalsIgnoreCase("vendre"))
					{
						if (VVEconomy.soustraireArgentBanque(prixVente) == true)
						{
							if (p.getItemInHand().getAmount() >= 1)
							{
								this.ajouterStock(1, p.getItemInHand());
								int reste = p.getItemInHand().getAmount() - 1;
								if (reste != 0)
								{
									p.setItemInHand(new ItemStack(p.getItemInHand().getType(), reste));
								}
								else
								{
									p.setItemInHand(new ItemStack(0));
								}
								int argentJoueurActuel = VVEconomy.getArgent(p);
								int argentJoueur = argentJoueurActuel + prixVente;
								VVEconomy.setArgent(p, argentJoueur);
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "La banque ne dispose pas d'assez d'argent pour acheter vos biens");
						}
					}
					else if (prixVente != -1 && args[0].equalsIgnoreCase("prixvente"))
					{
						p.sendMessage(ChatColor.GOLD + "Le prix que vous obtiendrez pour 1 " + p.getItemInHand().getType().toString() + " est " + prixVente + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}					
					
				}
				else
				{
					p.sendMessage(ChatColor.GOLD + "Vous ne pouvez pas vendre cet objet à la banque");
				}
				
			}
			else
			{
				p.sendMessage(ChatColor.GOLD + "Les arguments disponibles pour la banque sont :\n - tp : pour vous rendre à la banque (achat)\n - vendre : pour vendre ce que vous avez dans la main\n - quitter : quitter la banque\n prixvente : pour obtenir le prix de ce que vous avez dans la main");
			}
			
			return true;
		
		}
		
		//ATTENTION UNIQUEMENT FAIT PAR DES GENS QUI CONNAISSENT LA COMMANDE : IL FAUT AVOIR UN ITEM COMPATIBLE EN MAIN ET SURTOUT NE PAS ENTRER AUTRE CHOSE QU'UN INT EN ARGUMENT
		else if (label.equalsIgnoreCase("mettrecurrency") && p.hasPermission("vveconomy.admin"))
		{
			if (args.length == 1)
			{
				this.setCurrency(p.getItemInHand(), Integer.parseInt(args[0]));
			}
			else
			{
				p.sendMessage(ChatColor.GOLD + "Nombre d'args inattendu");
			}
			return true;
		}
		else if (label.equalsIgnoreCase("setbanque") && p.hasPermission("vveconomy.admin"))
		{
			this.setLocationBanque(p.getLocation());
		}
		else if (label.equalsIgnoreCase("ajouterargentbanque") && p.hasPermission("vveconomy.admin"))
		{
			VVEconomy.ajouterArgentBanque(Integer.parseInt(args[0]));
		}
		return false;
	}
}

package fr.vaevictis.vveconomy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class VVEconomyListener implements Listener
{
	@SuppressWarnings("unused")
	private VVEconomy plugin;

	public VVEconomyListener(VVEconomy plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerHitBlock(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		ItemStack clickedBlock = new ItemStack(e.getClickedBlock().getTypeId());
		//vérifie que le joueur est dans la banque
		int playerx = p.getLocation().getBlockX();
		int playery = p.getLocation().getBlockY();
		int playerz = p.getLocation().getBlockZ();
		int banquex = VVEconomy.getBanqueLocation().getBlockX();
		int banquey = VVEconomy.getBanqueLocation().getBlockY();
		int banquez = VVEconomy.getBanqueLocation().getBlockZ();
		if (p.getLocation().getWorld() == VVEconomy.getBanqueLocation().getWorld() && playerx >= banquex - 20 && playerx <= banquex + 20 && playery >= banquey - 5 && playery <= banquey + 5 && playerz >= banquez - 20 && playerz <= banquez + 20)
		{
			if (e.getAction().equals(Action.LEFT_CLICK_BLOCK))
			{				
				if (clickedBlock.getType() == Material.STONE || clickedBlock.getType() == Material.DIRT || clickedBlock.getType() == Material.COBBLESTONE || clickedBlock.getType() == Material.SAND || clickedBlock.getType() == Material.SANDSTONE || clickedBlock.getType() == Material.OBSIDIAN || clickedBlock.getType() == Material.NETHER_BRICK || clickedBlock.getType() == Material.NETHERRACK || /*clickedBlock.getType() == Material.GLOWSTONE_DUST ||*/ clickedBlock.getType() == Material.SOUL_SAND) /*||clickedBlock.getType() == Material.REDSTONE)*/
				{
					int prixAchat = VVEconomy.getPrixAchat(new ItemStack(clickedBlock.getTypeId()));
					if (prixAchat != -1 && p.isSneaking())
					{
						if (VVEconomy.getArgent(p) >= prixAchat)
						{
							if (VVEconomy.getStock(new ItemStack(clickedBlock.getTypeId())) >= 64)
									{
										e.getPlayer().getInventory().addItem(new ItemStack(clickedBlock.getTypeId(), 64));
										int asActuel = VVEconomy.getArgent(p);
										int asApres = asActuel - prixAchat;
										VVEconomy.setArgent(p, asApres);
										VVEconomy.ajouterArgentBanque(prixAchat);
										VVEconomy.soustraireStock(64, clickedBlock);									
									}
							else
							{
								p.sendMessage(ChatColor.GOLD + "La Banque ne dispose pas des stocks suffisants");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "Vous n'avez pas assez d'argent (coût de la transaction : " + prixAchat + ")");
						}
						
						}
					else if (prixAchat != -1 && !p.isSneaking())
					{
						p.sendMessage(ChatColor.GOLD + "Le prix pour l'achat de 64 " + clickedBlock.getType().toString() + " est " + prixAchat + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
					
				}
				else if (clickedBlock.getTypeId() == 233  || clickedBlock.getType() == Material.IRON_BLOCK || clickedBlock.getType() == Material.GOLD_BLOCK)
				{
					
					int prixAchat = VVEconomy.getPrixAchat(VVEconomy.renvoiIngot(clickedBlock));
					if (prixAchat != -1 && p.isSneaking())
					{
						if (VVEconomy.getArgent(p) >= prixAchat)
						{
							if (VVEconomy.getStock(VVEconomy.renvoiIngot(clickedBlock)) >= 16)
									{
										e.getPlayer().getInventory().addItem(new ItemStack(VVEconomy.renvoiIngot(clickedBlock).getTypeId(), 16));
										int asActuel = VVEconomy.getArgent(p);
										int asApres = asActuel - prixAchat;
										VVEconomy.setArgent(p, asApres);
										VVEconomy.ajouterArgentBanque(prixAchat);
										VVEconomy.soustraireStock(16, VVEconomy.renvoiIngot(clickedBlock));									
									}
							else
							{
								p.sendMessage(ChatColor.GOLD + "La Banque ne dispose pas des stocks suffisants");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "Vous n'avez pas assez d'argent (coût de la transaction : " + prixAchat + ")");
						}
						
						}
					else if (prixAchat != -1 && !p.isSneaking())
					{
						p.sendMessage(ChatColor.GOLD + "Le prix pour l'achat de 16 " + VVEconomy.renvoiIngot(clickedBlock).getType().toString() + " est " + prixAchat + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
					
				}
				else if (clickedBlock.getType() == Material.DIAMOND_BLOCK)
				{
					int prixAchat = VVEconomy.getPrixAchat(new ItemStack(Material.DIAMOND));
					if (prixAchat != -1 && p.isSneaking())
					{
						if (VVEconomy.getArgent(p) >= prixAchat)
						{
							if (VVEconomy.getStock(new ItemStack(Material.DIAMOND)) >= 1)
									{
										e.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND.getId(), 1));
										int asActuel = VVEconomy.getArgent(p);
										int asApres = asActuel - prixAchat;
										VVEconomy.setArgent(p, asApres);
										VVEconomy.ajouterArgentBanque(prixAchat);
										VVEconomy.soustraireStock(1, new ItemStack(Material.DIAMOND));									
									}
							else
							{
								p.sendMessage(ChatColor.GOLD + "La Banque ne dispose pas des stocks suffisants");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "Vous n'avez pas assez d'argent (coût de la transaction : " + prixAchat + ")");
						}
						
						}
					else if (prixAchat != -1 && !p.isSneaking())
					{
						p.sendMessage(ChatColor.GOLD + "Le prix pour l'achat de 1 DIAMOND est " + prixAchat + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
				}
				else if (clickedBlock.getType() == Material.GLOWSTONE)
				{
					int prixAchat = VVEconomy.getPrixAchat(new ItemStack(Material.GLOWSTONE_DUST));
					if (prixAchat != -1 && p.isSneaking())
					{
						if (VVEconomy.getArgent(p) >= prixAchat)
						{
							if (VVEconomy.getStock(new ItemStack(clickedBlock.getTypeId())) >= 64)
									{
										e.getPlayer().getInventory().addItem(new ItemStack(clickedBlock.getTypeId(), 64));
										int asActuel = VVEconomy.getArgent(p);
										int asApres = asActuel - prixAchat;
										VVEconomy.setArgent(p, asApres);
										VVEconomy.ajouterArgentBanque(prixAchat);
										VVEconomy.soustraireStock(64, new ItemStack(Material.GLOWSTONE_DUST));									
									}
							else
							{
								p.sendMessage(ChatColor.GOLD + "La Banque ne dispose pas des stocks suffisants");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "Vous n'avez pas assez d'argent (coût de la transaction : " + prixAchat + ")");
						}
						
						}
					else if (prixAchat != -1 && !p.isSneaking())
					{
						p.sendMessage(ChatColor.GOLD + "Le prix pour l'achat de 64 GLOWSTONE_DUST est " + prixAchat + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
				}
				//CAS DE LA REDSTONE
				else if (clickedBlock.getTypeId() == 224)
				{
					int prixAchat = VVEconomy.getPrixAchat(new ItemStack(Material.REDSTONE));
					if (prixAchat != -1 && !p.isSneaking())
					{
						if (VVEconomy.getArgent(p) >= prixAchat)
						{
							if (VVEconomy.getStock(new ItemStack(clickedBlock.getTypeId())) >= 64)
									{
										e.getPlayer().getInventory().addItem(new ItemStack(clickedBlock.getTypeId(), 64));
										int asActuel = VVEconomy.getArgent(p);
										int asApres = asActuel - prixAchat;
										VVEconomy.setArgent(p, asApres);
										VVEconomy.ajouterArgentBanque(prixAchat);
										VVEconomy.soustraireStock(64, new ItemStack(Material.REDSTONE));									
									}
							else
							{
								p.sendMessage(ChatColor.GOLD + "La Banque ne dispose pas des stocks suffisants");
							}
						}
						else
						{
							p.sendMessage(ChatColor.GOLD + "Vous n'avez pas assez d'argent (coût de la transaction : " + prixAchat + ")");
						}
						
						}
					else if (prixAchat != -1 && !p.isSneaking())
					{
						p.sendMessage(ChatColor.GOLD + "Le prix pour l'achat de 64 REDSTONE est " + prixAchat + " As.");
					}
					else
					{
						p.sendMessage(ChatColor.GOLD + "L'objet que vous souhaitez acheter n'as pas de cours attribué, contactez un administateur");
					}
				}
				else
				{
					p.sendMessage(ChatColor.GOLD + "Ce n'est pas à vendre dans la banque !");
				}
			}
		}
	
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit (PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		if (VVEconomy.joueursInBank.containsKey(p.getName()))
		{
			p.teleport(VVEconomy.joueursInBank.get(p.getName()));
			VVEconomy.joueursInBank.remove(p.getName());
		}
		
	}
}

package fr.vaevictis.vveconomy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Joueur {
	
	Location l = new Location(Bukkit.getServer().getWorld("world"), 0, 0, 0);
	public Joueur(Player p)
	{
		this.nom = p.getName();
		this.anciennePosition = l;
		this.hasQuit = true;
	}
	
	public Location getAnciennePosition() {
		return anciennePosition;
	}

	public void setAnciennePosition(Location anciennePosition) {
		this.anciennePosition = anciennePosition;
	}

	public boolean isHasQuit() {
		return hasQuit;
	}

	public void setHasQuit(boolean hasQuit) {
		this.hasQuit = hasQuit;
	}

	public Location anciennePosition;
	public String nom;
	public boolean hasQuit;
	
}

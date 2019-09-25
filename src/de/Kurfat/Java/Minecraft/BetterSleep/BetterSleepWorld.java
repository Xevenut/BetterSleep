package de.Kurfat.Java.Minecraft.BetterSleep;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

public class BetterSleepWorld implements Listener{

	private World world;
	private List<UUID> sleep = new ArrayList<>();
	
	public BetterSleepWorld(World world) {
		this.world = world;
		for(Player player : world.getPlayers()) {
			UUID uid = player.getUniqueId();
			if(player.isSleeping()) sleep.add(uid);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(event.getPlayer().getWorld().getName().equals(world.getName()) == false) return;
		UUID uid = event.getPlayer().getUniqueId();
		if(sleep.contains(uid)) sleep.remove(uid);
	}
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) return;
		UUID uid = event.getPlayer().getUniqueId();
		if(event.getFrom().getWorld().getName().equals(world.getName())) {
			if(sleep.contains(uid)) sleep.remove(uid);
		}
	}
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent event) {
		if(event.getPlayer().getWorld().getName().equals(world.getName()) == false || event.getBedEnterResult() != BedEnterResult.OK) return;
		UUID uid = event.getPlayer().getUniqueId();
		sleep.add(uid);
		for(Player player : world.getPlayers()) player.sendMessage(skipMessage(player) + " " + sleep.size() + "/" + world.getPlayers().size());
		if(world.getPlayers().size() / 2.0 <= sleep.size()) {
			world.setTime(0);
			world.setThundering(false);
			world.setStorm(false);
			for(Player player : world.getPlayers()) {
				player.setSleepingIgnored(false);
				if(player.getHealth() < 20) player.setHealth(20);
				player.sendMessage(newDayMessage(player));
			}
		}
	}
	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent event) {
		if(event.getPlayer().getWorld().getName().equals(world.getName()) == false) return;
		UUID uid = event.getPlayer().getUniqueId();
		sleep.remove(uid);
	}
	
	private static String skipMessage(Player player) {
		switch (MinecraftLocale.getByString(player.getLocale())) {
		case BG_BG:
			return "Прескочи през нощта";
		case DA_DK:
			return "Spring over natten";
		case DE_DE:
			return "Nacht Überspringen";
		case EN_US:
			return "Skip over night";
		case ES_ES:
			return "Pasar la noche";
		case FR_FR:
			return "Passer la nuit";
		case JA_JP:
			return "スキップオーバーナイト";
		case KO_KR:
			return "건너 뛰기";
		case PL_PL:
			return "Przejdź przez noc";
		case RO_RO:
			return "Treci peste noapte";
		case RU_RU:
			return "Пропустить за ночь";
		case UK_UA:
			return "Пропустити ніч";
		default:
			return "Skip over night";
		}
	}
	private static String newDayMessage(Player player) {
		switch (MinecraftLocale.getByString(player.getLocale())) {
		case BG_BG:
			return "Започва нов ден";
		case DA_DK:
			return "En ny dag begynder";
		case DE_DE:
			return "Ein neuer Tag beginnt";
		case EN_US:
			return "A new day is starting";
		case ES_ES:
			return "Comienza un nuevo día";
		case FR_FR:
			return "Un nouveau jour commence";
		case JA_JP:
			return "新しい日が始まっています";
		case KO_KR:
			return "새로운 날이 시작됩니다";
		case PL_PL:
			return "Rozpoczyna się nowy dzień";
		case RO_RO:
			return "O nouă zi începe";
		case RU_RU:
			return "Новый день начинается";
		case UK_UA:
			return "Починається новий день";
		default:
			return "A new day is starting";
		}
	}

}

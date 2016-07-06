package com.gb.cornucopia;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Settings {
	private Configuration config;
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if (eventArgs.modID.equals(CornuCopia.MODID)) {
        //System.out.println("CornuCopia: detected change in config file, reloading...");
        	sync();
        }
    }	
	
    public Settings(final File config_dir){
		config_dir.mkdirs();
		File file = new File(config_dir, "settings.cfg");
	//System.out.println("CornuCopia: LOADING CONFIG FROM " + file.getAbsolutePath());
		config = new Configuration(file);
		sync();
		
		
	}
	
	public void sync() {
		wild_veggie_spread = config.getBoolean("wild_veggie_spread", "Wild Growth", false, "wild ground crops have a chance to spread to neighboring blocks");
		wild_veggie_spread_rate = config.getInt("wild_veggie_spread_rate", "Wild Growth", 128, 1, Integer.MAX_VALUE, "If veggie spread is enabled, they will have a 1 / N chance to do so each day");

		wild_veggie_spawn = config.getBoolean("wild_veggie_spawn", "Wild Growth", false, "veggies have a small chance to spawn in the wild");
		wild_veggie_spawn_chance = config.getInt("wild_veggie_spawn_rate", "Wild Growth", 128, 1, Integer.MAX_VALUE, "veggies spawn chance");

		wild_fruit_spawn = config.getBoolean("wild_fruit_spawn", "Wild Growth", false, "fruits have a small chance to spawn in the wild");
		wild_fruit_spawn_chance = config.getInt("wild_fruit_spawn_rate", "Wild Growth", 256, 1, Integer.MAX_VALUE, "fruit spawn chance 1/N");

		wild_bee_spawn = config.getBoolean("wild_bee_spawn", "Wild Growth", false, "bee hives have a small chance to spawn in the wild");
		wild_bee_spawn_chance = config.getInt("wild_bee_rate", "Wild Growth", 128, 1, Integer.MAX_VALUE, "bee spawn chance 1/N");

		wild_growth_cooldown = config.getInt("wild_growth_cooldown", "Wild Growth", 120000, 600, Integer.MAX_VALUE, "defaults is ~5 minecraft days. min 600 (~30s) to prevent abuse");

		wild_growth_mega_log = config.getBoolean("wild_growth_mega_log", "Wild Growth", false, "tons of extra client side logging. not recommended.");
		
		config.save();
	}

	public static boolean wild_fruit_spawn;
	public static int wild_fruit_spawn_chance;

	public static boolean wild_bee_spawn;
	public static int wild_bee_spawn_chance;

	public static boolean wild_veggie_spawn;
	public static int wild_veggie_spawn_chance;

	public static boolean wild_veggie_spread;
	public static int wild_veggie_spread_rate;
	
	public static int wild_growth_cooldown; 
	
	public static boolean wild_growth_mega_log;
	
}


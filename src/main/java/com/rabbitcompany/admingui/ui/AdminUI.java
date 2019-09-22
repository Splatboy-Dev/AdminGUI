package com.rabbitcompany.admingui.ui;

import com.rabbitcompany.admingui.AdminGUI;
import com.rabbitcompany.admingui.utils.*;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AdminUI {
    
    public static Inventory inv_main;
    public static String inventory_main_name;
    public static int inv_main_rows = 3 * 9;

    public static Inventory inv_player;
    public static String inventory_player_name;
    public static int inv_player_rows = 1 * 9;

    public static Inventory inv_world;
    public static String inventory_world_name;
    public static int inv_world_rows = 3 * 9;

    public static Inventory inv_players;
    public static String inventory_players_name;
    public static int inv_players_rows = 6 * 9;

    public static Inventory inv_players_settings;
    public static String inventory_players_settings_name;
    public static int inv_players_settings_rows = 3 * 9;

    public static Inventory inv_actions;
    public static String inventory_actions_name;
    public static int inv_actions_rows = 4 * 9;

    public static Inventory inv_kick;
    public static String inventory_kick_name;
    public static int inv_kick_rows = 3 * 9;

    public static Inventory inv_ban;
    public static String inventory_ban_name;
    public static int inv_ban_rows = 4 * 9;

    public static Inventory inv_potions;
    public static String inventory_potions_name;
    public static int inv_potions_rows = 4 * 9;

    public static Inventory inv_spawner;
    public static String inventory_spawner_name;
    public static int inv_spawner_rows = 6 * 9;

    public static Inventory inv_inventory;
    public static String inventory_inventory_name;
    public static int inv_inventory_rows = 6 * 9;

    public static String target_player;

    private static int ban_years = 0;
    private static int ban_months = 0;
    private static int ban_days = 1;
    private static int ban_hours = 0;
    private static int ban_minutes = 0;

    private static int page = 1;
    private static int pages = 1;

    public static int duration = 1;
    public static int level = 1;

    public static boolean maintenance_mode = false;

    private static AdminGUI adminGUI;

    public static void initialize(){

        inventory_main_name = Message.getMessage("inventory_main");
        inventory_world_name = Message.getMessage("inventory_world");
        inventory_players_name = Message.getMessage("inventory_players");

        inv_main = Bukkit.createInventory(null, inv_main_rows);
        inv_player = Bukkit.createInventory(null, inv_player_rows);
        inv_world = Bukkit.createInventory(null, inv_world_rows);
        inv_players = Bukkit.createInventory(null, inv_players_rows);
        inv_players_settings = Bukkit.createInventory(null, inv_players_settings_rows);
        inv_actions = Bukkit.createInventory(null, inv_actions_rows);
        inv_kick = Bukkit.createInventory(null, inv_kick_rows);
        inv_ban = Bukkit.createInventory(null, inv_ban_rows);
        inv_potions = Bukkit.createInventory(null, inv_potions_rows);
        inv_spawner = Bukkit.createInventory(null, inv_spawner_rows);
        inv_inventory = Bukkit.createInventory(null, inv_inventory_rows);
    }

    public static Inventory GUI_Main(Player p){

        Inventory toReturn = Bukkit.createInventory(null, inv_main_rows, inventory_main_name);

        Player random_player = Bukkit.getOnlinePlayers().stream().findAny().get();

        for(int i = 1; i < 27; i++){
                Item.create(inv_main, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        Item.createPlayerHead(inv_main, p.getName(), 1, 12, Message.getMessage("main_player").replace("{player}", p.getName()));
        Item.create(inv_main, "GRASS_BLOCK", 1, 14, Message.getMessage("main_world"));
        Item.createPlayerHead(inv_main,  random_player.getName(),1, 16, Message.getMessage("main_players"));
        if(maintenance_mode){
            Item.create(inv_main, "GLOWSTONE_DUST", 1, 19, Message.getMessage("main_maintenance_mode"));
        }else{
            Item.create(inv_main, "REDSTONE", 1, 19, Message.getMessage("main_maintenance_mode"));
        }
        Item.create(inv_main, "REDSTONE_BLOCK", 1, 27, Message.getMessage("main_quit"));

        toReturn.setContents(inv_main.getContents());

        return toReturn;
    }

    public static Inventory GUI_Player(Player p){

        inventory_player_name = Message.getMessage("inventory_player").replace("{player}", p.getName());

        Inventory toReturn = Bukkit.createInventory(null, inv_player_rows, inventory_player_name);

        for(int i = 1; i < 9; i++){
            Item.create(inv_player, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        if(p.hasPermission("admingui.heal")) {
            Item.create(inv_player, "GOLDEN_APPLE", 1, 1, Message.getMessage("player_heal"));
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 1,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.feed")) {
            Item.create(inv_player, "COOKED_BEEF", 1, 2, Message.getMessage("player_feed"));
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 2,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.gamemode")) {
            if (p.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                Item.create(inv_player, "DIRT", 1, 3, Message.getMessage("player_survival"));
            } else if (p.getPlayer().getGameMode() == GameMode.ADVENTURE) {
                Item.create(inv_player, "GRASS_BLOCK", 1, 3, Message.getMessage("player_adventure"));
            } else if (p.getPlayer().getGameMode() == GameMode.CREATIVE) {
                Item.create(inv_player, "BRICKS", 1, 3, Message.getMessage("player_creative"));
            } else if (p.getPlayer().getGameMode() == GameMode.SPECTATOR) {
                Item.create(inv_player, "SPLASH_POTION", 1, 3, Message.getMessage("player_spectator"));
            }
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 3,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.god")) {
            if (p.isInvulnerable()) {
                Item.create(inv_player, "RED_TERRACOTTA", 1, 4, Message.getMessage("player_god_disabled"));
            } else {
                Item.create(inv_player, "LIME_TERRACOTTA", 1, 4, Message.getMessage("player_god_enabled"));
            }
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 4,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.potions")) {
            Item.create(inv_player, "POTION", 1, 5, Message.getMessage("player_potions"));
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 5,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.spawner")) {
            Item.create(inv_player, "SPAWNER", 1, 6, Message.getMessage("player_spawner"));
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 13, 6,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.kill")) {
            Item.create(inv_player, "DIAMOND_SWORD", 1, 7, Message.getMessage("player_kill"));
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 7,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.burn")) {
            Item.create(inv_player, "FLINT_AND_STEEL", 1, 8, Message.getMessage("player_burn"));
        }else{
            Item.create(inv_player, "RED_STAINED_GLASS_PANE", 1, 8,  Message.getMessage("permission"));
        }

        Item.create(inv_player, "REDSTONE_BLOCK", 1, 9, Message.getMessage("player_back"));

        toReturn.setContents(inv_player.getContents());

        return toReturn;
    }

    public static Inventory GUI_World(Player p){

        Inventory toReturn = Bukkit.createInventory(null, inv_world_rows, inventory_world_name);

        for(int i = 1; i < 27; i++){
            Item.create(inv_world, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        if(p.hasPermission("admingui.time")) {
            if (p.getPlayer().getWorld().getTime() < 13000) {
                Item.create(inv_world, "GOLD_BLOCK", 1, 11, Message.getMessage("world_day"));
            } else {
                Item.create(inv_world, "COAL_BLOCK", 1, 11, Message.getMessage("world_night"));
            }
        }else{
            Item.create(inv_world, "RED_STAINED_GLASS_PANE", 1, 11,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.weather")) {
            if (p.getPlayer().getWorld().isThundering()) {
                Item.create(inv_world, "BLUE_CONCRETE", 1, 13, Message.getMessage("world_thunder"));
            } else if (p.getPlayer().getWorld().hasStorm()) {
                Item.create(inv_world, "CYAN_CONCRETE", 1, 13, Message.getMessage("world_rain"));
            } else {
                Item.create(inv_world, "LIGHT_BLUE_CONCRETE", 1, 13, Message.getMessage("world_clear"));
            }
        }else{
            Item.create(inv_world, "RED_STAINED_GLASS_PANE", 1, 13,  Message.getMessage("permission"));
        }

        Item.create(inv_world, "REDSTONE_BLOCK", 1, 27, Message.getMessage("world_back"));

        toReturn.setContents(inv_world.getContents());

        return toReturn;
    }

    public static Inventory GUI_Players(Player p){

        ArrayList<String> pl = new ArrayList<String>();

        Inventory toReturn = Bukkit.createInventory(null, inv_players_rows, inventory_players_name);

        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
            pl.add(all.getName());
        }

        pl.remove(p.getName());

        Collections.sort(pl);

        int online = pl.size();

        pages = (int) Math.ceil((float)online / 45);

        for (int i = 46; i <= 53; i++){
            Item.create(inv_players, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        int player_slot = (page-1) * 45;

        for (int i = 0; i < 45; i++){
            if(player_slot < online){
                Item.createPlayerHead(inv_players, pl.get(player_slot),1, i+1, Message.getMessage("players_color") + pl.get(player_slot), Message.getMessage("players_lore"));
                player_slot++;
            }else{
                Item.create(inv_players, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i+1, "Empty");
            }
        }

        if(page > 1){
            Item.create(inv_players, "PAPER", 1, 49, Message.getMessage("players_previous"));
        }

        if(pages > 1){
            Item.create(inv_players, "BOOK", page, 50, Message.getMessage("players_page") + " " + page);
        }

        if(pages > page){
            Item.create(inv_players, "PAPER", 1, 51, Message.getMessage("players_next"));
        }

        Item.create(inv_players, "REDSTONE_BLOCK", 1, 54, Message.getMessage("players_back"));

        toReturn.setContents(inv_players.getContents());

        return toReturn;
    }

    public static Inventory GUI_Players_Settings(Player p, Player target_player){

        inventory_players_settings_name = Message.getMessage("players_color") + target_player.getName();

        Inventory toReturn = Bukkit.createInventory(null, inv_players_settings_rows, inventory_players_settings_name);

        for(int i = 1; i < 27; i++){
                Item.create(inv_players_settings, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        if(p.hasPermission("admingui.info")) {
            Item.createPlayerHead(inv_players_settings, target_player.getName(), 1, 5, Message.getMessage("players_settings_info").replace("{player}", target_player.getName()), Message.chat("&eHeal: " + Math.round(target_player.getHealth())), Message.chat("&7Feed: " + Math.round(target_player.getFoodLevel())), Message.chat("&aGamemode: " + target_player.getGameMode().toString()), Message.chat("&5IP: " + target_player.getAddress()));
        }else{
            Item.createPlayerHead(inv_players_settings, target_player.getName(), 1, 5, Message.getMessage("players_settings_info").replace("{player}", target_player.getName()));
        }

        Item.create(inv_players_settings, "DIAMOND_SWORD", 1, 11, Message.getMessage("players_settings_actions"));

        if(p.hasPermission("admingui.spawner.other")) {
            Item.create(inv_players_settings, "SPAWNER", 1, 13, Message.getMessage("players_settings_spawner"));
        }else{
            Item.create(inv_players_settings, "RED_STAINED_GLASS_PANE", 13, 25,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.kick.other")) {
            Item.create(inv_players_settings, "BLACK_CONCRETE", 1, 15, Message.getMessage("players_settings_kick_player"));
        }else{
            Item.create(inv_players_settings, "RED_STAINED_GLASS_PANE", 1, 15,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.ban")) {
            Item.create(inv_players_settings, "BEDROCK", 1, 17, Message.getMessage("players_settings_ban_player"));
        }else{
            Item.create(inv_players_settings, "RED_STAINED_GLASS_PANE", 1, 17,  Message.getMessage("permission"));
        }

        Item.create(inv_players_settings, "REDSTONE_BLOCK", 1, 27, Message.getMessage("players_settings_back"));

        toReturn.setContents(inv_players_settings.getContents());

        return toReturn;
    }

    public static Inventory GUI_Actions(Player p, String target){

        inventory_actions_name = Message.getMessage("inventory_actions").replace("{player}", target);
        target_player = target;

        Player target_player = Bukkit.getServer().getPlayer(target);

        Inventory toReturn = Bukkit.createInventory(null, inv_actions_rows, inventory_actions_name);

        for(int i = 1; i < 36; i++){
            Item.create(inv_actions, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        if(p.hasPermission("admingui.info")) {
            Item.createPlayerHead(inv_actions, target_player.getName(), 1, 5, Message.getMessage("actions_info").replace("{player}", target_player.getName()), Message.chat("&eHeal: " + Math.round(target_player.getHealth())), Message.chat("&7Feed: " + Math.round(target_player.getFoodLevel())), Message.chat("&aGamemode: " + target_player.getGameMode().toString()), Message.chat("&5IP: " + target_player.getAddress()));
        }else{
            Item.createPlayerHead(inv_actions, target_player.getName(), 1, 5, Message.getMessage("actions_info").replace("{player}", target_player.getName()));
        }

        if(p.hasPermission("admingui.heal.other")) {
            Item.create(inv_actions, "GOLDEN_APPLE", 1, 11, Message.getMessage("actions_heal"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 11,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.feed.other")) {
            Item.create(inv_actions, "COOKED_BEEF", 1, 13, Message.getMessage("actions_feed"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 13,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.gamemode.other")) {
            if (target_player.getGameMode() == GameMode.SURVIVAL) {
                Item.create(inv_actions, "DIRT", 1, 15, Message.getMessage("actions_survival"));
            } else if (target_player.getGameMode() == GameMode.ADVENTURE) {
                Item.create(inv_actions, "GRASS_BLOCK", 1, 15, Message.getMessage("actions_adventure"));
            } else if (target_player.getGameMode() == GameMode.CREATIVE) {
                Item.create(inv_actions, "BRICKS", 1, 15, Message.getMessage("actions_creative"));
            } else if (target_player.getGameMode() == GameMode.SPECTATOR) {
                Item.create(inv_actions, "SPLASH_POTION", 1, 15, Message.getMessage("actions_spectator"));
            }
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 15,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.god.other")) {
            if (target_player.isInvulnerable()) {
                Item.create(inv_actions, "RED_TERRACOTTA", 1, 17, Message.getMessage("actions_god_disabled"));
            } else {
                Item.create(inv_actions, "LIME_TERRACOTTA", 1, 17, Message.getMessage("actions_god_enabled"));
            }
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 17,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.teleport")) {
            Item.create(inv_actions, "ENDER_PEARL", 1, 19, Message.getMessage("actions_teleport_to_player"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 19,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.potions.other")) {
            Item.create(inv_actions, "POTION", 1, 21, Message.getMessage("actions_potions"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 21,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.kill.other")) {
            Item.create(inv_actions, "DIAMOND_SWORD", 1, 23, Message.getMessage("actions_kill_player"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 23,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.burn.other")) {
            Item.create(inv_actions, "FLINT_AND_STEEL", 1, 25, Message.getMessage("actions_burn_player"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 25,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.teleport.other")) {
            Item.create(inv_actions, "END_CRYSTAL", 1, 27, Message.getMessage("actions_teleport_player_to_you"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 27,  Message.getMessage("permission"));
        }

        if(p.hasPermission("admingui.inventory")) {
            Item.create(inv_actions, "BOOK", 1, 29, Message.getMessage("actions_inventory"));
        }else{
            Item.create(inv_actions, "RED_STAINED_GLASS_PANE", 1, 29,  Message.getMessage("permission"));
        }

        Item.create(inv_actions, "REDSTONE_BLOCK", 1, 36, Message.getMessage("actions_back"));

        toReturn.setContents(inv_actions.getContents());

        return toReturn;
    }

    public static Inventory GUI_Kick(Player p, String target){

        inventory_kick_name = Message.getMessage("inventory_kick").replace("{player}", target);
        target_player = target;

        Inventory toReturn = Bukkit.createInventory(null, inv_kick_rows, inventory_kick_name);

        for (int i = 1; i < 27; i++){
                Item.create(inv_kick, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        Item.create(inv_kick, "WHITE_TERRACOTTA", 1, 10, Message.getMessage("kick_hacking"));
        Item.create(inv_kick, "ORANGE_TERRACOTTA", 1, 12, Message.getMessage("kick_griefing"));
        Item.create(inv_kick, "MAGENTA_TERRACOTTA", 1, 14, Message.getMessage("kick_spamming"));
        Item.create(inv_kick, "LIGHT_BLUE_TERRACOTTA", 1, 16, Message.getMessage("kick_advertising"));
        Item.create(inv_kick, "YELLOW_TERRACOTTA", 1, 18, Message.getMessage("kick_swearing"));

        Item.create(inv_kick, "REDSTONE_BLOCK", 1, 27, Message.getMessage("kick_back"));

        toReturn.setContents(inv_kick.getContents());

        return toReturn;
    }

    public static Inventory GUI_Ban(Player p, String target){

        inventory_ban_name = Message.getMessage("inventory_ban").replace("{player}", target);
        target_player = target;

        Inventory toReturn = Bukkit.createInventory(null, inv_ban_rows, inventory_ban_name);

        for (int i = 1; i < 36; i++){
            Item.create(inv_ban, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        if(ban_years == 0){
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 12, Message.getMessage("ban_years"));
        }else{
            Item.create(inv_ban, "CLOCK", ban_years, 12, Message.getMessage("ban_years"));
        }

        if(ban_months == 0){
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 13, Message.getMessage("ban_months"));
        }else{
            Item.create(inv_ban, "CLOCK", ban_months, 13, Message.getMessage("ban_months"));
        }

        if(ban_days == 0){
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 14, Message.getMessage("ban_days"));
        }else{
            Item.create(inv_ban, "CLOCK", ban_days, 14, Message.getMessage("ban_days"));
        }

        if(ban_hours == 0){
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 15, Message.getMessage("ban_hours"));
        }else{
            Item.create(inv_ban, "CLOCK", ban_hours, 15, Message.getMessage("ban_hours"));
        }

        if(ban_minutes == 0){
            Item.create(inv_ban, "RED_STAINED_GLASS_PANE", 1, 16, Message.getMessage("ban_minutes"));
        }else{
            Item.create(inv_ban, "CLOCK", ban_minutes, 16, Message.getMessage("ban_minutes"));
        }

        Item.create(inv_ban, "WHITE_TERRACOTTA", 1, 30, Message.getMessage("ban_hacking"));
        Item.create(inv_ban, "ORANGE_TERRACOTTA", 1, 31, Message.getMessage("ban_griefing"));
        Item.create(inv_ban, "MAGENTA_TERRACOTTA", 1, 32, Message.getMessage("ban_spamming"));
        Item.create(inv_ban, "LIGHT_BLUE_TERRACOTTA", 1, 33, Message.getMessage("ban_advertising"));
        Item.create(inv_ban, "YELLOW_TERRACOTTA", 1, 34, Message.getMessage("ban_swearing"));

        Item.create(inv_ban, "REDSTONE_BLOCK", 1, 36, Message.getMessage("ban_back"));

        toReturn.setContents(inv_ban.getContents());

        return toReturn;
    }

    public static Inventory GUI_Potions(Player p, String target){

        inventory_potions_name = Message.getMessage("inventory_potions").replace("{player}", target);
        target_player = target;

        Inventory toReturn = Bukkit.createInventory(null, inv_potions_rows, inventory_potions_name);

        for (int i = 1; i < 36; i++){
            Item.create(inv_potions, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
            Item.create(inv_potions, "POTION", 1, 1, Message.getMessage("potions_night_vision"));
            Item.create(inv_potions, "POTION", 1, 2, Message.getMessage("potions_invisibility"));
            Item.create(inv_potions, "POTION", 1, 3, Message.getMessage("potions_jump_boost"));
            Item.create(inv_potions, "POTION", 1, 4, Message.getMessage("potions_fire_resistance"));
            Item.create(inv_potions, "POTION", 1, 5, Message.getMessage("potions_speed"));
            Item.create(inv_potions, "POTION", 1, 6, Message.getMessage("potions_slowness"));
            Item.create(inv_potions, "POTION", 1, 7, Message.getMessage("potions_water_breathing"));
            Item.create(inv_potions, "POTION", 1, 8, Message.getMessage("potions_instant_health"));
            Item.create(inv_potions, "POTION", 1, 9, Message.getMessage("potions_instant_damage"));
            Item.create(inv_potions, "POTION", 1, 10, Message.getMessage("potions_poison"));
            Item.create(inv_potions, "POTION", 1, 11, Message.getMessage("potions_regeneration"));
            Item.create(inv_potions, "POTION", 1, 12, Message.getMessage("potions_strength"));
            Item.create(inv_potions, "POTION", 1, 13, Message.getMessage("potions_weakness"));
            Item.create(inv_potions, "POTION", 1, 14, Message.getMessage("potions_luck"));
            Item.create(inv_potions, "POTION", 1, 15, Message.getMessage("potions_slow_falling"));
        }else if(Bukkit.getVersion().contains("1.12")){
            Item.create(inv_potions, "POTION", 1, 1, Message.getMessage("potions_night_vision"));
            Item.create(inv_potions, "POTION", 1, 2, Message.getMessage("potions_invisibility"));
            Item.create(inv_potions, "POTION", 1, 3, Message.getMessage("potions_jump_boost"));
            Item.create(inv_potions, "POTION", 1, 4, Message.getMessage("potions_fire_resistance"));
            Item.create(inv_potions, "POTION", 1, 5, Message.getMessage("potions_speed"));
            Item.create(inv_potions, "POTION", 1, 6, Message.getMessage("potions_slowness"));
            Item.create(inv_potions, "POTION", 1, 7, Message.getMessage("potions_water_breathing"));
            Item.create(inv_potions, "POTION", 1, 8, Message.getMessage("potions_instant_health"));
            Item.create(inv_potions, "POTION", 1, 9, Message.getMessage("potions_instant_damage"));
            Item.create(inv_potions, "POTION", 1, 10, Message.getMessage("potions_poison"));
            Item.create(inv_potions, "POTION", 1, 11, Message.getMessage("potions_regeneration"));
            Item.create(inv_potions, "POTION", 1, 12, Message.getMessage("potions_strength"));
            Item.create(inv_potions, "POTION", 1, 13, Message.getMessage("potions_weakness"));
            Item.create(inv_potions, "POTION", 1, 14, Message.getMessage("potions_luck"));
        }

        Item.create(inv_potions, "CLOCK", duration, 31, Message.getMessage("potions_time"));
        Item.create(inv_potions, "RED_STAINED_GLASS_PANE", 1, 32, Message.getMessage("potions_remove_all"));
        Item.create(inv_potions, "BEACON", level, 33, Message.getMessage("potions_level"));

        Item.create(inv_potions, "REDSTONE_BLOCK", 1, 36, Message.getMessage("potions_back"));

        toReturn.setContents(inv_potions.getContents());

        return toReturn;
    }

    public static Inventory GUI_Spawner(Player p, String target){

        inventory_spawner_name = Message.getMessage("inventory_spawner").replace("{player}", target);
        target_player = target;

        Inventory toReturn = Bukkit.createInventory(null, inv_spawner_rows, inventory_spawner_name);

        if (Bukkit.getVersion().contains("1.14")) {
            Item.create(inv_spawner, "BAT_SPAWN_EGG", 1, 1, Message.getMessage("spawner_bat"));
            Item.create(inv_spawner, "BLAZE_SPAWN_EGG", 1, 2, Message.getMessage("spawner_blaze"));
            Item.create(inv_spawner, "CAT_SPAWN_EGG", 1, 3, Message.getMessage("spawner_cat"));
            Item.create(inv_spawner, "CAVE_SPIDER_SPAWN_EGG", 1, 4, Message.getMessage("spawner_cave_spider"));
            Item.create(inv_spawner, "CHICKEN_SPAWN_EGG", 1, 5, Message.getMessage("spawner_chicken"));
            Item.create(inv_spawner, "COD_SPAWN_EGG", 1, 6, Message.getMessage("spawner_cod"));
            Item.create(inv_spawner, "COW_SPAWN_EGG", 1, 7, Message.getMessage("spawner_cow"));
            Item.create(inv_spawner, "CREEPER_SPAWN_EGG", 1, 8, Message.getMessage("spawner_creeper"));
            Item.create(inv_spawner, "DOLPHIN_SPAWN_EGG", 1, 9, Message.getMessage("spawner_dolphin"));
            Item.create(inv_spawner, "DONKEY_SPAWN_EGG", 1, 10, Message.getMessage("spawner_donkey"));
            Item.create(inv_spawner, "DROWNED_SPAWN_EGG", 1, 11, Message.getMessage("spawner_drowned"));
            Item.create(inv_spawner, "ELDER_GUARDIAN_SPAWN_EGG", 1, 12, Message.getMessage("spawner_elder_guardian"));
            Item.create(inv_spawner, "ENDERMAN_SPAWN_EGG", 1, 13, Message.getMessage("spawner_enderman"));
            Item.create(inv_spawner, "ENDERMITE_SPAWN_EGG", 1, 14, Message.getMessage("spawner_endermite"));
            Item.create(inv_spawner, "EVOKER_SPAWN_EGG", 1, 15, Message.getMessage("spawner_evoker"));
            Item.create(inv_spawner, "FOX_SPAWN_EGG", 1, 16, Message.getMessage("spawner_fox"));
            Item.create(inv_spawner, "GHAST_SPAWN_EGG", 1, 17, Message.getMessage("spawner_ghast"));
            Item.create(inv_spawner, "GUARDIAN_SPAWN_EGG", 1, 18, Message.getMessage("spawner_guardian"));
            Item.create(inv_spawner, "HORSE_SPAWN_EGG", 1, 19, Message.getMessage("spawner_horse"));
            Item.create(inv_spawner, "HUSK_SPAWN_EGG", 1, 20, Message.getMessage("spawner_husk"));
            Item.create(inv_spawner, "LLAMA_SPAWN_EGG", 1, 21, Message.getMessage("spawner_llama"));
            Item.create(inv_spawner, "MAGMA_CUBE_SPAWN_EGG", 1, 22, Message.getMessage("spawner_magma_cube"));
            Item.create(inv_spawner, "MOOSHROOM_SPAWN_EGG", 1, 23, Message.getMessage("spawner_mooshroom"));
            Item.create(inv_spawner, "MULE_SPAWN_EGG", 1, 24, Message.getMessage("spawner_mule"));
            Item.create(inv_spawner, "OCELOT_SPAWN_EGG", 1, 25, Message.getMessage("spawner_ocelot"));
            Item.create(inv_spawner, "PANDA_SPAWN_EGG", 1, 26, Message.getMessage("spawner_panda"));
            Item.create(inv_spawner, "PARROT_SPAWN_EGG", 1, 27, Message.getMessage("spawner_parrot"));
            Item.create(inv_spawner, "PHANTOM_SPAWN_EGG", 1, 28, Message.getMessage("spawner_phantom"));
            Item.create(inv_spawner, "PIG_SPAWN_EGG", 1, 29, Message.getMessage("spawner_pig"));
            Item.create(inv_spawner, "PILLAGER_SPAWN_EGG", 1, 30, Message.getMessage("spawner_pillager"));
            Item.create(inv_spawner, "POLAR_BEAR_SPAWN_EGG", 1, 31, Message.getMessage("spawner_polar_bear"));
            Item.create(inv_spawner, "PUFFERFISH_SPAWN_EGG", 1, 32, Message.getMessage("spawner_pufferfish"));
            Item.create(inv_spawner, "RABBIT_SPAWN_EGG", 1, 33, Message.getMessage("spawner_rabbit"));
            Item.create(inv_spawner, "RAVAGER_SPAWN_EGG", 1, 34, Message.getMessage("spawner_ravager"));
            Item.create(inv_spawner, "SALMON_SPAWN_EGG", 1, 35, Message.getMessage("spawner_salmon"));
            Item.create(inv_spawner, "SHEEP_SPAWN_EGG", 1, 36, Message.getMessage("spawner_sheep"));
            Item.create(inv_spawner, "SHULKER_SPAWN_EGG", 1, 37, Message.getMessage("spawner_shulker"));
            Item.create(inv_spawner, "SILVERFISH_SPAWN_EGG", 1, 38, Message.getMessage("spawner_silverfish"));
            Item.create(inv_spawner, "SKELETON_SPAWN_EGG", 1, 39, Message.getMessage("spawner_skeleton"));
            Item.create(inv_spawner, "SKELETON_HORSE_SPAWN_EGG", 1, 40, Message.getMessage("spawner_skeleton_horse"));
            Item.create(inv_spawner, "SLIME_SPAWN_EGG", 1, 41, Message.getMessage("spawner_slime"));
            Item.create(inv_spawner, "SPIDER_SPAWN_EGG", 1, 42, Message.getMessage("spawner_spider"));
            Item.create(inv_spawner, "SQUID_SPAWN_EGG", 1, 43, Message.getMessage("spawner_squid"));
            Item.create(inv_spawner, "STRAY_SPAWN_EGG", 1, 44, Message.getMessage("spawner_stray"));
            Item.create(inv_spawner, "TROPICAL_FISH_SPAWN_EGG", 1, 45, Message.getMessage("spawner_tropical_fish"));
            Item.create(inv_spawner, "TURTLE_SPAWN_EGG", 1, 46, Message.getMessage("spawner_turtle"));
            Item.create(inv_spawner, "VEX_SPAWN_EGG", 1, 47, Message.getMessage("spawner_vex"));
            Item.create(inv_spawner, "VILLAGER_SPAWN_EGG", 1, 48, Message.getMessage("spawner_villager"));
            Item.create(inv_spawner, "VINDICATOR_SPAWN_EGG", 1, 49, Message.getMessage("spawner_vindicator"));
            Item.create(inv_spawner, "WITCH_SPAWN_EGG", 1, 50, Message.getMessage("spawner_witch"));
            Item.create(inv_spawner, "WOLF_SPAWN_EGG", 1, 51, Message.getMessage("spawner_wolf"));
            Item.create(inv_spawner, "ZOMBIE_SPAWN_EGG", 1, 52, Message.getMessage("spawner_zombie"));
            Item.create(inv_spawner, "ZOMBIE_PIGMAN_SPAWN_EGG", 1, 53, Message.getMessage("spawner_zombie_pigman"));
        }else if(Bukkit.getVersion().contains("1.13")){
            Item.create(inv_spawner, "BAT_SPAWN_EGG", 1, 1, Message.getMessage("spawner_bat"));
            Item.create(inv_spawner, "BLAZE_SPAWN_EGG", 1, 2, Message.getMessage("spawner_blaze"));
            Item.create(inv_spawner, "CAVE_SPIDER_SPAWN_EGG", 1, 3, Message.getMessage("spawner_cave_spider"));
            Item.create(inv_spawner, "CHICKEN_SPAWN_EGG", 1, 4, Message.getMessage("spawner_chicken"));
            Item.create(inv_spawner, "COD_SPAWN_EGG", 1, 5, Message.getMessage("spawner_cod"));
            Item.create(inv_spawner, "COW_SPAWN_EGG", 1, 6, Message.getMessage("spawner_cow"));
            Item.create(inv_spawner, "CREEPER_SPAWN_EGG", 1, 7, Message.getMessage("spawner_creeper"));
            Item.create(inv_spawner, "DOLPHIN_SPAWN_EGG", 1, 8, Message.getMessage("spawner_dolphin"));
            Item.create(inv_spawner, "DONKEY_SPAWN_EGG", 1, 9, Message.getMessage("spawner_donkey"));
            Item.create(inv_spawner, "DROWNED_SPAWN_EGG", 1, 10, Message.getMessage("spawner_drowned"));
            Item.create(inv_spawner, "ELDER_GUARDIAN_SPAWN_EGG", 1, 11, Message.getMessage("spawner_elder_guardian"));
            Item.create(inv_spawner, "ENDERMAN_SPAWN_EGG", 1, 12, Message.getMessage("spawner_enderman"));
            Item.create(inv_spawner, "ENDERMITE_SPAWN_EGG", 1, 13, Message.getMessage("spawner_endermite"));
            Item.create(inv_spawner, "EVOKER_SPAWN_EGG", 1, 14, Message.getMessage("spawner_evoker"));
            Item.create(inv_spawner, "GHAST_SPAWN_EGG", 1, 15, Message.getMessage("spawner_ghast"));
            Item.create(inv_spawner, "GUARDIAN_SPAWN_EGG", 1, 16, Message.getMessage("spawner_guardian"));
            Item.create(inv_spawner, "HORSE_SPAWN_EGG", 1, 17, Message.getMessage("spawner_horse"));
            Item.create(inv_spawner, "HUSK_SPAWN_EGG", 1, 18, Message.getMessage("spawner_husk"));
            Item.create(inv_spawner, "LLAMA_SPAWN_EGG", 1, 19, Message.getMessage("spawner_llama"));
            Item.create(inv_spawner, "MAGMA_CUBE_SPAWN_EGG", 1, 20, Message.getMessage("spawner_magma_cube"));
            Item.create(inv_spawner, "MOOSHROOM_SPAWN_EGG", 1, 21, Message.getMessage("spawner_mooshroom"));
            Item.create(inv_spawner, "MULE_SPAWN_EGG", 1, 22, Message.getMessage("spawner_mule"));
            Item.create(inv_spawner, "OCELOT_SPAWN_EGG", 1, 23, Message.getMessage("spawner_ocelot"));
            Item.create(inv_spawner, "PARROT_SPAWN_EGG", 1, 24, Message.getMessage("spawner_parrot"));
            Item.create(inv_spawner, "PHANTOM_SPAWN_EGG", 1, 25, Message.getMessage("spawner_phantom"));
            Item.create(inv_spawner, "PIG_SPAWN_EGG", 1, 26, Message.getMessage("spawner_pig"));
            Item.create(inv_spawner, "POLAR_BEAR_SPAWN_EGG", 1, 27, Message.getMessage("spawner_polar_bear"));
            Item.create(inv_spawner, "PUFFERFISH_SPAWN_EGG", 1, 28, Message.getMessage("spawner_pufferfish"));
            Item.create(inv_spawner, "RABBIT_SPAWN_EGG", 1, 29, Message.getMessage("spawner_rabbit"));
            Item.create(inv_spawner, "SALMON_SPAWN_EGG", 1, 30, Message.getMessage("spawner_salmon"));
            Item.create(inv_spawner, "SHEEP_SPAWN_EGG", 1, 31, Message.getMessage("spawner_sheep"));
            Item.create(inv_spawner, "SHULKER_SPAWN_EGG", 1, 32, Message.getMessage("spawner_shulker"));
            Item.create(inv_spawner, "SILVERFISH_SPAWN_EGG", 1, 33, Message.getMessage("spawner_silverfish"));
            Item.create(inv_spawner, "SKELETON_SPAWN_EGG", 1, 34, Message.getMessage("spawner_skeleton"));
            Item.create(inv_spawner, "SKELETON_HORSE_SPAWN_EGG", 1, 35, Message.getMessage("spawner_skeleton_horse"));
            Item.create(inv_spawner, "SLIME_SPAWN_EGG", 1, 36, Message.getMessage("spawner_slime"));
            Item.create(inv_spawner, "SPIDER_SPAWN_EGG", 1, 37, Message.getMessage("spawner_spider"));
            Item.create(inv_spawner, "SQUID_SPAWN_EGG", 1, 38, Message.getMessage("spawner_squid"));
            Item.create(inv_spawner, "STRAY_SPAWN_EGG", 1, 39, Message.getMessage("spawner_stray"));
            Item.create(inv_spawner, "TROPICAL_FISH_SPAWN_EGG", 1, 40, Message.getMessage("spawner_tropical_fish"));
            Item.create(inv_spawner, "TURTLE_SPAWN_EGG", 1, 41, Message.getMessage("spawner_turtle"));
            Item.create(inv_spawner, "VEX_SPAWN_EGG", 1, 42, Message.getMessage("spawner_vex"));
            Item.create(inv_spawner, "VILLAGER_SPAWN_EGG", 1, 43, Message.getMessage("spawner_villager"));
            Item.create(inv_spawner, "VINDICATOR_SPAWN_EGG", 1, 44, Message.getMessage("spawner_vindicator"));
            Item.create(inv_spawner, "WITCH_SPAWN_EGG", 1, 45, Message.getMessage("spawner_witch"));
            Item.create(inv_spawner, "WOLF_SPAWN_EGG", 1, 46, Message.getMessage("spawner_wolf"));
            Item.create(inv_spawner, "ZOMBIE_SPAWN_EGG", 1, 47, Message.getMessage("spawner_zombie"));
            Item.create(inv_spawner, "ZOMBIE_PIGMAN_SPAWN_EGG", 1, 48, Message.getMessage("spawner_zombie_pigman"));
        }else if(Bukkit.getVersion().contains("1.12")){
            Item.create(inv_spawner, "BAT_SPAWN_EGG", 1, 1, Message.getMessage("spawner_bat"));
            Item.create(inv_spawner, "BLAZE_SPAWN_EGG", 1, 2, Message.getMessage("spawner_blaze"));
            Item.create(inv_spawner, "CAVE_SPIDER_SPAWN_EGG", 1, 3, Message.getMessage("spawner_cave_spider"));
            Item.create(inv_spawner, "CHICKEN_SPAWN_EGG", 1, 4, Message.getMessage("spawner_chicken"));
            Item.create(inv_spawner, "COW_SPAWN_EGG", 1, 5, Message.getMessage("spawner_cow"));
            Item.create(inv_spawner, "CREEPER_SPAWN_EGG", 1, 6, Message.getMessage("spawner_creeper"));
            Item.create(inv_spawner, "DONKEY_SPAWN_EGG", 1, 7, Message.getMessage("spawner_donkey"));
            Item.create(inv_spawner, "ELDER_GUARDIAN_SPAWN_EGG", 1, 8, Message.getMessage("spawner_elder_guardian"));
            Item.create(inv_spawner, "ENDERMAN_SPAWN_EGG", 1, 9, Message.getMessage("spawner_enderman"));
            Item.create(inv_spawner, "ENDERMITE_SPAWN_EGG", 1, 10, Message.getMessage("spawner_endermite"));
            Item.create(inv_spawner, "EVOKER_SPAWN_EGG", 1, 11, Message.getMessage("spawner_evoker"));
            Item.create(inv_spawner, "GHAST_SPAWN_EGG", 1, 12, Message.getMessage("spawner_ghast"));
            Item.create(inv_spawner, "GUARDIAN_SPAWN_EGG", 1, 13, Message.getMessage("spawner_guardian"));
            Item.create(inv_spawner, "HORSE_SPAWN_EGG", 1, 14, Message.getMessage("spawner_horse"));
            Item.create(inv_spawner, "HUSK_SPAWN_EGG", 1, 15, Message.getMessage("spawner_husk"));
            Item.create(inv_spawner, "LLAMA_SPAWN_EGG", 1, 16, Message.getMessage("spawner_llama"));
            Item.create(inv_spawner, "MAGMA_CUBE_SPAWN_EGG", 1, 17, Message.getMessage("spawner_magma_cube"));
            Item.create(inv_spawner, "MOOSHROOM_SPAWN_EGG", 1, 18, Message.getMessage("spawner_mooshroom"));
            Item.create(inv_spawner, "MULE_SPAWN_EGG", 1, 19, Message.getMessage("spawner_mule"));
            Item.create(inv_spawner, "OCELOT_SPAWN_EGG", 1, 20, Message.getMessage("spawner_ocelot"));
            Item.create(inv_spawner, "PARROT_SPAWN_EGG", 1, 21, Message.getMessage("spawner_parrot"));
            Item.create(inv_spawner, "PIG_SPAWN_EGG", 1, 22, Message.getMessage("spawner_pig"));
            Item.create(inv_spawner, "POLAR_BEAR_SPAWN_EGG", 1, 23, Message.getMessage("spawner_polar_bear"));
            Item.create(inv_spawner, "RABBIT_SPAWN_EGG", 1, 24, Message.getMessage("spawner_rabbit"));
            Item.create(inv_spawner, "SHEEP_SPAWN_EGG", 1, 25, Message.getMessage("spawner_sheep"));
            Item.create(inv_spawner, "SHULKER_SPAWN_EGG", 1, 26, Message.getMessage("spawner_shulker"));
            Item.create(inv_spawner, "SILVERFISH_SPAWN_EGG", 1, 27, Message.getMessage("spawner_silverfish"));
            Item.create(inv_spawner, "SKELETON_SPAWN_EGG", 1, 28, Message.getMessage("spawner_skeleton"));
            Item.create(inv_spawner, "SKELETON_HORSE_SPAWN_EGG", 1, 29, Message.getMessage("spawner_skeleton_horse"));
            Item.create(inv_spawner, "SLIME_SPAWN_EGG", 1, 30, Message.getMessage("spawner_slime"));
            Item.create(inv_spawner, "SPIDER_SPAWN_EGG", 1, 31, Message.getMessage("spawner_spider"));
            Item.create(inv_spawner, "SQUID_SPAWN_EGG", 1, 32, Message.getMessage("spawner_squid"));
            Item.create(inv_spawner, "STRAY_SPAWN_EGG", 1, 33, Message.getMessage("spawner_stray"));
            Item.create(inv_spawner, "VEX_SPAWN_EGG", 1, 34, Message.getMessage("spawner_vex"));
            Item.create(inv_spawner, "VILLAGER_SPAWN_EGG", 1, 35, Message.getMessage("spawner_villager"));
            Item.create(inv_spawner, "VINDICATOR_SPAWN_EGG", 1, 36, Message.getMessage("spawner_vindicator"));
            Item.create(inv_spawner, "WITCH_SPAWN_EGG", 1, 37, Message.getMessage("spawner_witch"));
            Item.create(inv_spawner, "WOLF_SPAWN_EGG", 1, 38, Message.getMessage("spawner_wolf"));
            Item.create(inv_spawner, "ZOMBIE_SPAWN_EGG", 1, 39, Message.getMessage("spawner_zombie"));
            Item.create(inv_spawner, "ZOMBIE_PIGMAN_SPAWN_EGG", 1, 40, Message.getMessage("spawner_zombie_pigman"));
        }

        Item.create(inv_spawner, "REDSTONE_BLOCK", 1, 54, Message.getMessage("spawner_back"));

        toReturn.setContents(inv_spawner.getContents());

        return toReturn;
    }

    public static Inventory GUI_Inventory(Player p, String target) {

        inventory_inventory_name = Message.getMessage("inventory_inventory").replace("{player}", target);
        target_player = target;

        Inventory toReturn = Bukkit.createInventory(null, inv_inventory_rows, inventory_inventory_name);

        Player player_target = Bukkit.getServer().getPlayer(target);

        if(player_target != null){

            ItemStack[] items = player_target.getInventory().getContents();
            ItemStack[] armor = player_target.getInventory().getArmorContents();

            for(int i = 0; i < items.length; i++){
                if(items[i] != null){
                    String material = items[i].getType().toString();
                    Item.create(inv_inventory, material, items[i].getAmount(), i+1, WordUtils.capitalizeFully(material.replace("_", " ")));
                }else{
                    inv_inventory.setItem(i, null);
                }
            }

            for (int i = 0, j = 37; i < armor.length; i++, j++){
                if(armor[i] != null){
                    String material = armor[i].getType().toString();
                    Item.create(inv_inventory, material, armor[i].getAmount(), j, WordUtils.capitalizeFully(material.replace("_", " ")));
                }else{
                    inv_inventory.setItem(0, null);
                }
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

        for (int i = 42; i < 54; i++){
            Item.create(inv_inventory, "LIGHT_BLUE_STAINED_GLASS_PANE", 1, i, "Empty");
        }

        Item.create(inv_inventory, "GREEN_TERRACOTTA", 1, 46, Message.getMessage("inventory_refresh"));

        Item.create(inv_inventory, "REDSTONE_BLOCK", 1, 54, Message.getMessage("inventory_back"));

        toReturn.setContents(inv_inventory.getContents());

        return toReturn;
    }

    public static void clicked_main(Player p, int slot, ItemStack clicked, Inventory inv){

        if(InventoryGUI.getClickedItem(clicked, Message.getMessage("main_quit"))){
            p.closeInventory();
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("main_player").replace("{player}", p.getName()))) {
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("main_world"))){
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("main_players"))){
            p.openInventory(GUI_Players(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("main_maintenance_mode"))) {
            if(p.hasPermission("admingui.maintenance.manage")){
                if (maintenance_mode) {
                    maintenance_mode = false;
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_maintenance_disabled"));
                    p.closeInventory();
                } else {
                    maintenance_mode = true;
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_maintenance_enabled"));
                    p.closeInventory();
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        if (!pl.isOp() && !pl.hasPermission("admingui.maintenance")) {
                            pl.kickPlayer(Message.getMessage("prefix") + Message.getMessage("message_maintenance"));
                        }
                    }
                }
            }else{
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("permission"));
                p.closeInventory();
            }
        }

    }

    public static void clicked_player(Player p, int slot, ItemStack clicked, Inventory inv){

        if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_back"))) {
            p.openInventory(GUI_Main(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_heal"))){
            p.setHealth(20);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_heal"));
            p.closeInventory();
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_feed"))){
            p.setFoodLevel(20);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_feed"));
            p.closeInventory();
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("player_survival"))){
            p.setGameMode(GameMode.ADVENTURE);
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("player_adventure"))){
            p.setGameMode(GameMode.CREATIVE);
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("player_creative"))){
            p.setGameMode(GameMode.SPECTATOR);
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("player_spectator"))){
            p.setGameMode(GameMode.SURVIVAL);
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_god_enabled"))){
            p.setInvulnerable(true);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_god_enabled"));
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_god_disabled"))){
            p.setInvulnerable(false);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_god_disabled"));
            p.openInventory(GUI_Player(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_potions"))){
            p.openInventory(GUI_Potions(p, p.getName()));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_spawner"))){
            p.openInventory(GUI_Spawner(p, p.getName()));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_kill"))){
            p.setHealth(0);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kill"));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("player_burn"))){
            p.setFireTicks(500);
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_burn"));
        }
    }

    public static void clicked_world(Player p, int slot, ItemStack clicked, Inventory inv){

        if(InventoryGUI.getClickedItem(clicked, Message.getMessage("world_back"))) {
            p.openInventory(GUI_Main(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_day"))){
            p.getPlayer().getWorld().setTime(13000);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_night"))){
            p.getPlayer().getWorld().setTime(0);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_clear"))){
            World world = p.getWorld();
            world.setThundering(false);
            world.setStorm(true);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_rain"))){
            World world = p.getWorld();
            world.setStorm(true);
            world.setThundering(true);
            p.openInventory(GUI_World(p));
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("world_thunder"))){
            World world = p.getWorld();
            world.setStorm(false);
            world.setThundering(false);
            p.openInventory(GUI_World(p));
        }

    }

    public static void clicked_players(Player p, int slot, ItemStack clicked, Inventory inv){

        if(clicked.getItemMeta().getLore() != null){
            if(clicked.getItemMeta().getLore().get(0).equals(Message.getMessage("players_lore"))){
                Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
                if(target_player != null){
                    p.openInventory(GUI_Players_Settings(p,target_player));
                }else{
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
                    p.closeInventory();
                }
            }
        }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_back"))){
            p.openInventory(GUI_Main(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_previous"))){
            page--;
            p.openInventory(GUI_Players(p));
        }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_next"))){
            page++;
            p.openInventory(GUI_Players(p));
        }

    }

    public static void clicked_players_settings(Player p, int slot, ItemStack clicked, Inventory inv, String title){

        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_back"))){
                p.openInventory(GUI_Players(p));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_info").replace("{player}", target_player.getName()))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_actions"))){
                p.openInventory(GUI_Actions(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("players_settings_spawner"))){
                p.openInventory(GUI_Spawner(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_settings_kick_player"))){
                p.openInventory(GUI_Kick(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("players_settings_ban_player"))){
                p.openInventory(GUI_Ban(p, target_player.getName()));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public static void clicked_actions(Player p, int slot, ItemStack clicked, Inventory inv, String title){

        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_back"))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_info").replace("{player}", target_player.getName()))){
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_survival"))){
                target_player.setGameMode(GameMode.ADVENTURE);
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_adventure"))){
                target_player.setGameMode(GameMode.CREATIVE);
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_creative"))){
                target_player.setGameMode(GameMode.SPECTATOR);
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_spectator"))){
                target_player.setGameMode(GameMode.SURVIVAL);
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_teleport_to_player"))){
                p.closeInventory();
                p.teleport(target_player.getLocation());
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_teleport").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_kill_player"))){
                target_player.getPlayer().setHealth(0);
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kill").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_burn_player"))){
                target_player.setFireTicks(500);
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_burn").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_teleport_player_to_you"))){
                p.closeInventory();
                target_player.teleport(p.getLocation());
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_teleport").replace("{player}", p.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_heal"))){
                target_player.setHealth(20);
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_heal").replace("{player}", p.getName()));
                p.sendMessage(Message.chat(Message.getMessage("prefix") + Message.getMessage("message_player_heal").replace("{player}", target_player.getName())));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("actions_feed"))){
                target_player.setFoodLevel(20);
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_feed").replace("{player}", p.getName()));
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_feed").replace("{player}", target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_god_enabled"))){
                target_player.setInvulnerable(true);
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_god_enabled").replace("{player}", target_player.getName()));
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_god_enabled").replace("{player}", p.getName()));
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_god_disabled"))){
                target_player.setInvulnerable(false);
                p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_god_disabled").replace("{player}", target_player.getName()));
                target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_god_disabled").replace("{player}", p.getName()));
                p.openInventory(GUI_Actions(p,target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_potions"))){
                p.openInventory(GUI_Potions(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("actions_inventory"))){
                p.openInventory(GUI_Inventory(p, target_player.getName()));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public static void clicked_kick(Player p, int slot, ItemStack clicked, Inventory inv, String title){

        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_back"))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_hacking"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_hacking"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_griefing"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_griefing"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_spamming"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_spamming"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_advertising"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_advertising"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("kick_swearing"))){
                if(target_player.hasPermission("admingui.kick.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_kick_bypass"));
                    p.closeInventory();
                }else{
                    target_player.kickPlayer(Message.getMessage("prefix") + Message.getMessage("kick") + Message.getMessage("kick_swearing"));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_kick").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public static void clicked_ban(Player p, int slot, ItemStack clicked, Inventory inv, String title){

        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        long mil_year = 31556952000L;
        long mil_month = 2592000000L;
        long mil_day = 86400000L;
        long mil_hour = 3600000L;
        long mil_minute = 60000L;

        Date time = new Date(System.currentTimeMillis()+(mil_minute*ban_minutes)+(mil_hour*ban_hours)+(mil_day*ban_days)+(mil_month*ban_months)+(mil_year*ban_years));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_back"))){
                p.openInventory(GUI_Players_Settings(p, target_player));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_hacking"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(),  TargetPlayer.banReason("ban_hacking", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_hacking", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_griefing"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_griefing", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_griefing", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_spamming"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_spamming", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_spamming", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_advertising"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_advertising", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_advertising", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_swearing"))){
                if(target_player.hasPermission("admingui.ban.bypass")){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_ban_bypass"));
                    p.closeInventory();
                }else{
                    TargetPlayer.ban(target_player.getName(), TargetPlayer.banReason("ban_swearing", time), time);
                    target_player.kickPlayer(Message.getMessage("prefix") + TargetPlayer.banReason("ban_swearing", time));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_ban").replace("{player}", target_player.getName()));
                    p.closeInventory();
                }
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_years"))){
                switch (ban_years){
                    case 0:
                        ban_years = 1;
                        break;
                    case 1:
                        ban_years = 2;
                        break;
                    case 2:
                        ban_years = 3;
                        break;
                    case 3:
                        ban_years = 4;
                        break;
                    case 4:
                        ban_years = 5;
                        break;
                    case 5:
                        ban_years = 6;
                        break;
                    case 6:
                        ban_years = 7;
                        break;
                    case 7:
                        ban_years = 8;
                        break;
                    case 8:
                        ban_years = 9;
                        break;
                    case 9:
                        ban_years = 10;
                        break;
                    case 10:
                        ban_years = 15;
                        break;
                    case 15:
                        ban_years = 20;
                        break;
                    case 20:
                        ban_years = 30;
                        break;
                    case 30:
                        ban_years = 0;
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_months"))){
                switch (ban_months){
                    case 0:
                        ban_months = 1;
                        break;
                    case 1:
                        ban_months = 2;
                        break;
                    case 2:
                        ban_months = 3;
                        break;
                    case 3:
                        ban_months = 4;
                        break;
                    case 4:
                        ban_months = 5;
                        break;
                    case 5:
                        ban_months = 6;
                        break;
                    case 6:
                        ban_months = 7;
                        break;
                    case 7:
                        ban_months = 8;
                        break;
                    case 8:
                        ban_months = 9;
                        break;
                    case 9:
                        ban_months = 10;
                        break;
                    case 10:
                        ban_months = 11;
                        break;
                    case 11:
                        ban_months = 12;
                        break;
                    case 12:
                        ban_months = 0;
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_days"))){
                switch (ban_days){
                    case 0:
                        ban_days = 1;
                        break;
                    case 1:
                        ban_days = 2;
                        break;
                    case 2:
                        ban_days = 3;
                        break;
                    case 3:
                        ban_days = 4;
                        break;
                    case 4:
                        ban_days = 5;
                        break;
                    case 5:
                        ban_days = 6;
                        break;
                    case 6:
                        ban_days = 7;
                        break;
                    case 7:
                        ban_days = 8;
                        break;
                    case 8:
                        ban_days = 9;
                        break;
                    case 9:
                        ban_days = 10;
                        break;
                    case 10:
                        ban_days = 15;
                        break;
                    case 15:
                        ban_days = 20;
                        break;
                    case 20:
                        ban_days = 30;
                        break;
                    case 30:
                        ban_days = 0;
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_hours"))){
                switch (ban_hours){
                    case 0:
                        ban_hours = 1;
                        break;
                    case 1:
                        ban_hours = 2;
                        break;
                    case 2:
                        ban_hours = 3;
                        break;
                    case 3:
                        ban_hours = 4;
                        break;
                    case 4:
                        ban_hours = 5;
                        break;
                    case 5:
                        ban_hours = 6;
                        break;
                    case 6:
                        ban_hours = 7;
                        break;
                    case 7:
                        ban_hours = 8;
                        break;
                    case 8:
                        ban_hours = 9;
                        break;
                    case 9:
                        ban_hours = 10;
                        break;
                    case 10:
                        ban_hours = 15;
                        break;
                    case 15:
                        ban_hours = 20;
                        break;
                    case 20:
                        ban_hours = 0;
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked,Message.getMessage("ban_minutes"))){
                switch (ban_minutes){
                    case 0:
                        ban_minutes = 5;
                        break;
                    case 5:
                        ban_minutes = 10;
                        break;
                    case 10:
                        ban_minutes = 15;
                        break;
                    case 15:
                        ban_minutes = 20;
                        break;
                    case 20:
                        ban_minutes = 25;
                        break;
                    case 25:
                        ban_minutes = 30;
                        break;
                    case 30:
                        ban_minutes = 35;
                        break;
                    case 35:
                        ban_minutes = 40;
                        break;
                    case 40:
                        ban_minutes = 45;
                        break;
                    case 45:
                        ban_minutes = 50;
                        break;
                    case 50:
                        ban_minutes = 55;
                        break;
                    case 55:
                        ban_minutes = 0;
                        break;
                }
                p.openInventory(GUI_Ban(p, target_player.getName()));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }

    }

    public static void clicked_potions(Player p, int slot, ItemStack clicked, Inventory inv, String title){

        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_back"))){
                if(p.getName().equals(target_player.getName())){
                    p.openInventory(GUI_Player(p));
                }else{
                    p.openInventory(GUI_Actions(p,target_player.getName()));
                }

            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_time"))){
                switch (duration){
                    case 1:
                        duration = 2;
                        break;
                    case 2:
                        duration = 3;
                        break;
                    case 3:
                        duration = 4;
                        break;
                    case 4:
                        duration = 5;
                        break;
                    case 5:
                        duration = 7;
                        break;
                    case 7:
                        duration = 10;
                        break;
                    case 10:
                        duration = 15;
                        break;
                    case 15:
                        duration = 20;
                        break;
                    case 20:
                        duration = 1000000;
                        break;
                    case 1000000:
                        duration = 1;
                        break;
                }
                p.openInventory(GUI_Potions(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_level"))){
                switch (level){
                    case 1:
                        level = 2;
                        break;
                    case 2:
                        level = 3;
                        break;
                    case 3:
                        level = 4;
                        break;
                    case 4:
                        level = 5;
                        break;
                    case 5:
                        level = 1;
                        break;
                }
                p.openInventory(GUI_Potions(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_remove_all"))) {
                for (PotionEffect effect : target_player.getActivePotionEffects()){
                    target_player.removePotionEffect(effect.getType());
                }

                if(p.getName().equals(target_player.getName())){
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_potions_remove"));
                }else{
                    target_player.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_target_player_potions_remove").replace("{player}", p.getName()));
                    p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_potions_remove").replace("{player}", target_player.getName()));
                }

                p.openInventory(GUI_Potions(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_night_vision"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.NIGHT_VISION, "potions_night_vision");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_invisibility"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.INVISIBILITY, "potions_invisibility");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_jump_boost"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.JUMP, "potions_jump_boost");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_fire_resistance"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.FIRE_RESISTANCE, "potions_fire_resistance");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_speed"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.SPEED, "potions_speed");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_slowness"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.SLOW, "potions_slowness");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_water_breathing"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.WATER_BREATHING, "potions_water_breathing");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_instant_health"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.HEAL, "potions_instant_health");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_instant_damage"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.HARM, "potions_instant_damage");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_poison"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.POISON, "potions_poison");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_regeneration"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.REGENERATION, "potions_regeneration");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_strength"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.INCREASE_DAMAGE, "potions_strength");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_weakness"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.WEAKNESS, "potions_weakness");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_luck"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.LUCK, "potions_luck");
                p.closeInventory();
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("potions_slow_falling"))){
                TargetPlayer.setPotionEffect(p, target_player, PotionEffectType.SLOW_FALLING, "potions_slow_falling");
                p.closeInventory();
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }
    }

    public static void clicked_spawner(Player p, int slot, ItemStack clicked, Inventory inv, String title){

        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_back"))){
                if(p.getName().equals(target_player.getName())){
                    p.openInventory(GUI_Player(p));
                }else{
                    p.openInventory(GUI_Players_Settings(p, target_player));
                }
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_bat"))){
                Entity.spawn(target_player.getLocation(), EntityType.BAT);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_blaze"))){
                Entity.spawn(target_player.getLocation(), EntityType.BLAZE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_cat"))){
                Entity.spawn(target_player.getLocation(), EntityType.CAT);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_cave_spider"))){
                Entity.spawn(target_player.getLocation(), EntityType.CAVE_SPIDER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_chicken"))){
                Entity.spawn(target_player.getLocation(), EntityType.CHICKEN);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_cod"))){
                Entity.spawn(target_player.getLocation(), EntityType.COD);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_cow"))){
                Entity.spawn(target_player.getLocation(), EntityType.COW);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_creeper"))){
                Entity.spawn(target_player.getLocation(), EntityType.CREEPER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_dolphin"))){
                Entity.spawn(target_player.getLocation(), EntityType.DOLPHIN);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_donkey"))){
                Entity.spawn(target_player.getLocation(), EntityType.DONKEY);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_drowned"))){
                Entity.spawn(target_player.getLocation(), EntityType.DROWNED);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_elder_guardian"))){
                Entity.spawn(target_player.getLocation(), EntityType.ELDER_GUARDIAN);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_enderman"))){
                Entity.spawn(target_player.getLocation(), EntityType.ENDERMAN);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_endermite"))){
                Entity.spawn(target_player.getLocation(), EntityType.ENDERMITE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_evoker"))){
                Entity.spawn(target_player.getLocation(), EntityType.EVOKER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_fox"))){
                Entity.spawn(target_player.getLocation(), EntityType.FOX);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_ghast"))){
                Entity.spawn(target_player.getLocation(), EntityType.GHAST);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_guardian"))){
                Entity.spawn(target_player.getLocation(), EntityType.GUARDIAN);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_horse"))){
                Entity.spawn(target_player.getLocation(), EntityType.HORSE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_husk"))){
                Entity.spawn(target_player.getLocation(), EntityType.HUSK);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_llama"))){
                Entity.spawn(target_player.getLocation(), EntityType.LLAMA);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_magma_cube"))){
                Entity.spawn(target_player.getLocation(), EntityType.MAGMA_CUBE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_mooshroom"))){
                Entity.spawn(target_player.getLocation(), EntityType.MUSHROOM_COW);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_mule"))){
                Entity.spawn(target_player.getLocation(), EntityType.MULE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_ocelot"))){
                Entity.spawn(target_player.getLocation(), EntityType.OCELOT);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_panda"))){
                Entity.spawn(target_player.getLocation(), EntityType.PANDA);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_parrot"))){
                Entity.spawn(target_player.getLocation(), EntityType.PARROT);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_phantom"))){
                Entity.spawn(target_player.getLocation(), EntityType.PHANTOM);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_pig"))){
                Entity.spawn(target_player.getLocation(), EntityType.PIG);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_pillager"))){
                Entity.spawn(target_player.getLocation(), EntityType.PILLAGER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_polar_bear"))){
                Entity.spawn(target_player.getLocation(), EntityType.POLAR_BEAR);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_pufferfish"))){
                Entity.spawn(target_player.getLocation(), EntityType.PUFFERFISH);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_rabbit"))){
                Entity.spawn(target_player.getLocation(), EntityType.RABBIT);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_ravager"))){
                Entity.spawn(target_player.getLocation(), EntityType.RAVAGER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_salmon"))){
                Entity.spawn(target_player.getLocation(), EntityType.SALMON);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_sheep"))){
                Entity.spawn(target_player.getLocation(), EntityType.SHEEP);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_shulker"))){
                Entity.spawn(target_player.getLocation(), EntityType.SHULKER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_silverfish"))){
                Entity.spawn(target_player.getLocation(), EntityType.SILVERFISH);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_skeleton"))){
                Entity.spawn(target_player.getLocation(), EntityType.SKELETON);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_skeleton_horse"))){
                Entity.spawn(target_player.getLocation(), EntityType.SKELETON_HORSE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_slime"))){
                Entity.spawn(target_player.getLocation(), EntityType.SLIME);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_spider"))){
                Entity.spawn(target_player.getLocation(), EntityType.SPIDER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_squid"))){
                Entity.spawn(target_player.getLocation(), EntityType.SQUID);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_stray"))){
                Entity.spawn(target_player.getLocation(), EntityType.STRAY);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_tropical_fish"))){
                Entity.spawn(target_player.getLocation(), EntityType.TROPICAL_FISH);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_turtle"))){
                Entity.spawn(target_player.getLocation(), EntityType.TURTLE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_vex"))){
                Entity.spawn(target_player.getLocation(), EntityType.VEX);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_villager"))){
                Entity.spawn(target_player.getLocation(), EntityType.VILLAGER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_vindicator"))){
                Entity.spawn(target_player.getLocation(), EntityType.VINDICATOR);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_wandering_trader"))){
                Entity.spawn(target_player.getLocation(), EntityType.WANDERING_TRADER);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_witch"))){
                Entity.spawn(target_player.getLocation(), EntityType.WITCH);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_wolf"))){
                Entity.spawn(target_player.getLocation(), EntityType.WOLF);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_zombie"))){
                Entity.spawn(target_player.getLocation(), EntityType.ZOMBIE);
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("spawner_zombie_pigman"))){
                Entity.spawn(target_player.getLocation(), EntityType.PIG_ZOMBIE);
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }
    }

    public static void clicked_inventory(Player p, int slot, ItemStack clicked, Inventory inv, String title){
        Player target_player = Bukkit.getServer().getPlayer(ChatColor.stripColor(title));

        if(target_player != null){
            if(InventoryGUI.getClickedItem(clicked, Message.getMessage("inventory_back"))){
                p.openInventory(GUI_Actions(p, target_player.getName()));
            }else if(InventoryGUI.getClickedItem(clicked, Message.getMessage("inventory_refresh"))){
                p.openInventory(GUI_Inventory(p, target_player.getName()));
            }
        }else{
            p.sendMessage(Message.getMessage("prefix") + Message.getMessage("message_player_not_found"));
            p.closeInventory();
        }
    }

    public static void setAdminGUI(AdminGUI adminGUI) {
        AdminUI.adminGUI = adminGUI;
    }
}

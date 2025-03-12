package me.aglerr.donations;

import com.muhammaddaffa.mdlib.utils.Common;
import me.aglerr.donations.managers.DependencyManager;
import me.aglerr.donations.managers.DonationGoal;
import me.aglerr.donations.objects.Product;
import me.aglerr.donations.objects.QueueDonation;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigValue {

    public static boolean USE_UUID;

    public static boolean BROADCAST_AVATAR_ENABLED;
    public static String HEADER;
    public static String LINE_1;
    public static String LINE_2;
    public static String LINE_3;
    public static String LINE_4;
    public static String LINE_5;
    public static String LINE_6;
    public static String LINE_7;
    public static String LINE_8;
    public static String FOOTER;

    public static List<String> BROADCAST_NO_AVATAR;

    public static boolean DONATION_GOAL_ENABLED;
    public static double DONATION_GOAL_AMOUNT;
    public static List<String> DONATION_GOAL_COMMANDS;

    public static int PROGRESS_BAR_LENGTH;
    public static char PROGRESS_BAR_SYMBOL;
    public static ChatColor PROGRESS_BAR_COMPLETED_COLOR;
    public static ChatColor PROGRESS_BAR_UNCOMPLETED_COLOR;

    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String INVALID_PRODUCT;
    public static String PERFORM_DONATION;
    public static List<String> HELP_MESSAGES;

    public static void initialize() {
        FileConfiguration config = DonationPlugin.DEFAULT_CONFIG.getConfig();
        USE_UUID = config.getBoolean("options.useUUID");
        BROADCAST_AVATAR_ENABLED = config.getBoolean("donationsMessage.messageWithAvatar.enabled");
        HEADER = config.getString("donationsMessage.messageWithAvatar.messages.header");
        LINE_1 = config.getString("donationsMessage.messageWithAvatar.messages.line1");
        LINE_2 = config.getString("donationsMessage.messageWithAvatar.messages.line2");
        LINE_3 = config.getString("donationsMessage.messageWithAvatar.messages.line3");
        LINE_4 = config.getString("donationsMessage.messageWithAvatar.messages.line4");
        LINE_5 = config.getString("donationsMessage.messageWithAvatar.messages.line5");
        LINE_6 = config.getString("donationsMessage.messageWithAvatar.messages.line6");
        LINE_7 = config.getString("donationsMessage.messageWithAvatar.messages.line7");
        LINE_8 = config.getString("donationsMessage.messageWithAvatar.messages.line8");
        FOOTER = config.getString("donationsMessage.messageWithAvatar.messages.footer");
        BROADCAST_NO_AVATAR = config.getStringList("donationsMessage.messageWithoutAvatar.messages");
        DONATION_GOAL_ENABLED = config.getBoolean("donationGoal.enabled");
        DONATION_GOAL_AMOUNT = config.getDouble("donationGoal.donationGoal");
        DONATION_GOAL_COMMANDS = config.getStringList("donationGoal.commandsOnReach");
        PROGRESS_BAR_LENGTH = config.getInt("donationGoal.progressBar.barLength");
        PROGRESS_BAR_SYMBOL = config.getString("donationGoal.progressBar.symbol").charAt(0);
        PROGRESS_BAR_COMPLETED_COLOR = ChatColor.valueOf(config.getString("donationGoal.progressBar.completedColor"));
        PROGRESS_BAR_UNCOMPLETED_COLOR = ChatColor.valueOf(config.getString("donationGoal.progressBar.notCompletedColor"));
        NO_PERMISSION = config.getString("messages.noPermission");
        RELOAD = config.getString("messages.reload");
        HELP_MESSAGES = config.getStringList("messages.help");
        INVALID_PRODUCT = config.getString("messages.invalidProduct");
        PERFORM_DONATION = config.getString("messages.performDonation");
    }

    public static String[] donationAvatar(QueueDonation donation) {
        return new String[]{
                finalParse(LINE_1, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_2, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_3, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_4, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_5, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_6, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_7, donation.getPlayer(), donation.getProduct()),
                finalParse(LINE_8, donation.getPlayer(), donation.getProduct())
        };
    }

    public static List<String> donationNoAvatar(QueueDonation donation){
        List<String> messages = new ArrayList<>();
        BROADCAST_NO_AVATAR.forEach(message ->
                messages.add(finalParse(message, donation.getPlayer(), donation.getProduct())));
        return messages;
    }

    private static String finalParse(String string, OfflinePlayer player, Product product){
        if (!DependencyManager.PLACEHOLDER_API_ENABLED) {
            return Common.color(parseProduct(string, player, product));
        }
        return Common.color(placeholderAPI(string, player, product));
    }

    private static String placeholderAPI(String string, OfflinePlayer player, Product product){
        return PlaceholderAPI.setPlaceholders(player, parseProduct(string, player, product));
    }

    private static String parseProduct(String string, OfflinePlayer player, Product product) {
        return string
                .replace("{product_name}", product.getName())
                .replace("{product_displayname}", product.getDisplayName())
                .replace("{product_price}", product.getPrice() + "")
                .replace("{player}", player.getName())
                .replace("{goal_progress_bar}", DonationGoal.getProgressBar())
                .replace("{goal_percentage}", DonationGoal.getDonationPercentage())
                .replace("{goal_donation_goal}", DonationGoal.getDonationGoal())
                .replace("{goal_current_donation}", DonationGoal.getCurrentDonation());
    }

}

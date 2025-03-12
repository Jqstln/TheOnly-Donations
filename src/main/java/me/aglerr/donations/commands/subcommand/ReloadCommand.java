package me.aglerr.donations.commands.subcommand;

import com.muhammaddaffa.mdlib.utils.Common;
import me.aglerr.donations.ConfigValue;
import me.aglerr.donations.DonationPlugin;
import me.aglerr.donations.commands.abstraction.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand extends SubCommand {

    @Override
    public String getPermission() {
        return "donations.admin";
    }

    @Override
    public List<String> parseTabCompletion(DonationPlugin plugin, CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public void execute(DonationPlugin plugin, CommandSender sender, String[] args) {
        plugin.reloadEverything();
        Common.sendMessage(sender, ConfigValue.RELOAD);
    }

}

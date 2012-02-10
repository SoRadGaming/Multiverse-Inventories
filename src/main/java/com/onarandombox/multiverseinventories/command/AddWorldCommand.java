package com.onarandombox.multiverseinventories.command;

import com.onarandombox.multiverseinventories.api.profile.WorldGroupProfile;
import com.onarandombox.multiverseinventories.util.MVIPerms;
import com.onarandombox.multiverseinventories.MultiverseInventories;
import com.onarandombox.multiverseinventories.locale.Message;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The /mvi info Command.
 */
public class AddWorldCommand extends InventoriesCommand {

    public AddWorldCommand(MultiverseInventories plugin) {
        super(plugin);
        this.setName("Adds a World to a World Group.");
        this.setCommandUsage("/mvinv addworld {WORLD} {GROUP}");
        this.setArgRange(2, 2);
        this.addKey("mvinv addworld");
        this.addKey("mvinv addw");
        this.addKey("mvinvaw");
        this.addKey("mvinvaddw");
        this.addKey("mvinvaddworld");
        this.setPermission(MVIPerms.COMMAND_ADDWORLD.getPerm());
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        World world = Bukkit.getWorld(args.get(0));
        if (world == null) {
            this.getPlugin().getMessager().normal(Message.ERROR_NO_WORLD, sender, args.get(0));
            return;
        }
        WorldGroupProfile worldGroup = this.getPlugin().getGroupManager().getGroup(args.get(1));
        if (worldGroup == null) {
            this.getPlugin().getMessager().normal(Message.ERROR_NO_GROUP, sender, args.get(1));
            return;
        }
        if (worldGroup.containsWorld(world.getName())) {
            this.getPlugin().getMessager().normal(Message.WORLD_ALREADY_EXISTS, sender, world.getName(),
                    worldGroup.getName());
            return;
        }
        worldGroup.addWorld(world);
        this.getPlugin().getMVIConfig().save();
        this.getPlugin().getMessager().normal(Message.WORLD_ADDED, sender, world.getName(),
                worldGroup.getName());
    }
}


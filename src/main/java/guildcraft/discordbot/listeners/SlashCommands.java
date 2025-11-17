package guildcraft.discordbot.listeners;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Guildcraft
 */
public class SlashCommands extends ListenerAdapter {
    
    PollCreator pollCreator = new PollCreator();
    
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("createpoll")) {
            pollCreator.userPoll(event, event.getChannel());
            
        }
    }
    
    // Guild command -- instantly udpated (max 100)
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("createpoll", "Create a new poll")
                .addOption(OptionType.STRING, "question", "The questions for the poll", true)
                .addOption(OptionType.STRING, "options", "The options separated by a comma", true)
                .addOption(OptionType.INTEGER, "duration_hours", "How long to keep the poll up", false));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}

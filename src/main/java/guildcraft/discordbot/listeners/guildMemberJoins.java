package guildcraft.discordbot.listeners;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Creates the listener logic for when a user joins the discord server/guild for the first time
 * 
 * @author Guildcraft
 * @version 1.0
 * @since 2023-12-30
 */
public class guildMemberJoins extends ListenerAdapter {
    
    /**
     * Initialize the Listener for when a guildMemberJoins the server/guild
     */
    public guildMemberJoins() {
        super();
    }
    
    /**
     * Sends a message to the user when the user joins the discord server/guild, asking the user to type in their name.
     * <p>
     * This is a prerequisite to the onMessageReceived event in this class. 
     * Once the user types in their name the onMessageReceived event takes over.
     * 
     * @param event Discord send the event when a new Member joins the Discord Server/Guild
     */
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String message = "Before you can see the rest of the server, please verify yourself by typing in your name in this chat.";
        TextChannel getChannel = event.getGuild().getTextChannelsByName("welcome", true).get(0);
        getChannel.sendMessage(message).queue();
    }
    
    /**
     * When a new user types in the welcome channel after joining the server, the user's nickname will be updated to the test the user entered.
     * <p>
     * The user's role will be changed to the verified role.
     * <p>
     * A welcome message will be sent to the general TextChannel.
     * 
     * @param event When a user sends a message in the server the bot is running in
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        TextChannel welcomeChannel = guild.getTextChannelsByName("welcome", true).get(0);
        TextChannel generalChannel = guild.getTextChannelsByName("general", true).get(0);
        String message = "Welcome to " + guild.getName() + ", " + event.getMessage().getContentRaw();
        Member updateMember = guild.retrieveMember(event.getAuthor()).complete();
        Role retrieveRole = guild.getRolesByName("Verified", true).get(0);
                
        if(event.getChannel().equals(welcomeChannel)) {
            generalChannel.sendMessage(message).queue();
            guild.addRoleToMember(updateMember, retrieveRole).queue();
            updateMember.modifyNickname(event.getMessage().getContentRaw()).queue();
        }
        
    }
    
}

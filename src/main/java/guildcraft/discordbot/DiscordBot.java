package guildcraft.discordbot;

import guildcraft.discordbot.environmentVariables.readEnvironmentFiles;
import guildcraft.discordbot.listeners.guildMemberJoins;
import java.nio.file.*;
import javax.swing.JFrame;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

/**
 * Initialize the bot, with the option of adding a gui frame for interactive presentation showing the bot running.
 * <p>
 * This project has a dependency on JDA - version 5.0.0-beta.18 using JDK 21
 *
 * @author Guildcraft
 * @version 1.0
 * @since 2023-12-30
 */
public class DiscordBot {

    private final ShardManager shardManager;
    private final DefaultShardManagerBuilder builder;

    /**
     * Initializes the DefaultShardMenagerBuilder and the ShardManager variables.
     * Turns on the bot to an online state, with the activity notification showing. 
     * 
     * 
     * @param token The token for the bot that is sent out in the REST command
     */
    public DiscordBot(String token) {
        builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("Guildcraft Bot"));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_PRESENCES);

        shardManager = builder.build();
    }

    /**
     * Adds a gui to the project and calls on the DiscordBot(String token) constructor.
     * 
     * @param token The token for the bot that is sent out in the REST command
     * @param frame Creates a gui JFrame 
     */
    public DiscordBot(String token, JFrame frame) {
        this(token);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
    
    /**
     * Add the different Event Listeners to this method. 
     * <p>
     * This method adds the different events and commands that the bot can do in the Discord Server.
     */
    public void addEventListeners() {
        shardManager.addEventListener(new guildMemberJoins());
    }

    /**
     * Tells the JVM to run this code specifically. This method initializes the program itself
     * 
     * @param args No explicit use for incoming arguments
     */
    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String envFile = currentRelativePath.toAbsolutePath().toString() + "\\src\\main\\java\\guildcraft\\discordbot\\environmentVariables\\.env";
        readEnvironmentFiles tokens = new readEnvironmentFiles(envFile);
        DiscordBot bot = new DiscordBot(
                tokens.variableValue("BOT_TOKEN")
                //comment the next line out if you do not want a gui
                , new JFrame("Testing")
        );
        bot.addEventListeners();
        
    }
}

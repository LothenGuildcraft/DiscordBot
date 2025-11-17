package guildcraft.discordbot.listeners;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessagePollBuilder;
import net.dv8tion.jda.api.utils.messages.MessagePollData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Guildcraft
 */
public class PollCreator {
    
    private static final Logger logger = LoggerFactory.getLogger(PollCreator.class);
    
    public void userPoll(SlashCommandInteractionEvent event, MessageChannel channel) {
        String question = event.getOption("question").getAsString();
        String optionsInput = event.getOption("options").getAsString();
        Integer durationHours = event.getOption("duration_hours") != null ? event.getOption("duration_hours").getAsInt() : 24;
        
        List<String> optionList = Arrays.asList(optionsInput.split(","))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        
        MessagePollBuilder pollDataBuilder = MessagePollData.builder(question)
                .setDuration(Duration.ofHours(durationHours))
                .setMultiAnswer(false);
        
        for(String answer : optionList) {
            pollDataBuilder.addAnswer(answer);
        }
        
        MessagePollData pollData = pollDataBuilder.build();
        
        event.reply("New poll created!").setEphemeral(true).queue();
        event.getChannel().sendMessagePoll(pollData).queue();
        logger.info("Poll successfully created and queued");
    }    
}

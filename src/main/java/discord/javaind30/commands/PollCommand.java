package discord.javaind30.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public class PollCommand implements SlashCommand {

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String optionName = event.getOptions().get(0).getName();
        String replyMessage = "";
        switch (optionName) {
            case "create": replyMessage="Create new poll!";
            break;
            case "edit" : replyMessage="Edit poll!";
            break;
            case "run" : replyMessage="Run poll!";
        }

        return event.reply()
                .withEphemeral(true)
                .withContent(replyMessage);
    }
}

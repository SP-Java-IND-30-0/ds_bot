package discord.javaind30.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public class PingCommand implements SlashCommand {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        System.out.println("event = " + event);
        return event.reply()
                .withEphemeral(true)
                .withContent("Pong!");
    }
}

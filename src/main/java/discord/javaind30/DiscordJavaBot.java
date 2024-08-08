package discord.javaind30;

import discord.javaind30.listeners.SlashCommandListener;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DiscordJavaBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordJavaBot.class);
    private static final String BOT_TOKEN = "MTI2ODUwMzA1MDQxNTUwOTUzNA.GTGFk-.B6NrNX5ygRIOKnGH6EjEIT1njRYYDs-r7TSD4Y";

    public static void main(String[] args) {

       final GatewayDiscordClient bot = DiscordClient.create(BOT_TOKEN).login().block();

        List<String> commands = List.of("greet.json", "ping.json", "calc.json");

        try {
            new GlobalCommandRegistrar(bot.getRestClient()).registerCommands(commands);
        } catch (Exception e) {
            LOGGER.error("Error trying to register global slash commands", e);
        }

        bot.on(ChatInputInteractionEvent.class, SlashCommandListener::handle)
                .then(bot.onDisconnect())
                .block();

    }
}
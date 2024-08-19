package discord.javaind30;

import discord.javaind30.listeners.SlashCommandListener;
import discord.javaind30.registrar.GlobalCommandRegistrar;
import discord.javaind30.registrar.GuildCommandRegistrar;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;


public class DiscordJavaBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordJavaBot.class);
    private static final String BOT_TOKEN = "MTI2ODUwMzA1MDQxNTUwOTUzNA.GTGFk-.B6NrNX5ygRIOKnGH6EjEIT1njRYYDs-r7TSD4Y";

    public static void main(String[] args) {


        try {
            DbHandler db = DbHandler.getInstance();
            db.createTables();
        } catch (SQLException e) {
            LOGGER.error("Error trying to connect to database", e);
        }

        final GatewayDiscordClient bot = DiscordClient.create(BOT_TOKEN).login().block();

        List<String> globalCommands = List.of("greet.json", "ping.json", "calc.json", "poll.json");
//        List<String> guildCommands = List.of("poll.json");


        try {
            new GlobalCommandRegistrar(bot.getRestClient()).registerCommands(globalCommands);
        } catch (Exception e) {
            LOGGER.error("Error trying to register global slash commands", e);
        }
//        try {
//            new GuildCommandRegistrar(bot.getRestClient()).registerCommands(guildCommands);
//        } catch (Exception e){
//            LOGGER.error("Error trying to register guild slash commands", e);
//        }

        bot.on(ChatInputInteractionEvent.class, SlashCommandListener::handle).then()
                .block();

        bot.onDisconnect().block();
    }



}
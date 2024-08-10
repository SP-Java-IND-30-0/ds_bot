package discord.javaind30;

import discord.javaind30.listeners.SlashCommandListener;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

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

        List<String> commands = List.of("greet.json", "ping.json", "calc.json");

        assert bot != null;
        Flux<Guild> guilds = bot.getGuilds();
        Flux<Snowflake> map=guilds.map(Guild::getId);
        System.out.println("          ___Start");
        for (Snowflake id : map.toIterable()) {

            System.out.println("id = " + id.asLong());
        }
        System.out.println();

//        1267546485877379185
//        1267546485877379185
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
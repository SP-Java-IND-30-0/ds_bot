package discord.javaind30.registrar;

import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;

import java.io.IOException;
import java.util.List;

public class GuildCommandRegistrar extends CommandRegistrar {

    public GuildCommandRegistrar(RestClient restClient) {
        super(restClient);
    }

    public void registerCommands(List<String> fileNames) throws IOException {
        super.registerCommands(fileNames);

        final ApplicationService applicationService = restClient.getApplicationService();

        List<Long> guilds = restClient.getGuilds().map(guild -> guild.id().asLong()).collectList().block();


        for (long guildId : guilds) {
            applicationService.bulkOverwriteGuildApplicationCommand(applicationId, guildId, commands)
                    .doOnNext(cmd -> LOGGER.debug("Successfully registered Guild Command {}", cmd.name()))
                    .doOnError(e -> LOGGER.error("Failed to register guild commands", e))
                    .subscribe();
        }
    }

}

package discord.javaind30.registrar;

import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;

import java.io.IOException;
import java.util.List;

public class GlobalCommandRegistrar extends CommandRegistrar {

    public GlobalCommandRegistrar(RestClient restClient) {
        super(restClient);
    }

    public void registerCommands(List<String> fileNames) throws IOException {
        super.registerCommands(fileNames);

        final ApplicationService applicationService = restClient.getApplicationService();

        applicationService.bulkOverwriteGlobalApplicationCommand(applicationId, commands)
                .doOnNext(cmd -> LOGGER.debug("Successfully registered Global Command {}", cmd.name()))
                .doOnError(e -> LOGGER.error("Failed to register global commands", e))
                .subscribe();
    }

}

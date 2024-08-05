package discord.javaind30;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.TextInput;
import discord4j.core.spec.InteractionPresentModalSpec;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

import discord4j.core.event.domain.Event;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        DiscordClient client = DiscordClient.create("MTI2ODUwMzA1MDQxNTUwOTUzNA.GTGFk-.B6NrNX5ygRIOKnGH6EjEIT1njRYYDs-r7TSD4Y");
        GatewayDiscordClient bot = client.login().block();

        System.out.println("bot = " + bot);

        Flux<MessageCreateEvent> stream=bot.on(MessageCreateEvent.class);

        System.out.println("stream = " + stream);
        System.out.println("stream.getClass() = " + stream.getClass());
        System.out.println("stream.subscribe() = " + stream.subscribe());

        long applicationId = bot.getRestClient().getApplicationId().block();

// Build our command's definition
        ApplicationCommandRequest greetCmdRequest = ApplicationCommandRequest.builder()
                .name("greet")
                .description("Greets You")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("name")
                        .description("Your name")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()
                ).build();

// Create the command with Discord
        bot.getRestClient().getApplicationService()
                .createGlobalApplicationCommand(applicationId, greetCmdRequest)
                .subscribe();

        stream.filter(event -> event.getMessage().getContent().equals("!ping")).subscribe(event -> {
            Message message = event.getMessage();
            System.out.println("message = " + message);
            System.out.println("message.getId() = " + message.getId());

//            if ("!ping".equals(message.getContent()) && !message.getAuthor().get().isBot()) {
                MessageChannel channel = message.getChannel().block();
                channel.createMessage("Pong!").block();
//            }
        });

        bot.on(ChatInputInteractionEvent.class, event -> {
            if (event.getCommandName().equals("greet")) {
        /*
        Since slash command options are optional according to discord, we will wrap it into the following function
        that gets the value of our option as a String without chaining several .get() on all the optional values
        In this case, there is no fear it will return empty/null as this is marked "required: true" in our json.
         */
                String name = event.getOption("name")
                        .flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asString)
                        .get(); //This is warning us that we didn't check if its present, we can ignore this on required options

                //Reply to the slash command, with the name the user supplied
//                return event.reply()
//                        .withEphemeral(true)
//                        .withContent("Hello, " + name);
                return event.presentModal(InteractionPresentModalSpec.builder()
                        .title(name)
                        .customId("modal1")
                        .addAllComponents(Arrays.asList(
                                ActionRow.of(TextInput.small("input1", "A title?").required(false)),
                                ActionRow.of(TextInput.paragraph("para1", "Tell us something...", 250, 928).placeholder("...in more than 250 characters but less than 928").required(true))
                        ))
                        .build());
            }
            return Mono.empty();
        }).subscribe();

        bot.onDisconnect().block();

//        DiscordClient client = DiscordClient.create("MTI2ODUwMzA1MDQxNTUwOTUzNA.GTGFk-.B6NrNX5ygRIOKnGH6EjEIT1njRYYDs-r7TSD4Y");
//
//        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> Mono.empty());
//
//        login.block();

//        DiscordClient.create("MTI2ODUwMzA1MDQxNTUwOTUzNA.GTGFk-.B6NrNX5ygRIOKnGH6EjEIT1njRYYDs-r7TSD4Y")
//                .withGateway(client -> {
//                    client.getEventDispatcher().on(ReadyEvent.class)
//                            .subscribe(ready -> System.out.println("Logged in as " + ready.getSelf().getUsername()));
//
//                    client.getEventDispatcher().on(MessageCreateEvent.class)
//                            .map(MessageCreateEvent::getMessage)
//                            .filter(msg -> msg.getContent().equals("!ping"))
//                            .flatMap(Message::getChannel)
//                            .flatMap(channel -> channel.createMessage("Pong!"))
//                            .subscribe();
//
//                    return client.onDisconnect();
//                })
//                .block();
    }
}
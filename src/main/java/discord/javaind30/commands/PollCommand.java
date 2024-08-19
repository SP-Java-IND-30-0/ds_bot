package discord.javaind30.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.component.LayoutComponent;
import discord4j.core.object.component.TextInput;
import discord4j.core.spec.InteractionApplicationCommandCallbackSpec;
import discord4j.core.spec.InteractionPresentModalSpec;
import reactor.core.publisher.Mono;

public class PollCommand implements SlashCommand {

    @Override
    public String getName() {
        return "poll";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
//        String optionName = event.getOptions().get(0).getName();
        String replyMessage = "";
//        switch (optionName) {
//            case "create":
                LayoutComponent topicInput = ActionRow.of(TextInput.small("topic", "Укажите тему голосования"));
                LayoutComponent optionsInput = ActionRow.of(TextInput.paragraph("options", "Варианты выбора (от 1 до 10)", 1, 10));
                LayoutComponent timeInput = ActionRow.of(TextInput.small("time", "Время голосования (часы)"));
                LayoutComponent multipleChoiceInput = ActionRow.of(TextInput.small("multiple_choice", "Возможность множественного выбора", 1, 1));

                LayoutComponent startButton = ActionRow.of(Button.primary("start_poll", "Запустить голосование в чат"));

                InteractionPresentModalSpec modalSpec = InteractionPresentModalSpec.create()
                        .withTitle("Создание голосования")
                        .withCustomId("create_poll")
                        .withComponents(topicInput, optionsInput, timeInput, multipleChoiceInput, startButton);

//                return event.presentModal(modalSpec);
                return event.presentModal(InteractionPresentModalSpec.builder()
                        .title("User Info")
                        .customId("user_info_modal")
                        .addComponent(ActionRow.of(TextInput.small("name", "Name")))
                        .addComponent(ActionRow.of(TextInput.paragraph("description", "Description")))
                        .build());

//                return event.reply().withEphemeral(true).withContent("Создание голосования").withEmbeds();
//                        .withComponents(ActionRow.of(topicInput));
 //                       .withComponents(ActionRow.of(topicInput), ActionRow.of(optionsInput), ActionRow.of(timeInput), ActionRow.of(multipleChoiceInput), ActionRow.of(startButton));



//            case "edit" : replyMessage="Edit poll!";
//            break;
//            case "run" : replyMessage="Run poll!";
//        }
//
//        return event.reply()
//                .withEphemeral(true)
//                .withContent(replyMessage);
    }
}

package discord.javaind30.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;

public class CalcCommand implements SlashCommand {
    @Override
    public String getName() {
        return "calc";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {

        double num1 = event.getOption("number1")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asDouble)
                .get();

        double num2 = event.getOption("number2")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asDouble)
                .get();

        String operator = event.getOption("operator")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();

        System.out.println(num1 + " " + operator + " " + num2);
        double result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> 0.0;
        };

        return event.reply()
                .withEphemeral(true)
                .withContent("Результат: " + num1 + " " + operator + " " + num2 + " = " + result);
    }
}

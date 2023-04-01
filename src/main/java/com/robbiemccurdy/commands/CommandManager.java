package com.robbiemccurdy.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getCommandString();

        if(command.equals("hello")){
            String user = event.getUser().getAsMention();
            event.getChannel().sendMessage("Hi " + user + "!");
        }
    }
}

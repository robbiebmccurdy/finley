package com.robbiemccurdy.commands;

import com.robbiemccurdy.ICommand;
import com.robbiemccurdy.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Play implements ICommand {
    @Override
    public String getName() {
        return "play";
    }

    public String getDescription(){
        return "Will play a song";
    }

    public List<OptionData> getOptions(){
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "name", "Name of the song to play", true));
        return options;
    }

    public void execute(SlashCommandInteractionEvent event){
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            event.reply("You need to be in a voice channel to call this bot.").queue();
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        } else {
            if(selfVoiceState.getChannel() != memberVoiceState.getChannel()){
                event.reply("You need to be in the same channel as me.").queue();
            }
        }

        PlayerManager playerManager = PlayerManager.get();
        event.reply("Playing " + event.getName());
        System.out.print(event.getOptions());
        playerManager.play(event.getGuild(), event.getOption("name").getAsString());
    }
}

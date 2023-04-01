package com.robbiemccurdy.listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser(); //grabs user
        String emoji = event.getEmoji().getAsReactionCode(); //grabs emoji unicode string
        Channel channel = event.getChannel(); // grabs channel
        String channelMention = channel.getAsMention(); //gets channel as a #mention
        String jumpLink = event.getJumpUrl(); //gets the location to where the #mention jumps to

        String message = user.getAsTag().toString() + " reacted to a message with " + emoji + " in the " + channelMention + " channel!"; //reply

        event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(message).queue(); //sends the actual message in default guild channel
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT); //gets raw string
        if(message.contains("ping")){
            event.getChannel().sendMessage("pong").queue();;
        }

        if(message.contains("finley")){
            event.getChannel().sendMessage("Yes?").queue();
        }

        if(message.contains("gremlin")){
            event.getChannel().sendMessage("Abagh Mi Xaridon Iwi Etine.\n" +
                    "Mun Untimoto Batča Gooninu, Ath Eusunoto Še Vrotor Mi Itidonok Črirhenu!").queue();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        User user = event.getUser();
        event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(user.getAsMention() + " has joined the server!").queue();
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        List<Member> members = event.getGuild().getMembers();
        int onlineMem = 0;

        for(Member member : members){
            if(member.getOnlineStatus() == OnlineStatus.ONLINE){
                onlineMem++;
            }
        }

        User user = event.getUser();
        String message = user.getAsMention() + " updated their online status to " + event.getNewOnlineStatus().getKey() + "!\nThere are now " + onlineMem + " online currently.";
        event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(message).queue();
    }
}

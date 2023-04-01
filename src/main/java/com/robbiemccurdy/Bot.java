package com.robbiemccurdy;

import com.robbiemccurdy.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot {

    private final Dotenv config;
    private final ShardManager shardManager;

    public Bot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        //building the shard manager
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("ur mom"));
        builder.setMemberCachePolicy(MemberCachePolicy.ONLINE);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ONLINE_STATUS);
        shardManager = builder.build();

        //registering the listeners
        shardManager.addEventListener(new EventListener());
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
        } catch (LoginException e) {
            System.out.println("ERROR: Bot Token is INVALID");
        }
    }
}

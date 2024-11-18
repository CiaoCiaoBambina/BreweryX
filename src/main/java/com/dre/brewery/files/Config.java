package com.dre.brewery.files;

import com.dre.brewery.storage.DataManagerType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import eu.okaeri.configs.annotation.Header;
import lombok.Getter;
import lombok.Setter;

@Header("""
        Our proper config guide can be found at: https://brewery.lumamc.net/en/guide/edit_config/
        Still have questions? Join our Discord: https://discord.gg/ZTGCzeKg45
        """)
@Getter @Setter
public class Config extends AbstractOkaeriConfigFile {

    @Getter @Exclude
    private static final Config instance = createConfig(Config.class, "config.yml");


    @Comment("The language file to be used (found in plugins/Brewery/languages)")
    private String language = "en";

    @Comment("""
            Enable checking for Updates, Checks the curseforge api for updates to Brewery [true]
            If an Update is found a Message is logged on Server-start and displayed to OPs joining the game""")
    private boolean updateCheck = true;

    @Comment("Autosave interval in minutes [10]")
    private int autosave = 10;

    @Comment("Prefix used on messages")
    private String pluginPrefix = "&2[BreweryX]&f ";

    @Comment("Show debug messages in logs [false]")
    private boolean debug = false;


    @Comment("-- Storage Settings --")
    private Storage storage = new Storage();
    @Getter @Setter
    public static class Storage extends OkaeriConfig {
        @Comment("""
                What type of storage to use [FLATFILE]
                Available types: FlatFile, MySQL, SQLite""")
        private DataManagerType type = DataManagerType.FLATFILE;
        @Comment("The name of the database. When the database is a file, this will be the name of the file. [brewery-data]")
        private String database = "brewery-data";
        private String tablePrefix = "brewery_";
        private String address = "localhost";
        private String username = "root";
        private String password = "";
    }


    // Maybe condense more of this into configuration sections

    @Comment("If the player wakes up at /home when logging in after excessive drinking (/home plugin must be installed!) [true]")
    private boolean enableHome = true;

    @Comment("""
            Type of the home-teleport: ['cmd: home']
            bed = Player will be teleported to his spawn bed
            'cmd: home' = /home will be executed by the player. He has to have permissions for it without any delay!
            'cmd: spawn' = /spawn will be executed by the player.
            'cmd: whatever' = /whatever will be executed by the player.""")
    private String homeType = "cmd: home";

    @Comment("""
            If the player "wakes up" at a random place when offline for some time while drinking (the places have to be defined with '/brew Wakeup add' through an admin)
            The Player wakes at the nearest of two random places of his world [true]""")
    private boolean enableWake = true;
}

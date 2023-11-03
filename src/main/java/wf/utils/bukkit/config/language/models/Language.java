package wf.utils.bukkit.config.language.models;


import wf.utils.bukkit.config.BukkitConfig;

import java.util.List;

public interface Language {

    public default BukkitConfig getConfig(){
        return null;
    }
    public List<String> getAvailableLanguages();
    public LanguageType getLanguageType();

    public MessageReceiver getMessageReceiver(String name);


}

package wf.utils.bukkit.command.handler.lang;

import wf.utils.bukkit.config.language.utils.DefaultMessages;
import wf.utils.bukkit.config.language.utils.DefaultMessagesManager;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValues;
import wf.utils.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DefaultCommandHandlerMessagesManager implements DefaultMessagesManager {

    private static DefaultCommandHandlerMessagesManager instance;

    private final HashMap<String, DefaultMessages> languages;

    private DefaultCommandHandlerMessagesManager() {
        this.languages = new HashMap<>();

        languages.put(DefaultEnCommandHandlerMessages.getInstance().getLang(), DefaultEnCommandHandlerMessages.getInstance());
        languages.put(DefaultRuCommandHandlerMessages.getInstance().getLang(), DefaultRuCommandHandlerMessages.getInstance());
    }


    public static DefaultCommandHandlerMessagesManager getInstance() {
        if(instance == null) instance = new DefaultCommandHandlerMessagesManager();
        return instance;
    }

    @NotNull
    @Override
    public DefaultMessages getDefaultValues(String lang) {
        return languages.getOrDefault(lang, languages.get("en"));
    }


    public HashMap<String, DefaultMessages> getLanguages() {
        return languages;
    }
}

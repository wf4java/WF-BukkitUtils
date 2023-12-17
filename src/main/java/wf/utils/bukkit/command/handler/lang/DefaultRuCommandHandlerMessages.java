package wf.utils.bukkit.command.handler.lang;

import wf.utils.bukkit.config.language.utils.DefaultMessages;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValue;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigDefaultValues;

import java.util.Arrays;
import java.util.Collection;

public class DefaultRuCommandHandlerMessages implements DefaultMessages {


    private final Collection<ConfigDefaultValue> values;

    private static DefaultRuCommandHandlerMessages instance;


    private DefaultRuCommandHandlerMessages() {
        values = Arrays.asList(
                new ConfigDefaultValue("COMMAND.DEFAULT.WRITE_ALL_ARGUMENTS","Введите все аргументы!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.COMMAND_NOT_FOUND","Команда не найдена!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.NOT_FOUND_AVAILABLE_COMMANDS","Не найдено доступных команд!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ONLY_PLAYER_COMMAND","Эта команда только для!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.YOU_NOT_HAVE_PERMISSION","У тебя не прав использовать эту команду!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.LANGUAGE_CHANGE","Твоя язык изменён на: %{lang}"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.BLOCK_ARGUMENT_WRONG","Не правильный аргумент, введите название блока!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.ITEM_ARGUMENT_WRONG","Не правильный аргумент, введите название предмета!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.LANGUAGE_ARGUMENT_WRONG","Не правильный аргумент, введите название доступного языка!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.MATERIAL_ARGUMENT_WRONG","Не правильный аргумент, введите название материала!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.ONLINE_PLAYER_ARGUMENT_WRONG","Не правильный аргумент, введите имя игрока который онлайн!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.BOOLEAN_ARGUMENT_WRONG","Не правильный аргумент, введите true/false!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.DOUBLE_ARGUMENT_WRONG","Не правильный аргумент введите число!"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.INTEGER_ARGUMENT_WRONG","Не правильный аргумент, введите число (целое)"),
                new ConfigDefaultValue("COMMAND.DEFAULT.ARGUMENT.CONFIGURATION_SECTION_ARGUMENT_WRONG","Не правильный аргумент, введите доступное название!"));
    }


    public static DefaultRuCommandHandlerMessages getInstance() {
        if (instance == null) instance = new DefaultRuCommandHandlerMessages();
        return instance;
    }

    public Collection<ConfigDefaultValue> getValues() {
        return values;
    }

    @Override
    public String getLang() {
        return "ru";
    }
}

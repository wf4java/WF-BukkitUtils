package wf.utils.bukkit.command.handler.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.config.language.models.MessageReceiver;

public record ExecutionCommand(Command bukkitCommand, CommandHandler commandHandler, MessageReceiver messageReceiver) {

    public String getMessage(String path) {
        return messageReceiver.get(path);
    }

    public String getMessage(CommandSender sender, String path) {
        return commandHandler.getMessage(sender, path);
    }

    @Override
    public String toString() {
        return "ExecutionCommand{" +
                "bukkitCommand=" + bukkitCommand +
                ", commandHandler=" + commandHandler +
                '}';
    }
}

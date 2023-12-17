package wf.utils.bukkit.command.handler.handler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.config.language.models.MessageReceiver;

public class ExecutionCommand {

    private final Command bukkitCommand;
    private final CommandHandler commandHandler;
    private final MessageReceiver messageReceiver;

    public ExecutionCommand(Command bukkitCommand, CommandHandler commandHandler, MessageReceiver messageReceiver) {
        this.bukkitCommand = bukkitCommand;
        this.commandHandler = commandHandler;
        this.messageReceiver = messageReceiver;
    }

    public Command getBukkitCommand() {
        return bukkitCommand;
    }


    public CommandHandler getCommandHandler() {
        return commandHandler;
    }



    public MessageReceiver getMessageReceiver() {
        return messageReceiver;
    }

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

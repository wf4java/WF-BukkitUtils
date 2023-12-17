package wf.utils.bukkit.command.handler.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import wf.utils.bukkit.command.handler.handler.ExecutionCommand;
import wf.utils.bukkit.command.handler.subcommand.executor.Argument;
import wf.utils.bukkit.command.handler.subcommand.executor.SubCommandExecutor;
import wf.utils.java.functions.TriConsumer;

public class SubCommandBuilder {

    private String command;
    private String permission;

    private TriConsumer<CommandSender, ExecutionCommand, Object[]> runnable;
    private Argument[] arguments;
    private boolean onlyPlayer = false;


    public SubCommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public SubCommandBuilder setRunnable(TriConsumer<CommandSender, ExecutionCommand, Object[]> runnable) {
        this.runnable = runnable;
        return this;
    }

    public SubCommandBuilder setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
        return this;
    }

    public SubCommandBuilder setArguments(Argument... arguments) {
        this.arguments = arguments;
        return this;
    }

    public SubCommandBuilder setCommand(String command) {
        this.command = command;
        return this;
    }

    public SubCommand build() {
        return new SubCommand(command, permission, arguments, runnable, onlyPlayer);
    }

}

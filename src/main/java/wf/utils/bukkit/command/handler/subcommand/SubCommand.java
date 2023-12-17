package wf.utils.bukkit.command.handler.subcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wf.utils.bukkit.command.handler.handler.ExecutionCommand;
import wf.utils.bukkit.command.handler.subcommand.executor.Argument;
import wf.utils.bukkit.command.handler.subcommand.executor.SubCommandExecutor;
import wf.utils.bukkit.config.language.models.MessageReceiver;
import wf.utils.java.functions.TriConsumer;



public class SubCommand {

    private String command;
    private String permission;
    private SubCommandExecutor subCommandExecutor;
    private TriConsumer<CommandSender, ExecutionCommand, Object[]> runnable;
    private boolean onlyPlayer = false;


    public SubCommand(){

    }

    public SubCommand(String command, String permission, Argument[] arguments, TriConsumer<CommandSender, ExecutionCommand, Object[]> runnable, boolean onlyPlayer) {
        this.command = command;
        this.permission = permission;
        this.runnable = runnable;
        this.onlyPlayer = onlyPlayer;
        this.subCommandExecutor = new SubCommandExecutor(command, arguments);
    }




    public void onCommand(CommandSender sender, ExecutionCommand command, String[] args, int argsPosition, MessageReceiver msg){
        if(!checkPermission(sender)){
            sender.sendMessage(ChatColor.RED + ("\n" + (msg == null ? "You not have permission!" : msg.get("COMMAND.DEFAULT.YOU_NOT_HAVE_PERMISSION"))));
            return;
        }

        if(onlyPlayer && (!(sender instanceof Player))){
            sender.sendMessage(ChatColor.RED + ("\n" + (msg == null ? "This command is for players only!" : msg.get("COMMAND.DEFAULT.YOU_NOT_HAVE_PERMISSION"))));
            return;
        }

        Object[] output = subCommandExecutor.calculate(sender, msg, args, argsPosition);
        if(output == null) return;
        runnable.accept(sender, command, output);
    }


    public boolean checkPermission(CommandSender sender){
        if(permission == null) return true;
        if(sender.isOp() || sender.hasPermission("*")) return true;
        return sender.hasPermission(permission);
    }



    public static SubCommandBuilder builder(){
        return new SubCommandBuilder();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public SubCommandExecutor getSubCommandExecutor() {
        return subCommandExecutor;
    }

    public void setSubCommandExecutor(SubCommandExecutor subCommandExecutor) {
        this.subCommandExecutor = subCommandExecutor;
    }

    public TriConsumer<CommandSender, ExecutionCommand, Object[]> getRunnable() {
        return runnable;
    }

    public void setRunnable(TriConsumer<CommandSender, ExecutionCommand, Object[]> runnable) {
        this.runnable = runnable;
    }

    public boolean isOnlyPlayer() {
        return onlyPlayer;
    }

    public void setOnlyPlayer(boolean onlyPlayer) {
        this.onlyPlayer = onlyPlayer;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Subcommand{" +
                "permission=" + permission +
                ", commandBuilder=" + subCommandExecutor +
                ", runnable=" + runnable +
                ", onlyPlayer=" + onlyPlayer +
                '}';
    }
}

package wf.utils.bukkit.command.handler.handler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wf.utils.bukkit.command.handler.subcommand.SubCommand;
import wf.utils.bukkit.command.handler.subcommand.SubCommandBuilder;
import wf.utils.bukkit.command.handler.subcommand.executor.Argument;
import wf.utils.bukkit.command.handler.subcommand.executor.types.bukkit.BukkitArgumentType;
import wf.utils.bukkit.config.language.*;
import wf.utils.bukkit.config.language.models.Language;
import wf.utils.bukkit.config.language.models.LanguageType;
import wf.utils.bukkit.config.language.models.MessageReceiver;


import java.util.*;
import java.util.stream.Collectors;


public class CommandHandler implements CommandExecutor, TabExecutor {


    private final JavaPlugin plugin;
    private String ownCommandName;
    private HashMap<String, SubCommand> subcommands = new HashMap<>();
    private TreeMap<String, SubCommand> sortedSubcommands = new TreeMap<String, SubCommand>((str1, str2) -> str1.compareTo(str2) * -1);
    private Language language;



    public CommandHandler(JavaPlugin plugin, String[] commands, Language language, boolean addDefaultCommands) {
        this.plugin = plugin;
        this.language = language;
        this.ownCommandName = commands[0];
        for(String command : commands){
            plugin.getCommand(command).setExecutor(this);
            plugin.getCommand(command).setTabCompleter(this);
        }
        if(addDefaultCommands) addDefaultCommands();
    }

    public CommandHandler(JavaPlugin plugin, String[] commands, Language language) {
        this(plugin, commands, language, true);
    }

    public CommandHandler(JavaPlugin plugin, String command, Language language) {
        this.plugin = plugin;
        this.language = language;
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
        this.ownCommandName = command;
        addDefaultCommands();
    }


    public CommandHandler(JavaPlugin plugin, String[] commands) {
        this(plugin, commands,null, true);
    }

    public CommandHandler(JavaPlugin plugin, String command) {
        this.plugin = plugin;
        plugin.getCommand(command).setExecutor(this);
        plugin.getCommand(command).setTabCompleter(this);
        this.ownCommandName = command;
        addDefaultCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            sender.sendMessage(ChatColor.GOLD + "/" + ownCommandName + ChatColor.AQUA + " <" + ChatColor.DARK_GREEN +
                    String.join(ChatColor.RED + ", " + ChatColor.DARK_GREEN, subcommands.keySet().stream().
                            map((s) -> s.split("\\.")[0]).distinct().collect(Collectors.toList())) + ChatColor.AQUA + ">");
            return true;
        }

        MessageReceiver msg = getMessageReceiver(sender.getName());

        for(Map.Entry<String, SubCommand> entry : subcommands.entrySet()){
            String[] subcommandArgs = entry.getKey().split("\\.");
            String full = String.join(".", Arrays.copyOfRange(args,0, subcommandArgs.length));
            if(full.equalsIgnoreCase(String.join(".", subcommandArgs))){
                entry.getValue().onCommand(sender, new ExecutionCommand(command, this, msg), args, subcommandArgs.length, msg);
                return true;
            }
        }

        if(msg == null) sender.sendMessage("\n" + ChatColor.RED + "Command not found!");
        else sender.sendMessage(ChatColor.RED + msg.get("COMMAND.DEFAULT.COMMAND_NOT_FOUND"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return null;
        List<String> tabulation = new ArrayList<>();

        for(Map.Entry<String, SubCommand> entry : subcommands.entrySet()){
            if(!entry.getValue().checkPermission(sender)) continue;

            String[] subcommandArgs = entry.getKey().split("\\.");

            if(args.length < 2){
                tabulation.add(subcommandArgs[0]);
            }else {
                boolean isIt = true;
                int max = Math.min(args.length - 1, subcommandArgs.length);
                for(int i = 0; i < max; i++) if(!subcommandArgs[i].equalsIgnoreCase(args[i])) { isIt = false; break; }
                if(!isIt) continue;

                if(args.length <= subcommandArgs.length){
                    tabulation.add(subcommandArgs[args.length - 1]);
                }else if(args.length <= subcommandArgs.length + entry.getValue().getSubCommandExecutor().getArguments().length){
                    return entry.getValue().getSubCommandExecutor().getArguments()[args.length - subcommandArgs.length - 1]
                            .getType().tabulation(sender, args[args.length - 1]);
                }

            }
        }

        return tabulation;
    }

    public void addDefaultCommands(){
        addSubcommand(SubCommand.builder()
                .setCommand("allcommands")
                .setRunnable((sender, command, args) -> {
                    int availableCommandsCount = 0;

                    for(Map.Entry<String, SubCommand> entry : sortedSubcommands.entrySet()){
                        if(entry.getKey().equals("allcommands")) continue;

                        if(entry.getValue().checkPermission(sender)){
                            if(availableCommandsCount == 0) sender.sendMessage("\n");
                            sender.sendMessage(entry.getValue().getSubCommandExecutor().getArgumentsText());
                            availableCommandsCount++;
                        }
                    }

                    if(availableCommandsCount == 0){
                        if(language == null) sender.sendMessage("Not found available commands!");
                        else sender.sendMessage(getMessage(sender,"COMMAND.DEFAULT.NOT_FOUND_AVAILABLE_COMMANDS"));
                    }
                })
                .build());


        if(language != null && language.getAvailableLanguages().size() > 1) {
            if (language.getLanguageType() == LanguageType.GENERAL) {
                addSubcommand(SubCommand.builder()
                        .setCommand("language")
                        .setPermission("wf.language.change")
                        .setArguments(new Argument(BukkitArgumentType.LANGUAGE(language)))
                        .setRunnable((sender, command, args) -> {
                            ((GeneralLanguage) language).selectLanguage(plugin, (String) args[0]);
                            sender.sendMessage(ChatColor.YELLOW + getMessage(sender, "COMMAND.DEFAULT.LANGUAGE_CHANGE")
                                    .replace("%{lang}", ChatColor.AQUA + (String) args[0]));
                        })
                        .build());
            } else if (language.getLanguageType() == LanguageType.PERSONAL) {
                addSubcommand(SubCommand.builder()
                        .setCommand("language")
                        .setArguments(new Argument(BukkitArgumentType.LANGUAGE(language)))
                        .setRunnable((sender, command, args) -> {
                            ((PersonalLanguage) language).setPlayerLanguage(sender.getName(), (String) args[0]);
                            sender.sendMessage(ChatColor.YELLOW + getMessage(sender, "COMMAND.DEFAULT.LANGUAGE_CHANGE")
                                    .replace("%{lang}", ChatColor.AQUA + (String) args[0]));
                        })
                        .build());
            }
        }


    }

    public void addSubcommand(String command, SubCommand subcommand) {
        subcommand.getSubCommandExecutor().setCommand("/" + ownCommandName + " " + String.join(" ", command.split("\\.")) );
        subcommands.put(command, subcommand);
        sortedSubcommands.put(subcommand.getCommand(), subcommand);
    }

    public void addSubcommand(SubCommand subcommand) {
        subcommand.getSubCommandExecutor().setCommand("/" + ownCommandName + " " + String.join(" ", subcommand.getCommand().split("\\.")) );
        subcommands.put(subcommand.getCommand(), subcommand);
        sortedSubcommands.put(subcommand.getCommand(), subcommand);
    }

    public MessageReceiver getMessageReceiver(String player){
        if(language == null) return null;
        return language.getMessageReceiver(player);
    }

    public String getMessage(CommandSender sender, String path){
        if(language == null) return path;
        return language.getMessageReceiver(sender.getName()).get(path);
    }

    public Map<String, SubCommand> getSubcommands() {
        return Collections.unmodifiableMap(subcommands);
    }

    public void setSubcommands(HashMap<String, SubCommand> subcommands) {
        this.subcommands = subcommands;
        sortedSubcommands.putAll(subcommands);
    }

    public Map<String, SubCommand> getSortedSubcommands() {
        return Collections.unmodifiableMap(sortedSubcommands);
    }



    public String getOwnCommandName() {
        return ownCommandName;
    }

    public void setOwnCommandName(String ownCommandName) {
        this.ownCommandName = ownCommandName;
    }

    public Language getLanguage() {
        return language;
    }

    public static CommandHandlerBuilder builder() {
        return new CommandHandlerBuilder();
    }



    @Override
    public String toString() {
        return "CommandHandler{" +
                "plugin=" + plugin +
                ", ownCommandName='" + ownCommandName + '\'' +
                ", subcommands=" + subcommands +
                ", language=" + language +
                '}';
    }
}













package fr.arcanmc.cardinal.server.commands.client;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.client.ClientManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClientListCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("client") && args[1].equalsIgnoreCase("list")) {

            Cardinal cardinal = Cardinal.getInstance();
            ClientManager clientManager = ServerService.get().getClientManager();

            if (clientManager.getClients().isEmpty()) {
                cardinal.getLogger().info("[CLIENT] No clients connected");
                return;
            }
            cardinal.getLogger().info("[CLIENT] List of clients:");
            for (int i = 0; i < clientManager.getClients().size(); i++) {
                Client client = new ArrayList<>(clientManager.getClients()).get(i);
                cardinal.getLogger().info("   Client [" + client.getId() + "] [" + client.getName() + "] - [" + client.getAddress() + "] - [" + client.getInstanceAmount() + " instances]");
            }

        }
    }
}

package fr.arcanmc.cardinal.server.commands.client;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.client.Client;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.server.ServerService;
import fr.arcanmc.cardinal.server.client.ClientManager;

public class ClientInfoCommand implements CommandExecutor {
    @Override
    public void execute(String[] args) {
        if (args.length == 3 && args[0].equalsIgnoreCase("client") && args[1].equalsIgnoreCase("info")) {

            Cardinal cardinal = Cardinal.getInstance();
            ClientManager clientManager = ServerService.get().getClientManager();

            int id = Integer.parseInt(args[2]);

            if (clientManager.getClient(id) != null) {
                Client client = clientManager.getClient(id);
                cardinal.getLogger().info("[CLIENT] Client information:");
                cardinal.getLogger().info("NAME: " + client.getName());
                cardinal.getLogger().info("ID: " + client.getId());
                cardinal.getLogger().info("ADDRESS: " + client.getAddress());
                cardinal.getLogger().info("Game instances: " + client.getInstanceAmount());
            } else {
                cardinal.getLogger().error("Client not found");
            }

        }
    }
}

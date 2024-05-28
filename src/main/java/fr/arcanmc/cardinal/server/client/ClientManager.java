package fr.arcanmc.cardinal.server.client;

import fr.arcanmc.cardinal.api.client.Client;

import java.util.HashSet;
import java.util.Set;

public class ClientManager {

    private final Set<Client> clients;

    public ClientManager() {
        this.clients = new HashSet<>();
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public Client getRandomClient() {
        return this.clients.stream().findAny().orElse(null);
    }

    public Client getClient(String name) {
        return this.clients.stream().filter(client -> client.getName().equals(name)).findFirst().orElse(null);
    }

    public Client getClient(int id) {
        return this.clients.stream().filter(client -> client.getId() == id).findFirst().orElse(null);
    }

    public Client getClientByAddress(String address) {
        return this.clients.stream().filter(client -> client.getAddress().equals(address)).findFirst().orElse(null);
    }

    public boolean hasClient(String name) {
        return this.clients.stream().anyMatch(client -> client.getName().equals(name));
    }

}

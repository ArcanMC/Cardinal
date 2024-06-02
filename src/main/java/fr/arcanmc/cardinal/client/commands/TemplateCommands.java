package fr.arcanmc.cardinal.client.commands;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.command.CommandExecutor;
import fr.arcanmc.cardinal.client.ClientService;
import fr.arcanmc.cardinal.client.template.TemplateManager;
import fr.arcanmc.cardinal.core.console.Logger;

public class TemplateCommands implements CommandExecutor {

    private final TemplateManager templateManager = ClientService.get().getTemplateManager();

    public void execute(String[] args) {
        if (args.length == 0)
            return;
        if (args[0].equalsIgnoreCase("templates") || args[0].equalsIgnoreCase("template")) {
            Logger logger = Cardinal.getInstance().getLogger();
            if (args.length == 1) {
                logger.info("Templates commands:");
                logger.info(" - templates create <name> <prefix>");
                logger.info(" - templates update <name>");
                logger.info(" - templates list");
            } else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("list")) {
                    if (this.templateManager.getTemplateData().getTemplates().isEmpty()) {
                        logger.info("Any saved template has been found...");
                    } else {
                        logger.info("Templates list (" + this.templateManager.getTemplateData().getTemplates().size() + "):");
                        this.templateManager.getTemplateData().getTemplates().forEach(template -> logger.info(" - " + template.getName()));
                    }
                } else {
                    logger.error("Command not found.");
                }
            } else if (args.length == 3) {
                String name = args[2];
                if (args[1].equalsIgnoreCase("update")) {
                    if (this.templateManager.getTemplateData().getTemplates().stream().noneMatch(template -> template.getName().equals(name))) {
                        logger.error("Template not found.");
                        return;
                    }
                    this.templateManager.updateTemplate(args[2]);
                } else if (args[1].equalsIgnoreCase("delete")) {
                    if (this.templateManager.getTemplateData().getTemplates().stream().noneMatch(template -> template.getName().equals(name))) {
                        logger.error("Template not found.");
                        return;
                    }
                    this.templateManager.deleteTemplate(name);
                } else {
                    logger.error("Command not found.");
                }
            } else if (args.length == 4 && args[1].equalsIgnoreCase("create")) {
                String name = args[2];
                String prefix = args[3];
                if (this.templateManager.getTemplateData().getTemplates().stream().anyMatch(template -> template.getName().equals(name))) {
                    logger.error("Template already exists.");
                    return;
                }
                this.templateManager.createTemplate(name, prefix);
            } else {
                logger.error("Command not found.");
            }
        }
    }
}

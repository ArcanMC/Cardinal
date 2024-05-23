package fr.arcanmc.cardinal.client.template;

import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.template.EnvironmentVariable;
import fr.arcanmc.cardinal.api.template.Template;
import fr.arcanmc.cardinal.core.console.Logger;
import lombok.Setter;

import java.io.File;
public class TemplateBuilder {

    private String name;

    private String prefix;

    private String imageId;

    private EnvironmentVariable environmentVariable;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setEnvironmentVariable(EnvironmentVariable environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    public Template build() {

        if (this.name == null || this.prefix == null || this.imageId == null) {
            throw new IllegalStateException("Name, prefix and imageId cannot be null");
        }

        Logger logger = Cardinal.getInstance().getLogger();

        File file = new File("templates/" + this.name);
        File contentFile = new File("templates/" + this.name + "/content");
        File dockerFile = new File("templates/" + this.name + "/Dockerfile");

        if (!file.exists() || !file.isDirectory()) {
            logger.error("Missing template folder!");
            return null;
        }
        if (!contentFile.exists() || !contentFile.isDirectory()) {
            logger.error("Missing content folder!");
            return null;
        }
        if (!dockerFile.exists() || !dockerFile.isFile()) {
            logger.error("Missing Dockerfile!");
            return null;
        }

        return new Template(name, prefix, imageId, environmentVariable);
    }

}

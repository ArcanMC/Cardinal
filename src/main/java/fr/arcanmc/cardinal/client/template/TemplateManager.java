package fr.arcanmc.cardinal.client.template;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import fr.arcanmc.cardinal.Cardinal;
import fr.arcanmc.cardinal.api.template.Template;
import fr.arcanmc.cardinal.core.console.Logger;
import fr.arcanmc.cardinal.core.docker.DockerAccess;
import fr.arcanmc.cardinal.utils.file.FileUtils;
import fr.arcanmc.cardinal.utils.file.Serialize;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TemplateManager {

    @Getter
    private final File templateFolder;

    @Getter
    private final TemplateData templateData;

    @Getter
    private final Serialize serialize;

    private final Logger logger = Cardinal.getInstance().getLogger();

    public TemplateManager() {

        this.serialize = new Serialize();

        this.templateFolder = new File("templates");
        this.templateFolder.mkdirs();

        String json = FileUtils.loadFile(new File("templates.json"));
        TemplateData templateData = (TemplateData) serialize.deserialize(json, TemplateData.class);

        if (templateData == null) {
            templateData = new TemplateData(new ArrayList<>(List.of(
                    new Template("default", "df", "", null)
            )));
        }
        this.templateData = templateData;
    }

    public void createTemplate(String name, String prefix) {
        logger.info("Creating template: " + name);

        if (this.templateData.getTemplates().stream().anyMatch(template -> template.getName().equals(name))) {
            logger.error("Template already exists");
            return;
        }

        String templateFolderPath = "templates/" + name;
        String dockerFilePath = templateFolderPath + "/Dockerfile";
        String serverFolderPath = templateFolderPath + "/content";
        String imageIdCardinal = name + ":cardinal";

        File templateFolder = new File(templateFolderPath);
        templateFolder.mkdirs();

        File dockerFile = new File(dockerFilePath);
        if (!dockerFile.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("client/Dockerfile")) {
                if (in == null) {
                    logger.error("Could not find Dockerfile");
                    return;
                }
                Files.copy(in, dockerFile.toPath());
            } catch (IOException e) {
                logger.error("Could not create Dockerfile");
            }
        }

        File serverFolder = new File(serverFolderPath);
        serverFolder.mkdirs();

        String imageId = createDockerImage(dockerFilePath, imageIdCardinal);

        TemplateBuilder templateBuilder = new TemplateBuilder();
        templateBuilder.setName(name);
        templateBuilder.setPrefix(prefix);
        templateBuilder.setImageId(imageId);
        this.templateData.getTemplates().add(templateBuilder.build());

        logger.info("Template created: " + name);
    }

    public void deleteTemplate(String name) {
        logger.info("Attempting to delete template " + name);
        if (getTemplateData().getTemplates().stream().anyMatch(template -> template.getName().equalsIgnoreCase(name))) {
            Template template = getTemplateData().getTemplates().stream().filter(values -> values.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
            if (template != null) {
                DockerAccess.get().getDockerClient().removeImageCmd(template.getImageId()).exec();
                FileUtils.deleteFile(new File(template.getName()));
                getTemplateData().getTemplates().remove(template);
                logger.info("Successfully deleted template: " + name);
            } else {
                logger.error("Something went wrong when deleting template..");
            }
        } else {
            logger.error("Any template found with this name..");
        }
    }

    public void updateTemplate(String name) {
        logger.info("Attempting to update template " + name);
        if (getTemplateData().getTemplates().stream().anyMatch(template -> template.getName().equalsIgnoreCase(name))) {
            Template template = getTemplateData().getTemplates().stream().filter(values -> values.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
            if (template != null) {
                String dockerFilePath = "templates/" + name + "/Dockerfile";
                String imageIdCardinal = name + ":cardinal";
                String imageId = createDockerImage(dockerFilePath, imageIdCardinal);
                template.setImageId(imageId);
                logger.info("Successfully updated template: " + name);
            } else {
                logger.error("Something went wrong when updating template..");
            }
        } else {
            logger.error("Any template found with this name..");
        }
    }

    public Template getTemplate(String name) {
        return this.templateData.getTemplates().stream().filter(template -> template.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean templateExists(String name) {
        return this.templateData.getTemplates().stream().anyMatch(template -> template.getName().equals(name));
    }

    public boolean templateHasImage(String name) {
        return this.templateData.getTemplates().stream().anyMatch(template -> template.getName().equals(name) && template.getImageId() != null);
    }

    public void save() {
        String json = getSerialize().serialize(templateData);
        FileUtils.saveFile(new File("templates.json"), json);
    }

    private String createDockerImage(String dockerFilePath, String imageName) {
        String imageId = "null";
        try (BuildImageCmd buildImageCmd = DockerAccess.get().getDockerClient().buildImageCmd()) {
            imageId = buildImageCmd
                    .withDockerfile(new File(dockerFilePath))
                    .withNoCache(Boolean.TRUE)
                    .withTags(Collections.singleton(imageName.toLowerCase(Locale.ROOT)))
                    .exec(new BuildImageResultCallback())
                    .awaitImageId();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Could not build Docker image");
        }
        return imageId;
    }

}

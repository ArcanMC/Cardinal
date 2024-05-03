package fr.arcanmc.cardinal.file;

import fr.arcanmc.cardinal.utils.YamlOrder;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileConfiguration {

    private Map<String, Object> mapping;
    private String header;

    public FileConfiguration(File file) throws IOException {
        if (file.exists()) {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            reloadConfig(reader);
            reader.close();
        } else {
            mapping = new LinkedHashMap<>();
        }
    }

    public FileConfiguration(InputStream input) {
        reloadConfig(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    public FileConfiguration(Reader reader) {
        reloadConfig(reader);
    }

    public FileConfiguration reloadConfig(File file) throws FileNotFoundException {
        return reloadConfig(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    }

    public FileConfiguration reloadConfig(InputStream input) {
        return reloadConfig(new InputStreamReader(input, StandardCharsets.UTF_8));
    }

    public FileConfiguration reloadConfig(Reader reader) {
        Yaml yml = new Yaml();
        mapping = yml.load(reader);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> returnType) {
        try {
            String[] tree = key.split("\\.");
            Map<String, Object> map = mapping;
            for (int i = 0; i < tree.length - 1; i++) {
                map = (Map<String, Object>) map.get(tree[i]);
            }
            if (returnType.equals(String.class)) {
                return (T) map.get(tree[tree.length - 1]).toString();
            }
            return returnType.cast(map.get(tree[tree.length - 1]));
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void set(String key, T value) {
        String[] tree = key.split("\\.");
        Map<String, Object> map = mapping;
        for (int i = 0; i < tree.length - 1; i++) {
            Map<String, Object> map1 = (Map<String, Object>) map.get(tree[i]);
            if (map1 == null) {
                map1 = new LinkedHashMap<>();
                map.put(tree[i], map1);
            }
            map = map1;
        }
        if (value != null) {
            map.put(tree[tree.length - 1], (T) value);
        } else {
            map.remove(tree[tree.length - 1]);
        }
    }

    private Yaml createYamlInstance() {
        DumperOptions options = createDumperOptions();
        Representer customRepresenter = createRepresenter();
        return new Yaml(customRepresenter, options);
    }

    private DumperOptions createDumperOptions()  {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return options;
    }

    private Representer createRepresenter() {
        DumperOptions options = createDumperOptions();
        Representer customRepresenter = new Representer(options);
        YamlOrder customProperty = new YamlOrder();
        customRepresenter.setPropertyUtils(customProperty);
        return customRepresenter;
    }

    private PrintWriter createConfiguredPrintWriter(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        if (header != null) {
            pw.println("#" + header.replace("\n", "\n#"));
        }
        return pw;
    }

    public String saveToString() throws IOException {
        Yaml yaml = createYamlInstance();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter pw = createConfiguredPrintWriter(out);

        yaml.dump(mapping, pw);

        pw.flush();
        pw.close();

        return out.toString(StandardCharsets.UTF_8);
    }

    public void saveConfig(File file) throws IOException {
        Yaml yaml = createYamlInstance();

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        PrintWriter pw = createConfiguredPrintWriter(new FileOutputStream(file));
        yaml.dump(mapping, pw);

        pw.flush();
        pw.close();
    }

}

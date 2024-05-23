package fr.arcanmc.cardinal.api.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    private String name;

    @Setter
    private String prefix;

    @Setter
    private String imageId;

    private EnvironmentVariable environmentVariable;

}

package br.com.neocamp.saldo.conta;

import br.com.neocamp.saldo.conta.dto.UsuarioJSONPlaceHolderDTO;
import br.com.neocamp.saldo.conta.dto.UsuarioTaskDTO;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
    private static final String JSON_PREFIX_FOLDER = "jsons/";
    public static UsuarioJSONPlaceHolderDTO getUsuarioJSONPlaceHolderDTO(int id) {
        return UsuarioJSONPlaceHolderDTO.builder()
                .id(id)
                .name("jose vitor" + id)
                .username("jv" + id)
                .email("email" + id)
                .phone("1234567-" + id)
                .website("www.jv" + id + ".com")
                .build();
    }

    public static UsuarioJSONPlaceHolderDTO[] getArrayOfUsuarioJSONPlaceHolderDTO() {
        UsuarioJSONPlaceHolderDTO[] usuarios = new UsuarioJSONPlaceHolderDTO[1];
        usuarios[0] = getUsuarioJSONPlaceHolderDTO(1);
        return usuarios;
    }

    public static UsuarioTaskDTO[] getArrayOfUsuarioTaskDTO() {
        UsuarioTaskDTO[] usuarios = new UsuarioTaskDTO[1];
        usuarios[0] = UsuarioTaskDTO.builder()
                        .nickName("joe")
                        .team("neocamp")
                        .build();
        return usuarios;
    }

    public static String getJsonFromResource(final String jsonName) throws IOException {
        URL resource = TestUtils.class.getClassLoader().getResource(JSON_PREFIX_FOLDER + jsonName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found!");
        } else {
            return new String(Files.readAllBytes(Paths.get(resource.getPath())));
        }
    }
}

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.lang.reflect.Modifier;

public class ConverterParaNovaClasse {

//    public static void main(String[] args) {
////        byte[] dadosBinarios = recuperarDoBancoDeDados(1); // Supondo que o ID do registro é 1
////
////        // Desserializa o objeto e converte se necessário
////        MinhaClasseAtualizada objetoAtualizado = desserializarObjeto(dadosBinarios);
////
////        // Exibe o objeto
////        System.out.println(objetoAtualizado);
////    }

    public static MinhaClasseAtualizada desserializarObjeto(byte[] dadosBinarios) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(dadosBinarios);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            // Desserializa diretamente como MinhaClasseAtualizada
            return (MinhaClasseAtualizada) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] recuperarDoBancoDeDados(int id) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String usuario = "postgres";
        String senha = "admin";

        String sql = "SELECT dados_binarios FROM tb_objetos_birario WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBytes("dados_binarios");
            } else {
                System.out.println("Nenhum dado encontrado com o ID fornecido.");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T desserializarObjeto(byte[] dadosBinarios, Class<T> clazz) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(dadosBinarios);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return clazz.cast(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao desserializar o objeto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static MinhaClasseAtualizada converterParaNovaClasse(MinhaClasseOriginalVI objetoOriginal) {
        // Converta os atributos do objetoOriginal para o novo objetoAtualizado
        MinhaClasseAtualizada objetoAtualizado = new MinhaClasseAtualizada();
        // Exemplo de conversão de atributos
        objetoAtualizado.setAtributo(objetoOriginal.getAtributo());
       // objetoAtualizado.setAtributo2(null);
        objetoAtualizado.setUsuario(objetoOriginal.getUsuario());
        // Adapte conforme necessário para novos atributos
        return objetoAtualizado;
    }



    public static MinhaClasseAtualizada converterParaNovaClasse2(MinhaClasseOriginalVI objetoOriginal) {
        MinhaClasseAtualizada objetoAtualizado = new MinhaClasseAtualizada();

        // Copia todos os campos da classe original para a nova classe
        try {
            // Obtém todos os campos da classe original
            Field[] camposOriginais = MinhaClasseOriginalVI.class.getDeclaredFields();
            for (Field campoOriginal : camposOriginais) {
                campoOriginal.setAccessible(true);
                Object valor = campoOriginal.get(objetoOriginal);

                // Obtém o campo correspondente na nova classe
                try {
                    Field campoAtualizado = MinhaClasseAtualizada.class.getDeclaredField(campoOriginal.getName());
                    campoAtualizado.setAccessible(true);

                    // Defina o valor do campo correspondente na nova classe
                    campoAtualizado.set(objetoAtualizado, valor);
                } catch (NoSuchFieldException e) {
                    // Campo não encontrado na nova classe - você pode optar por ignorar ou tratar aqui
                    System.out.println("Campo não encontrado na nova classe: " + campoOriginal.getName());
                }
            }

            // Inicializa campos novos se necessário
            // objetoAtualizado.setNovoAtributo(defaultValue);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return objetoAtualizado;
    }


            public static MinhaClasseAtualizada converterParaNovaClasse3(MinhaClasseOriginalVI objetoOriginal) {
            MinhaClasseAtualizada objetoAtualizado = new MinhaClasseAtualizada();

            try {
                // Obtém todos os campos da classe original
                Field[] camposOriginais = MinhaClasseOriginalVI.class.getDeclaredFields();
                for (Field campoOriginal : camposOriginais) {
                    campoOriginal.setAccessible(true);

                    // Copia o valor do campo se não for final
                    if (!Modifier.isFinal(campoOriginal.getModifiers())) {
                        Object valor = campoOriginal.get(objetoOriginal);

                        // Obtém o campo correspondente na nova classe
                        try {
                            Field campoAtualizado = MinhaClasseAtualizada.class.getDeclaredField(campoOriginal.getName());
                            campoAtualizado.setAccessible(true);

                            // Defina o valor do campo correspondente na nova classe
                            campoAtualizado.set(objetoAtualizado, valor);
                        } catch (NoSuchFieldException e) {
                            // Campo não encontrado na nova classe - você pode optar por ignorar ou tratar aqui
                            System.out.println("Campo não encontrado na nova classe: " + campoOriginal.getName());
                        }
                    } else {
                        // Lida com campos finais, que não podem ser modificados
                        System.out.println("Campo final não modificado: " + campoOriginal.getName());
                    }
                }

                // Inicializa campos novos se necessário
                // Exemplo: objetoAtualizado.setNovoAtributo(defaultValue);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return objetoAtualizado;
        }
    }


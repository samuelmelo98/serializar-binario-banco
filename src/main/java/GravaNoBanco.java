import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GravaNoBanco {

//    public static void main(String[] args) {
//        // Cria o objeto para ser serializado
//        MinhaClasseAtualizada objeto = new MinhaClasseAtualizada(42);
//
//        // Serializa o objeto
//        byte[] dadosBinarios = serializarObjeto(objeto);
//
//        // Grava o binário no banco de dados
//        gravarNoBancoDeDados(dadosBinarios, 1); // Supondo que o ID do registro é 1
//    }

    public static byte[] serializarObjeto(Serializable objeto) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(objeto);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void gravarNoBancoDeDados(byte[] dadosBinarios, int id) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String usuario = "postgres";
        String senha = "admin";

        String sql = "INSERT INTO tb_objetos_birario (id, dados_binarios) VALUES (?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET dados_binarios = EXCLUDED.dados_binarios;";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setBytes(2, dadosBinarios);
            stmt.executeUpdate();

            System.out.println("Objeto gravado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

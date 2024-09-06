import java.io.*;

public class MinhaClasseOriginalVI implements Serializable {
    private static final long serialVersionUID = 1L;

    private int atributo; // Tipo original
    private Usuario usuario;

    public MinhaClasseOriginalVI(int atributo, Usuario user) {
        this.atributo = atributo;
        this.usuario = user;
    }

    public MinhaClasseOriginalVI() {
    }

    public int getAtributo() {
        return atributo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "MinhaClasseOriginal{atributo=" + atributo + "}";
    }

    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeInt(atributo);
        usuario.writeData(dos);
    }

    public  MinhaClasseOriginalVI readData(DataInputStream dis) throws IOException {
        MinhaClasseOriginalVI obj = new MinhaClasseOriginalVI();
        obj.atributo = dis.readInt();
        obj.usuario = usuario.readData(dis);
        return obj;
    }

    // Serializa o objeto manualmente
    public byte[] serializar() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            OutputStream os = baos;
            os.write(intToBytes(atributo)); // Grava o int
          //  os.write(usuario.getBytes("UTF-8")); // Grava a String

            // Serializa o objeto Endereco
            byte[] usuarioBytes = usuario.serializar();
            os.write(intToBytes(usuarioBytes.length)); // Grava o comprimento do Endereco
            os.write(usuarioBytes); // Grava o objeto Endereco
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Métodos auxiliares
    private static byte[] intToBytes(int valor) {
        return new byte[]{
                (byte) (valor >>> 24),
                (byte) (valor >>> 16),
                (byte) (valor >>> 8),
                (byte) valor
        };
    }

    private static int bytesToInt(byte[] bytes) {
        return (bytes[0] << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    private static byte[] readBytes(InputStream is, int length) throws IOException {
        byte[] buffer = new byte[length];
        int bytesRead = 0;
        while (bytesRead < length) {
            int result = is.read(buffer, bytesRead, length - bytesRead);
            if (result == -1) throw new IOException("End of stream reached before reading all bytes");
            bytesRead += result;
        }
        return buffer;
    }
}

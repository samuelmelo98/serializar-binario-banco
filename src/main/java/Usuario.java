import java.io.*;

public class Usuario implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int id;

    public Usuario() {
    }

    public Usuario(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                '}';
    }

    /**
     * Escreve
     * @param dos
     * @throws IOException
     */
    public void writeData(DataOutputStream dos) throws IOException {
        dos.writeInt(id);
    }

    /**
     * LER
     * @param dis
     * @return
     * @throws IOException
     */
    public  Usuario readData(DataInputStream dis) throws IOException {
        Usuario obj = new Usuario();
        obj.id = dis.readInt();
        return obj;
    }

    // Serializa o objeto manualmente
    public byte[] serializar() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            OutputStream os = baos;
           // os.write(id.getBytes("UTF-8")); // Grava a String
            os.write(intToBytes(id)); // Grava o int
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

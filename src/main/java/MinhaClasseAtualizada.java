import java.io.*;

public class MinhaClasseAtualizada implements Serializable {
    private static final long serialVersionUID = 2L;

    private Integer atributo; // Novo tipo
    private int atributo2;
    private Usuario usuario;

    // Construtor para criar a nova classe a partir da versão antiga
    public MinhaClasseAtualizada(int atributo, Usuario user) {
        this.atributo = atributo;
        this.usuario = user;
    }

    // Construtor padrão
    public MinhaClasseAtualizada() {
        // Necessário para a desserialização
    }

    // Método personalizado para desserializar a classe antiga
    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Tenta ler o atributo como `int`
        try {
            atributo = ois.readInt();
        } catch (EOFException e) {
            // Caso não tenha o atributo, atribua null ou valor padrão
            atributo = null;
        }
    }

    // Método para serializar o novo tipo
    private void writeObject(ObjectOutputStream oos) throws IOException {
        // Serializa o atributo
        if (atributo != null) {
            oos.writeInt(atributo);
        } else {
            // Caso o atributo seja null, trata conforme necessário
            oos.writeInt(0); // Ou trate como quiser
        }
    }

    @Override
    public String toString() {
        return "MinhaClasseAtualizada{atributo=" + atributo + "atributo2=" + atributo2 + "usuario=" + usuario + "}";
    }

    public Integer getAtributo() {
        return atributo;
    }

    public void setAtributo(Integer atributo) {
        this.atributo = atributo;
    }

    public int getAtributo2() {
        return atributo2;
    }

    public void setAtributo2(int atributo2) {
        this.atributo2 = atributo2;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

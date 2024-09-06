import javax.swing.text.html.MinimalHTMLWriter;

public class Main {
    public static void main(String[] args) {


        try{
            /**
             * Serializa o objeto e grava o binario no banco.
             */
            Usuario usuario = new Usuario(100);
   //         MinhaClasseOriginal minhaClasseOriginal = new MinhaClasseOriginal(10,usuario);

    //        byte[] dadosBinarios = GravaNoBanco.serializarObjeto(minhaClasseOriginal);

     //       GravaNoBanco.gravarNoBancoDeDados(dadosBinarios,1);
            MinhaClasseOriginalVI minhaClasseOriginalVI = new MinhaClasseOriginalVI(10,usuario);
          byte[] dadosBinarios =  minhaClasseOriginalVI.serializar();
            GravaNoBanco.gravarNoBancoDeDados(dadosBinarios,2);



            /**
             * Recupera o objeto antigo e coloca no novo objeto desserializa
             */
            byte[] binarioObjetoAntigo = ConverterParaNovaClasse.recuperarDoBancoDeDados(2);
            /** não funcionou
             *  MinhaClasseAtualizada  minhaClasseAtualizada = ConverterParaNovaClasse.desserializarObjeto(binarioObjetoAntigo);
             */
            MinhaClasseOriginalVI minhaClasseOriginal = ConverterParaNovaClasse.desserializarObjeto(binarioObjetoAntigo,MinhaClasseOriginalVI.class);
            System.out.println("Objeto original já serializado em banco." + minhaClasseOriginal);
            MinhaClasseAtualizada objetoAtualizado = ConverterParaNovaClasse.converterParaNovaClasse3(minhaClasseOriginal);
            System.out.println("Objeto da nova classe com novo atributos." + objetoAtualizado);


        } catch (Throwable e){
            e.printStackTrace();
        }

    }
}

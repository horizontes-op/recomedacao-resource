package insper.store.recomendacao;
import insper.store.aluno.*;
import insper.store.instituicao.InstituicaoOut;
import insper.store.instituicao.InstituicaoController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import insper.store.aluno.AlunoController;
import java.util.List;
@Service
public class RecomendacaoService {
    // }
    @Autowired
    private AlunoController alunoController;
    @Autowired
    private InstituicaoController instituicaoController;

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    public List<Recomendacao> create(Recomendacao in) {
        ResponseEntity<List<InstituicaoOut>> instituicoes = instituicaoController.readAll();
        System.out.println(instituicoes.toString());
        ResponseEntity<AlunoOut> aluno = alunoController.read(in.id_aluno());
        AlunoOut alunoOut = aluno.getBody();

        List<InstituicaoOut> instituicaoOut = instituicoes.getBody();
        for (InstituicaoOut instituicao : instituicaoOut) {
            
        }
        System.out.println(aluno.toString());
                // MediaType mediaType = MediaType.parse("application/json");
                String prompt = "Tenho um aluno com essas caracteristicas: " + 
                "nome: "+ alunoOut.nome().toString() + ", Areas de interesse: " +alunoOut.areas_interesse() + 
               ", descricao: " + alunoOut.descricao() + ", ocupacao: " + alunoOut.ocupacao() +  
               ", genero: " + alunoOut.genero().toString() + 
                "renda per capita: " + alunoOut.renda_per_capita().toString()+ 
                "e gostaria de através dessas informações recomendar "+
                "instituições de ensino para ele. Poderia me ajudar? As instituicoes são essas: " + 
                instituicaoOut.toString() +   
                " devolva as universidades que dão match com ele. Me retone uma lista de "+ 
                "objetos com os campos  id_instituicao e motivo. Não me retorne nada além disso";
        ChatGptClient chatGptClient = new ChatGptClient();
        List<Recomendacao> response = chatGptClient.enviarRequisicaoParaChatGpt(prompt, in.id_aluno());
        for (Recomendacao recomendacao : response) {
            recomendacaoRepository.save(new RecomendacaoModel(recomendacao));
        }
        for (Recomendacao recomendacaoModel : response) {   
            System.out.println(recomendacaoModel);
        }
        return response;

    //     Recomendacao recomendacao = Recomendacao.builder()
    //         .id_aluno(aluno.getBody().id())
    //         .nome(aluno.getBody().nome())
    //         .id_instituicao(instituicaoOut.get(0).id())
    //         .area_atuacao(instituicaoOut.get(0).area_atuacao())
    //         .build();

    //     System.out.println(prompt);
    //    return recomendacao;


        
        
    }

    // public Aluno read(@NonNull String id) {
    //     return recomendacaoRepository.findById(id).map(RecomendacaoModel::to).orElse(null);
    // }
    
}

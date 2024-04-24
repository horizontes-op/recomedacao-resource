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
    
    @Autowired
    private AlunoController alunoController;
    @Autowired
    private InstituicaoController instituicaoController;

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    public List<Recomendacao> create(Recomendacao in) {
        ResponseEntity<List<InstituicaoOut>> instituicoes = instituicaoController.readAll();
        ResponseEntity<AlunoOut> aluno = alunoController.read(in.id_aluno());
        AlunoOut alunoOut = aluno.getBody();
        List<InstituicaoOut> instituicaoOut = instituicoes.getBody();

        String prompt = buildPrompt(alunoOut, instituicaoOut);

        ChatGptClient chatGptClient = new ChatGptClient();
        List<Recomendacao> response = chatGptClient.enviarRequisicaoParaChatGpt(prompt, in.id_aluno());
        for (Recomendacao recomendacao : response) {
            recomendacaoRepository.save(new RecomendacaoModel(recomendacao));
        }
        return response;
    }

    private String safelyGetString(String value) {
        return value != null ? value : "Desconhecido";
    }
    
    private String buildPrompt(AlunoOut alunoOut, List<InstituicaoOut> instituicaoOut) {
        return "Tenho um aluno com as seguintes características: " +
            "nome: " + safelyGetString(alunoOut.nome()) + 
            ", sobrenome: " + safelyGetString(alunoOut.sobrenome()) +
            ", email: " + safelyGetString(alunoOut.email()) +
            ", gênero: " + safelyGetString(alunoOut.genero()) +
            ", data de nascimento: " + (alunoOut.dataNascimento() != null ? alunoOut.dataNascimento().toString() : "Desconhecido") +
            ", CEP: " + safelyGetString(alunoOut.cep()) +
            ", escolaridade: " + safelyGetString(alunoOut.escolaridade()) +
            ", ocupação: " + safelyGetString(alunoOut.ocupacao()) +
            ", estudou em: " + safelyGetString(alunoOut.estudouEm()) +
            ", renda per capita: " + (alunoOut.renda_per_capita() != null ? alunoOut.renda_per_capita().toString() : "Desconhecido") +
            ", turno disponível: " + safelyGetString(alunoOut.turno_disponivel()) +
            ", disponibilidade de deslocamento: " + safelyGetString(alunoOut.disponibilidade_de_deslocamento()) +
            ", modalidade do ensino: " + safelyGetString(alunoOut.modalidade_do_ensino()) +
            ", áreas de interesse: " + safelyGetString(alunoOut.areas_interesse()) +
            ", tipo de oportunidade: " + safelyGetString(alunoOut.tipo_oportunidade()) +
            ", natureza da instituição preferida: " + safelyGetString(alunoOut.natureza_instituicao()) +
            ", descrição: " + safelyGetString(alunoOut.descricao()) +
            " e gostaria de usar essas informações para recomendar instituições de ensino para ele. As instituições são: " +
            instituicaoOut.toString() + ". Por favor, devolva as universidades que dão match com ele. Me retorne uma lista de " +
            "objetos com os campos id_instituicao e motivo. Não me retorne nada além disso.";
    }
}



// prompt antigo
// String prompt = "Tenho um aluno com essas caracteristicas: " + 
// "nome: "+ alunoOut.nome().toString() + ", Areas de interesse: " +alunoOut.areas_interesse() + 
// ", descricao: " + alunoOut.descricao() + ", ocupacao: " + alunoOut.ocupacao() +  
// ", genero: " + alunoOut.genero().toString() + 
// "renda per capita: " + alunoOut.renda_per_capita().toString()+ 
// " e gostaria de através dessas informações recomendar "+
// "instituições de ensino para ele. Poderia me ajudar? As instituicoes são essas: " + 
// instituicaoOut.toString() +   
// " devolva as universidades que dão match com ele. Me retone uma lista de "+ 
// "objetos com os campos  id_instituicao e motivo. Não me retorne nada além disso";
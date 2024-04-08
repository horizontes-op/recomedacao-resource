package insper.store.recomendacao;

public class RecomendacaoParser {

    public static Recomendacao to(RecomendacaoIn in) {
        return Recomendacao.builder()
            .id_aluno(in.id_aluno())
            .build();
    }

    public static RecomendacaoOut to(Recomendacao recomendacao) {
        return RecomendacaoOut.builder()
            
            .id(recomendacao.id())
            .id_aluno(recomendacao.id_aluno())
            .id_instituicao(recomendacao.id_instituicao())
            .motivo(recomendacao.motivo())
            .area_atuacao(null)
            
            .build();
    }
    
}

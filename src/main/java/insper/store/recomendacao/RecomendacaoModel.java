package insper.store.recomendacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.sql.Date;

import insper.store.aluno.AlunoOut;
import insper.store.instituicao.InstituicaoOut;

@Entity
@Table(name = "recomendacao")
@EqualsAndHashCode(of = "id")
@Builder @Getter @Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class RecomendacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "id_aluno")
    private String id_aluno;

    @Column(name = "id_instituicao")
    private String id_instituicao;

    @Column(name = "motivo")
    private String motivo;


    public RecomendacaoModel(Recomendacao o) {
        this.id = o.id();
        // this.id_aluno = o.id_aluno();
        this.id_instituicao = o.id_instituicao();
        this.motivo = o.motivo();
    }

    public Recomendacao to() {
        return Recomendacao.builder()
            .id(id)
            .id_aluno(id_aluno)
            .id_instituicao(id_instituicao)
            .motivo(motivo)
            .build();
    }


}

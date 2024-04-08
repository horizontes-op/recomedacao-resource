package insper.store.recomendacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.sql.Date;

@Builder
@Getter @Setter @Accessors(fluent = true, chain = true)
@AllArgsConstructor @NoArgsConstructor
public class Recomendacao {
    private String id;
    private String id_aluno;
    private String id_instituicao;
    private String motivo;
    private String nome;
    private String area_atuacao;
    private String email;
    private String telefone;
    private String numero_endereco;
    private String site;
    private String cep;
    private String perfil_instagram;
    private String perfil_facebook;
    private String perfil_linkedin;
    private String perfil_twitter;
    private String descricao;
    private String misssao;
    private String visao;
    private String valores;
}

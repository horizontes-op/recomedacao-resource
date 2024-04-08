package insper.store.recomendacao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendacaoRepository extends CrudRepository<RecomendacaoModel, String> {
    
}

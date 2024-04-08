package insper.store.recomendacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {
    "insper.store.aluno",
    "insper.store.instituicao"
})
@EnableDiscoveryClient
@SpringBootApplication
public class RecomendacaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecomendacaoApplication.class, args);
    }   

}

package insper.store.recomendacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

import insper.store.aluno.AlunoOut;
import java.util.ArrayList;
import java.lang.reflect.Type;
public class ChatGptClient {
    private static final String OPENAI_API_KEY = Config.getOpenaiApiKey();

    public static List<Recomendacao> parseResponse(String jsonResponse, String idAluno) {
        Gson gson = new Gson(); 
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
    
        // Extrair a primeira escolha (resposta) da lista de escolhas
        JsonArray choices = jsonObject.getAsJsonArray("choices");
        String choice = choices.get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString(); // Obter como String, não como objeto JSON
        System.out.println(choice);
       
        Type listType = new TypeToken<List<Recomendacao>>(){}.getType();
        List<Recomendacao> recomendacoes = gson.fromJson(choice, listType);
        for (Recomendacao recomendacao : recomendacoes) {
            recomendacao.id_aluno(idAluno);
            System.out.println(recomendacao.id_instituicao());
            System.out.println(recomendacao.motivo());
        }   
        
        return recomendacoes;
        
        // ObjectMapper objectMapper = new ObjectMapper();
        // try {
        //     List<Recomendacao> lista = objectMapper.readValue(choice, new TypeReference<List<Recomendacao>>(){});
    
        //     for (Recomendacao recomendacao : lista) {
        //         RecomendacaoModel recomendacaomodel = new RecomendacaoModel(recomendacao);
        //         recomendacoes.add(recomendacaomodel);
        //     }
        // } catch (JsonProcessingException e) {
        //     e.printStackTrace();
        // }
    
        // Retornar a lista de recomendações
        
    }
    
    
    public List<Recomendacao>  enviarRequisicaoParaChatGpt(String prompt, String idAluno) {
        // URL da API do ChatGPT
        String url = "https://api.openai.com/v1/chat/completions";

        // Cabeçalhos da requisição
        System.out.println(OPENAI_API_KEY);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "); // Substitua YOUR_API_KEY pelo seu token de API do OpenAI

        // Corpo da requisição
        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"Você vai gerar um padrão de correspondência entre personas e instituições de ensino para um projeto de educação. Cada correspondência deve conter uma persona e uma instituição. Retorne as correspondecias com uma lista de JSON sem quebra de linha\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
        // Criar uma entidade de requisição
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        System.out.println("Enviando requisição para a API do ChatGPT: " + requestBody);
        // Criar o RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Enviar a requisição para a API do ChatGPT
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // formate a resposta para retornar uma lista de objetos

        
    List<Recomendacao> response =  parseResponse(responseEntity.getBody().toString(), idAluno );
    // for (RecomendacaoModel recomendacaoModel : response) {
    //     System.out.println(recomendacaoModel);
    // }
        // Processar a resposta
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String resposta = responseEntity.getBody();
            System.out.println("Resposta da API do ChatGPT: " + resposta);
           
        } else {
            System.err.println("Falha ao enviar a requisição para a API do ChatGPT. Código de status: " + responseEntity.getStatusCodeValue());
        }
        return response;
    }

    public static void main(String[] args) {
        
        String prompt = "Tenho um aluno com essas caracteristicas: " + 
                "nome: Fernando Santos, area de interesse: Tecnologia, Inovação, descricao:  ser um profissional com experiência em desenvolvimento web e interesse em aprender novas tecnologias. ocupacao: aluno, "+
               ", genero: masculino" +
                "renda per capita: 2800 " + 
                "e gostaria de através dessas informações recomendar "+
                "instituições de ensino para ele. Poderia me ajudar? As instituicoes são essas: " + 
                "Faculdade abc, usp, unicamp, ita" +
                " devolva as universidades que dão match com ele. Me retone uma lista de "+ 
                "objetos com os campos  id_instituicao e motivo. Não me retorne nada além disso. Me devolva tudo em uma linha.";
        // ChatGptClient chatGptClient = new ChatGptClient();
        // AlunoOut aluno = new AlunoOut("1", "jose", null , null, "masculino","ciencias",null,null,null);
        // chatGptClient.enviarRequisicaoParaChatGpt(prompt, "83285703295-923-5");
        String jsonResponse = "{\n" +
                "  \"id\": \"chatcmpl-9BmfTw2DzgE9crROVj4iCDTu9nofI\",\n" +
                "  \"object\": \"chat.completion\",\n" +
                "  \"created\": 1712595715,\n" +
                "  \"model\": \"gpt-3.5-turbo-0125\",\n" +
                "  \"choices\": [\n" +
                "    {\n" +
                "      \"index\": 0,\n" +
                "      \"message\": {\n" +
                "        \"role\": \"assistant\",\n" +
                "        \"content\": \"[{\\\"id_instituicao\\\": \\\"42901ni1o29u401\\\", \\\"motivo\\\": \\\"Área de interesse em Tecnologia e Inovação\\\"}, {\\\"id_instituicao\\\": \\\"210941290849128-41\\\", \\\"motivo\\\": \\\"Interesse em aprender novas tecnologias\\\"}, {\\\"id_instituicao\\\": \\\"91249128-412-2\\\", \\\"motivo\\\": \\\"Experiência em desenvolvimento web\\\"}]\"\n" +
                "      },\n" +
                "      \"logprobs\": null,\n" +
                "      \"finish_reason\": \"stop\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"usage\": {\n" +
                "    \"prompt_tokens\": 236,\n" +
                "    \"completion_tokens\": 80,\n" +
                "    \"total_tokens\": 316\n" +
                "  },\n" +
                "  \"system_fingerprint\": \"fp_b28b39ffa8\"\n" +
                "}";
        String idAluno = "83285703295-923-5";

        parseResponse(jsonResponse, idAluno);
    
    }

}
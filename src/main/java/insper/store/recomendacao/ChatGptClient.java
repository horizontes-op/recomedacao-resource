package insper.store.recomendacao;

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
   

    public static List<Recomendacao> parseResponse(String jsonResponse, String idAluno) {
        Gson gson = new Gson(); 
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray choices = jsonObject.getAsJsonArray("choices");
        String choice = choices.get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString(); // Obter como String, não como objeto JSON
        Type listType = new TypeToken<List<Recomendacao>>(){}.getType();
        List<Recomendacao> recomendacoes = gson.fromJson(choice, listType);
        for (Recomendacao recomendacao : recomendacoes) {
            recomendacao.id_aluno(idAluno);
        }   
        return recomendacoes;
    }
    
    
    public List<Recomendacao>  enviarRequisicaoParaChatGpt(String prompt, String idAluno) {
        // URL da API do ChatGPT
        String url = "https://api.openai.com/v1/chat/completions";
     
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = System.getenv("OPENAI_API_KEY");
        headers.set("Authorization", "Bearer " + token); 
        // Corpo da requisição
        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"Você vai gerar um padrão de correspondência entre personas e instituições de ensino para um projeto de educação. Cada correspondência deve conter uma persona e uma instituição. Retorne as correspondecias com uma lista de JSON sem quebra de linha\"}, {\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
        // Criar uma entidade de requisição
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        // Criar o RestTemplate
        
        RestTemplate restTemplate = new RestTemplate();

        // Enviar a requisição para a API do ChatGPT
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        // formate a resposta para retornar uma lista de objetos
        List<Recomendacao> response =  parseResponse(responseEntity.getBody().toString(), idAluno );
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String resposta = responseEntity.getBody();
            } else {
                System.err.println("Falha ao enviar a requisição para a API do ChatGPT. Código de status: " + responseEntity.getStatusCodeValue());
                throw new RuntimeException("Falha ao enviar a requisição para a API do ChatGPT");
            }
            return response;
        }
    }
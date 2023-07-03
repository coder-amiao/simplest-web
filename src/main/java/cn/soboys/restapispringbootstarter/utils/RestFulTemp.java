package cn.soboys.restapispringbootstarter.utils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/2 10:23
 * @webSite https://github.com/coder-amiao
 */
@Component
public class RestFulTemp {

    @Resource
    private RestTemplate restTemplate;

    public ResponseEntity<String> doGet(String url) {
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        return response;
    }

    public ResponseEntity<String> doPost(String url, Object jsonData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, requestEntity, String.class);
        return response;
    }

    public ResponseEntity<String> doPost(String url, String jsonStr) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(jsonStr, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response;
    }

    public ResponseEntity<String> doPost(String url, Map<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public String sendFilePost(String url, File file) throws IOException {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(file);
        map.add("file", resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);

        return restTemplate.postForObject(url, httpEntity, String.class);
    }

    public String doPut(String url, Object jsonData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public String doDelete(String url) {
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
        return responseEntity.getBody();
    }
}

package ru.ruthen.resttemplate;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.ruthen.resttemplate.model.User;

public class SimpleGetExample {
    static final String URL_USERS = "http://91.241.64.178:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();
    static String code = "";

    public static void main(String[] args) {
        String cookie = getUsers();
        code += createUser(cookie);
        code += updateUser(cookie);
        code += deleteUser(cookie);
    }
    private static String getUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL_USERS,
                HttpMethod.GET, entity, String.class);
        String cookie = response.getHeaders().getFirst("Set-Cookie");
        return cookie;

    }
    private static String createUser(String cookie) {
        User user = new User(3L,"James", "Brown", (byte) 29);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL_USERS,
                HttpMethod.POST,request,String.class);
        return response.getBody();
    }
    private static String updateUser(String cookie) {
        User updateUser = new User(3L,"Thomas","Shelby", (byte) 29);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookie);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<User> request = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL_USERS,
                HttpMethod.PUT,request, String.class);
        return response.getBody();

    }
    private static String deleteUser(String cookie) {
        int id = 3;
        String url = URL_USERS + "/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.DELETE,request,String.class);
        return response.getBody();
    }
}

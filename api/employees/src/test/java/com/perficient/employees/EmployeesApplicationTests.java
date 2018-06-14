package com.perficient.employees;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeesApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/hello", String.class);
        assertThat(body).isEqualTo("Hello there");
    }

    @Test
    public void CreateTest() throws Exception {
        JSONObject request = new JSONObject();
        request.put("first_name", "bobby");
        request.put("last_name", "sample");
        request.put("birth_date", "1997-08-29");
        request.put("gender", "M");
        request.put("hire_date", "2001-01-01");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
        ResponseEntity<String> loginResponse = restTemplate
                .postForEntity("/employees", entity, String.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}

package com.codeJ.JPA.Test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.codeJ.JPA.ComUtil.JPADevConfigClass;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= {JPADevConfigClass.class})
public class DbCoreApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void test() {
		ResponseEntity<ModelAndView> reponse=restTemplate.getForEntity("/reaAlldData", ModelAndView.class);
	}

}

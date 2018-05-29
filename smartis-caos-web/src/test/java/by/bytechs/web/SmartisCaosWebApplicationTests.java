package by.bytechs.web;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.service.TerminalService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartisCaosWebApplicationTests {
	@Autowired
	private TerminalService terminalService;

	@Test
	public void contextLoads() {
	}

	@Ignore
	@Test
	public void testController() {
		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		String url = "http://172.31.251.174:7004/caos/terminals";
		System.out.println("url: " + url);
		ResponseEntity<TerminalDTO> responseEntity = template.exchange(url, HttpMethod.GET, entity, TerminalDTO.class);
		TerminalDTO dto = responseEntity.getBody();
		Assert.assertNotNull(dto);
		Assert.assertTrue(dto.getTerminalID().equals("CDS02457"));
		Assert.assertTrue(dto.getLogicalName().equals("ДМ 02457"));
	}

}

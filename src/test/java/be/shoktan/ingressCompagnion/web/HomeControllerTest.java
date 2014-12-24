package be.shoktan.ingressCompagnion.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

public class HomeControllerTest {
	@Test
	public void shouldShowHomePage() throws Exception {
		HomeController control = new HomeController();
		
		MockMvc mockMvc = standaloneSetup(control).build();
		
		mockMvc.perform(get("/"))
			.andExpect(view().name("home"));
	}
}

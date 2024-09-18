package KorberSupply.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import KorberSupply.PruebaTecnica.PruebaTecnicaApplication;

@SpringBootTest(classes = PruebaTecnicaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ControllersTestConfig.class)
public class ControllersDeleteTest {
	
	@Autowired
	private TestsFixture _fixture;
	
    private MockMvc _mockMvc;
	
	@LocalServerPort
    private int port;
	
	private URI url;
	
	@BeforeEach
	private void TestSetup() {
	    url = _fixture.GetControllerUrl(port);
	   
	    _mockMvc = _fixture.BuildMockMvc();
	    
	    _fixture.InstantiateArticlesInDb(1);
	}
	
	@Test
	public void DeleteArticleById() throws Exception {
		final int expectedArticleId = 0;
		
		//Ensure that there is an article to be deleted
		assertEquals(1, _fixture.GetAllArticlesFromDb().size());
		
		_mockMvc.perform(delete(url.toString() + "/" + expectedArticleId)
				.contentType("application/json"))
        		.andExpect(status().isOk());
		
		assertEquals(0, _fixture.GetAllArticlesFromDb().size());
	}
	

	@Test
	public void DeleteArticleById_NonexistantId_ThrowsControlledError400() throws Exception {
		final int expectedArticleId = 24;

		TestsFixture.CleanDb();
		
		//Ensure that there is no articles to be deleted
		assertEquals(0, _fixture.GetAllArticlesFromDb().size());
		
		_mockMvc.perform(delete(url.toString() + "/" + expectedArticleId)
				.contentType("application/json"))
        		.andExpect(status().is4xxClientError());
		
		assertEquals(0, _fixture.GetAllArticlesFromDb().size());
	}
	
	@AfterAll
	public static void CleanDb() {
		TestsFixture.CleanDb();
	}
}
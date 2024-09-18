package KorberSupply.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import KorberSupply.PruebaTecnica.PruebaTecnicaApplication;
import KorberSupply.PruebaTecnica.Dtos.ArticleCreateRequestDto;
import KorberSupply.PruebaTecnica.POJO.Article;

@SpringBootTest(classes = PruebaTecnicaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ControllersTestConfig.class)
public class ControllersCreateTest {
	
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

	}
	
	@Test
	public void PostArticle() throws Exception {
		final String expectedDescription = "Description of expected string";
		final double expectedWeight = 2.5;
		final double expectedVolume = 5;
		final boolean isActive = true;
		
		ArticleCreateRequestDto createRequest = _fixture.BuildArticleCreateRequest(
				expectedDescription, 
				expectedWeight, 
				expectedVolume, 
				isActive);
		
		_mockMvc.perform(post(url.toString())
                .contentType("application/json")
                .content(_fixture.ConvertToJsonString(createRequest)))
                .andExpect(status().isOk());
		
		Article articleInserted = _fixture.GetAllArticlesFromDb().get(0);
		
		assertEquals(1, _fixture.GetAllArticlesFromDb().size());
		assertEquals(expectedDescription, articleInserted.getDescription());
		assertEquals(expectedWeight, articleInserted.getWeight());
		assertEquals(expectedVolume, articleInserted.getVolume());
		assertEquals(isActive, articleInserted.isActive());
		
	}
	
	@Test
	public void PostArticle_EmptyValues_Throws500() throws Exception {
		
		ArticleCreateRequestDto createRequest = new ArticleCreateRequestDto();
		
		createRequest.setDescription("Description of expected string");
		createRequest.setWeight(2.5);
		
		_mockMvc.perform(post(url.toString())
                .contentType("application/json")
                .content(_fixture.ConvertToJsonString(createRequest)))
                .andExpect(status().is5xxServerError());
		
		ArrayList<Article> articleInserted = _fixture.GetAllArticlesFromDb();

		assertEquals(0, articleInserted.size());
	}
	
	@Test
	public void PostArticle_EmptyRequest_Throws500() throws Exception {
		ArticleCreateRequestDto createRequest = new ArticleCreateRequestDto();
		
		_mockMvc.perform(post(url.toString())
                .contentType("application/json")
                .content(_fixture.ConvertToJsonString(createRequest)))
                .andExpect(status().is5xxServerError());
		
		ArrayList<Article> articleInserted = _fixture.GetAllArticlesFromDb();

		assertEquals(0, articleInserted.size());
	}
	
	@AfterAll
	public static void CleanDb() {
		TestsFixture.CleanDb();
	}
}
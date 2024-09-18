package KorberSupply.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import KorberSupply.PruebaTecnica.PruebaTecnicaApplication;
import KorberSupply.PruebaTecnica.POJO.Article;

@SpringBootTest(classes = PruebaTecnicaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ControllersTestConfig.class)
public class ControllersReadTest {
	
	@Autowired
	private TestsFixture _fixture;
	
    private MockMvc _mockMvc;
	
	@LocalServerPort
    private int port;
	
	private URI url;
	
	@BeforeEach
	public void TestSetup() {
	    url = _fixture.GetControllerUrl(port);
	   
	    _mockMvc = _fixture.BuildMockMvc();
	    
	    _fixture.InstantiateArticlesInDb(); //Default creates 3
	}
	
	@Test
	public void GetArticleById() throws Exception {
		final int expectedArticleId = 2; //Checks the length of the insert getting the last one
		
		MvcResult result = _mockMvc.perform(get(url.toString() + "/" + expectedArticleId)
				.contentType("application/json"))
        		.andExpect(status().isOk())
        		.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
        Article returnedArticle = _fixture.MapJsonToArticle(jsonResponse);

		_fixture.AssertDefaultArticle(returnedArticle);
	}
	
	@Test
	public void GetArticleById_NonExistentId_ThrowsControlledError400() throws Exception {
		final int expectedNonExistentArticleId = 34; //Checks the length of the insert getting the last one
		ArrayList<Article> currentArticlesInDb = _fixture.GetAllArticlesFromDb();
		
		assertEquals(3, currentArticlesInDb.size());
		
		_mockMvc.perform(get(url.toString() + "/" + expectedNonExistentArticleId)
				.contentType("application/json"))
				.andExpect(status().is4xxClientError());
		
		for (Article article : currentArticlesInDb) {
			assertNotEquals(expectedNonExistentArticleId, article.getId());
		}
	}
	
	@Test
	public void GetAllArticles() throws Exception {
		MvcResult result = _mockMvc.perform(get(url.toString())
				.contentType("application/json"))
        		.andExpect(status().isOk())
        		.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
        ArrayList<Article> returnedArticles = _fixture.MapJsonToArticleArrayList(jsonResponse);

		_fixture.AssertDefaultArticlesCollection(returnedArticles);
	}
	
	@Test
	public void GetAllArticles_NoArticles_EmptyArray() throws Exception {
		TestsFixture.CleanDb();
		
		MvcResult result = _mockMvc.perform(get(url.toString())
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		ArrayList<Article> returnedArticles = _fixture.MapJsonToArticleArrayList(jsonResponse);
		
		assertEquals(0, returnedArticles.size());
	}
	
	@AfterEach
	private void Cleanup() {
		TestsFixture.CleanDb();
	}
}
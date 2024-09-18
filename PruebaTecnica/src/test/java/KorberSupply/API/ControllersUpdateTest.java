package KorberSupply.API;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import KorberSupply.PruebaTecnica.Dtos.ArticleUpdateRequestDto;
import KorberSupply.PruebaTecnica.POJO.Article;

@SpringBootTest(classes = PruebaTecnicaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ControllersTestConfig.class)
public class ControllersUpdateTest {
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
	    
	    _fixture.InstantiateArticlesInDb(1);
	}
	
	@Test
	public void UpdateArticleById() throws Exception {
		final int expectedArticleId = 0;
		final String updatedDescription = "Hey, a new description!";
		final double updatedWeight = 3.14;
		final double updatedVolume = 12.358;
		final boolean updatedIsActive = false;
		
		ArrayList<Article> currentArticles =_fixture.GetAllArticlesFromDb();
		
		assertEquals(1, currentArticles.size());
		
		assertArticleNotEquals(currentArticles.get(expectedArticleId), 
				updatedDescription, 
				updatedWeight, 
				updatedVolume, 
				updatedIsActive);
		
		ArticleUpdateRequestDto updateRequest = BuildUpdateRequest(expectedArticleId, 
				updatedDescription, 
				updatedWeight, 
				updatedVolume, 
				updatedIsActive);
		
		MvcResult result = _mockMvc.perform(patch(url.toString())
                .contentType("application/json")
                .content(_fixture.ConvertToJsonString(updateRequest)))
                .andExpect(status().isOk())
                .andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
        Article returnedArticle = _fixture.MapJsonToArticle(jsonResponse);
		
        assertEquals(expectedArticleId, returnedArticle.getId());
        assertEquals(updatedDescription, returnedArticle.getDescription());
        assertEquals(updatedWeight, returnedArticle.getWeight());
        assertEquals(updatedVolume, returnedArticle.getVolume());
        assertEquals(updatedIsActive, returnedArticle.isActive());
	}
	
	@Test
	public void UpdateArticleById_NonExistentId_ThrowsControlledError400() throws Exception {
		final int expectedArticleId = 22;
		
		ArrayList<Article> currentArticles =_fixture.GetAllArticlesFromDb();
		
		assertEquals(1, currentArticles.size());
		
		assertNotEquals(expectedArticleId, currentArticles.get(0).getId());
		
		ArticleUpdateRequestDto updateRequest = BuildUpdateRequest(expectedArticleId, "", 0, 0, true);
		
		_mockMvc.perform(patch(url.toString())
                .contentType("application/json")
                .content(_fixture.ConvertToJsonString(updateRequest)))
                .andExpect(status().is4xxClientError());
	}

	
	private void assertArticleNotEquals(Article article, String description, double weight, double volume, boolean isActive) {
		assertNotEquals(description, article.getDescription());
		assertNotEquals(weight, article.getWeight());
		assertNotEquals(volume, article.getVolume());
		assertNotEquals(isActive, article.isActive());
	}
	
	private ArticleUpdateRequestDto BuildUpdateRequest(int articleIdToChange, String description, double weight, double volume, boolean isActive) {
		ArticleUpdateRequestDto updateRequest = new ArticleUpdateRequestDto();
		
		updateRequest.setArticleIdToUpdate(articleIdToChange);
		updateRequest.setDescription(description);
		updateRequest.setWeight(weight);
		updateRequest.setVolume(volume);
		updateRequest.setActive(isActive);
		
		return updateRequest;
	}
	
	@AfterEach
	private void CleanUp() {
		TestsFixture.CleanDb();
	}
}
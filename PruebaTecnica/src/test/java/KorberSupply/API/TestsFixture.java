package KorberSupply.API;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import KorberSupply.PruebaTecnica.ArticleMockDAO.ArticleMockDao;
import KorberSupply.PruebaTecnica.Dtos.ArticleCreateRequestDto;
import KorberSupply.PruebaTecnica.POJO.Article;

@Service
public class TestsFixture {
	
	@MockBean
	private static ArticleMockDao _articleMockDao;
	
	@Autowired
	private ObjectMapper _objectMapper;
	
	@Autowired
    private WebApplicationContext _webApplicationContext; 
	
	private String defaultDescription = "Description of article number";
	
	public TestsFixture (ArticleMockDao articleMockDao, ObjectMapper objectMapper, WebApplicationContext webApplicationContext) {
		_objectMapper = objectMapper;
		_webApplicationContext = webApplicationContext;
		_articleMockDao = articleMockDao;
	}
	
	public void InstantiateArticlesInDb() {
		InstantiateArticlesInDb(3);
	}
	
	public ArrayList<Article> GetAllArticlesFromDb() {
		return _articleMockDao.GetAllArticles();
	}
	
	public void InstantiateArticlesInDb(int numberOfArticles) {
		for (int i = 0; i < numberOfArticles; i++) {
			InsertArticle(defaultDescription + i, i, i, i % 2 == 0);
		}
	}
	
	public void InsertArticle(String description, double weight, double volume, boolean isActive) {
		ArticleCreateRequestDto articleToInsert = new ArticleCreateRequestDto();
		
		articleToInsert.setDescription(description);
		articleToInsert.setWeight(weight);
		articleToInsert.setVolume(volume);
		articleToInsert.setActive(isActive);
		
		_articleMockDao.CreateArticle(articleToInsert);
	}
	
	public URI GetControllerUrl(int port) {
		return UriComponentsBuilder
	            .fromHttpUrl("http://localhost")
	            .port(port)
	            .path("/api/articles")
	            .build()
	            .toUri();
	}
	
	public MockMvc BuildMockMvc() {
		return MockMvcBuilders.webAppContextSetup(_webApplicationContext).build();
	}
	
	public String ConvertToJsonString(Object obj) {
		try {
			return _objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	
	public Article MapJsonToArticle(String json) throws JsonMappingException, JsonProcessingException {
        return _objectMapper.readValue(json, Article.class);
	}
	
	public ArrayList<Article> MapJsonToArticleArrayList(String json) throws JsonMappingException, JsonProcessingException {
        return _objectMapper.readValue(json, new TypeReference<ArrayList<Article>>(){});
	}
	
	public void AssertDefaultArticlesCollection (ArrayList<Article> articleCollection) {
		for (int i = articleCollection.size() - 1; i > 0; i--) { //The collections are returned upside down (highest id first)
			AssertDefaultArticle(articleCollection.get(i));
		}
	}
	
	public void AssertDefaultArticle(Article articleToAssert) {
		int index = articleToAssert.getId();
		
		assertEquals(defaultDescription + index, articleToAssert.getDescription());
		assertEquals(index, articleToAssert.getWeight());
		assertEquals(index, articleToAssert.getVolume());
		assertEquals(index % 2 == 0, articleToAssert.isActive());
	}
	
	public ArticleCreateRequestDto BuildArticleCreateRequest(String description, double weight, double volume, boolean isActive) {
		ArticleCreateRequestDto createRequest = new ArticleCreateRequestDto();
		
		createRequest.setDescription(description);
		createRequest.setWeight(weight);
		createRequest.setVolume(volume);
		createRequest.setActive(isActive);
		
		return createRequest;
	}
	
	//Doing it this way to avoid giving easy access to a drop command on the DAO interface (In SQL it would just be a DROP TABLE)
	public static void CleanDb() {
		for (Article article : _articleMockDao.GetAllArticles()) {
			_articleMockDao.DeleteArticleById(article.getId());
		}
		
		_articleMockDao.ResetPrimaryKey();
	}
}
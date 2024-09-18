package KorberSupply.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import KorberSupply.API.ControllersTestConfig;
import KorberSupply.API.TestsFixture;
import KorberSupply.PruebaTecnica.PruebaTecnicaApplication;
import KorberSupply.PruebaTecnica.ArticleMockDAO.ArticleMockDao;
import KorberSupply.PruebaTecnica.POJO.Article;

@SpringBootTest(classes = PruebaTecnicaApplication.class)
@ContextConfiguration(classes = ControllersTestConfig.class)
public class ArticleMockDaoTests {
	
	@Autowired
	private TestsFixture _fixture;
	
	@Autowired
	private ArticleMockDao _articleMockDao;
	
	@BeforeEach
	private void SetUp(){
		_fixture.InstantiateArticlesInDb();
	}
	
	@Test
	public void ResetPrimaryKey() {
		ArrayList<Article> currentArticles = _fixture.GetAllArticlesFromDb();
		
		assertEquals(3, currentArticles.size());
		
		assert(0 < currentArticles.get(2).getId()); //Assert that the last insert is not higher than 0 since its increasing
		
		TestsFixture.CleanDb(); //Remove the Db so there is no conflict on the key
		
		_articleMockDao.CreateArticle(_fixture.BuildArticleCreateRequest("Test", 1, 1, false));
		
		ArrayList<Article> newArticles = _fixture.GetAllArticlesFromDb();
		
		assertEquals(1, newArticles.size());
		assertNotNull(newArticles.get(0));
	}
	
	@Test
	public void ResetPrimaryKey_WithoutCleaningDb() {
		ArrayList<Article> currentArticles = _fixture.GetAllArticlesFromDb();
		
		assertEquals(3, currentArticles.size());
		
		assert(0 < currentArticles.get(2).getId()); //Assert that the last insert is not higher than 0 since its increasing
		
		_articleMockDao.CreateArticle(_fixture.BuildArticleCreateRequest("Test", 1, 1, false));
		
		ArrayList<Article> newArticles = _fixture.GetAllArticlesFromDb();
		
		assertEquals(4, newArticles.size()); //4 because we added an extra one

		Article createdArticle = newArticles.get(3);
		
		assertEquals("Test", createdArticle.getDescription());
		assertEquals(1, createdArticle.getWeight());
		assertEquals(1, createdArticle.getVolume());
		assertEquals(false, createdArticle.isActive());
		assertNotEquals(0, createdArticle.getId());
	}
	
	@AfterEach
	private void CleanUp() {
		TestsFixture.CleanDb();
	}
}
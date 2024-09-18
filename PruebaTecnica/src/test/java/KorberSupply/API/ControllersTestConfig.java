package KorberSupply.API;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import KorberSupply.PruebaTecnica.ArticleMockDAO.ArticleMockDao;

@TestConfiguration
public class ControllersTestConfig {

	@Bean
	public ArticleMockDao articleMockDao() {
		return new ArticleMockDao();
	}
	
	 @Bean
	    public ObjectMapper objectMapper() {
	        return new ObjectMapper();
	    }
	
    @Bean
    public TestsFixture controllersTestFixture(ArticleMockDao articleMockDao, ObjectMapper objectMapper, WebApplicationContext webApplicationContext) {
        return new TestsFixture(articleMockDao, objectMapper, webApplicationContext);
    }
}
package KorberSupply.PruebaTecnica.ArticleMockDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import KorberSupply.PruebaTecnica.Dtos.ArticleCreateRequestDto;
import KorberSupply.PruebaTecnica.Dtos.ArticleUpdateRequestDto;
import KorberSupply.PruebaTecnica.Dtos.BaseArticleRequest;
import KorberSupply.PruebaTecnica.POJO.Article;

@Repository //I know technically it doesn't talk to a DB but to make it as close as possible to the real thing 
public class ArticleMockDao {

	private HashMap<Integer, Article> articleCacheDB = new HashMap<>();
	private int primaryKeyInt = 0; 
	
	public Article GetArticleById(int id) {
		return articleCacheDB.get(id);
	}
	
	public ArrayList<Article> GetAllArticles() {
		return new ArrayList<>(articleCacheDB.values());
	}
	
	public void CreateArticle(ArticleCreateRequestDto articleRequest) {
		articleCacheDB.put(primaryKeyInt, ApiRequestToArticleMapper(articleRequest));
	}
	
	public Article UpdateArticle(ArticleUpdateRequestDto articleUpdateRequest) {
		Article oldArticle = articleCacheDB.get(articleUpdateRequest.getArticleIdToUpdate());
		
		if(oldArticle == null) {
			throw new NoSuchElementException ("No article was found with the id: " + articleUpdateRequest.getArticleIdToUpdate());
		}
		
		oldArticle.setDescription(articleUpdateRequest.getDescription());
		oldArticle.setWeight(articleUpdateRequest.getWeight());
		oldArticle.setVolume(articleUpdateRequest.getVolume());
		oldArticle.setActive(articleUpdateRequest.isActive());
		
		return oldArticle;
	}
	
	public void DeleteArticleById(Integer id) { 
		if(articleCacheDB.remove(id) == null) {
			throw new NoSuchElementException ("No article was found with the id: " + id);
		};
	}
	
	
	//Only used in one place but if this project ever got extended I am assuming that it would be used in many more places, for that reason (and SOLID) it got its own method
	private Article ApiRequestToArticleMapper(BaseArticleRequest request) {
		return new Article(primaryKeyInt++,
					request.getDescription(),
					request.getWeight(),
					request.getVolume(),
					request.isActive()
				);
	}
	
	//This method shouldnt exist but because this is not an actual database this does not get done automatically
	public void ResetPrimaryKey() {
		primaryKeyInt = 0;
	}
}
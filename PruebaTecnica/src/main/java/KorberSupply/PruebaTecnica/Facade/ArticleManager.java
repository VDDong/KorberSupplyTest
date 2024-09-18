package KorberSupply.PruebaTecnica.Facade;

import KorberSupply.PruebaTecnica.Dtos.ArticleCreateRequestDto;
import KorberSupply.PruebaTecnica.Dtos.ArticleUpdateRequestDto;
import KorberSupply.PruebaTecnica.POJO.Article;
import java.util.List;

public interface ArticleManager {
	
	Article GetArticleById(int id);
	
	List<Article> GetAllArticles();
	
	void CreateArticle(ArticleCreateRequestDto article);
	
	Article UpdateArticle(ArticleUpdateRequestDto newArticle);
	
	void DeleteArticleById(int id);
}
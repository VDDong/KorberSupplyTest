package KorberSupply.PruebaTecnica.Facade.Impl;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import KorberSupply.PruebaTecnica.ArticleMockDAO.ArticleMockDao;
import KorberSupply.PruebaTecnica.Dtos.ArticleCreateRequestDto;
import KorberSupply.PruebaTecnica.Dtos.ArticleUpdateRequestDto;
import KorberSupply.PruebaTecnica.Facade.*;
import KorberSupply.PruebaTecnica.POJO.Article;

@Service
public class ArticleManagerImpl implements ArticleManager{
	
	@Autowired
	private ArticleMockDao _articleMockDao;
	
	public ArticleManagerImpl(ArticleMockDao articleMockDao) {
		_articleMockDao = articleMockDao;
	}
	
	public Article GetArticleById(int id) {
		Article returnArticle = _articleMockDao.GetArticleById(id);
		
		if (returnArticle == null) {
			throw new NoSuchElementException ("No article was found with the id: " + id);
		}
		
		return returnArticle;
	}
	
	public ArrayList<Article> GetAllArticles() {
		return _articleMockDao.GetAllArticles();
	}
	
	public void CreateArticle(ArticleCreateRequestDto article) {
		_articleMockDao.CreateArticle(article);
	}
	
	public Article UpdateArticle(ArticleUpdateRequestDto newArticle) {
		if(newArticle == null) {
			throw new IllegalArgumentException("An article is needed in order to proceed with the update.");
		}
		
		return _articleMockDao.UpdateArticle(newArticle);
	}
	
	public void DeleteArticleById(int id) { 
		_articleMockDao.DeleteArticleById(id);
	}
}
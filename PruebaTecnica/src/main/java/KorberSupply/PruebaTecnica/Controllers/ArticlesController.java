package KorberSupply.PruebaTecnica.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import KorberSupply.PruebaTecnica.Dtos.ArticleCreateRequestDto;
import KorberSupply.PruebaTecnica.Dtos.ArticleUpdateRequestDto;
import KorberSupply.PruebaTecnica.Facade.Impl.ArticleManagerImpl;
import KorberSupply.PruebaTecnica.POJO.Article;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
	
	@Autowired
	private ArticleManagerImpl _articlesManagerService;
	
	public ArticlesController(ArticleManagerImpl articlesManagerService) {
		_articlesManagerService = articlesManagerService;
	}
	
	@PostMapping
	public void PostArticle(@RequestBody ArticleCreateRequestDto article) {
		_articlesManagerService.CreateArticle(article);
	}
	
	@GetMapping("/{id}")
	public Article GetArticleById(@PathVariable int id) {
		return _articlesManagerService.GetArticleById(id);
	}
	
	@GetMapping
	public ArrayList<Article> GetAllArticles() {
		return _articlesManagerService.GetAllArticles();
	}
	
	@PatchMapping
	public Article UpdateArticleById(@RequestBody ArticleUpdateRequestDto article) {
		return _articlesManagerService.UpdateArticle(article);
	}
	
	@DeleteMapping("/{id}")
	public void DeleteArticleById(@PathVariable int id) {
		_articlesManagerService.DeleteArticleById(id);
	}
}
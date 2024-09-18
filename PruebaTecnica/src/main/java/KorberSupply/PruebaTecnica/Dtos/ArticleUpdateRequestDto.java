package KorberSupply.PruebaTecnica.Dtos;

public class ArticleUpdateRequestDto extends BaseArticleRequest{
	private int articleIdToUpdate;

	public int getArticleIdToUpdate() {
		return articleIdToUpdate;
	}

	public void setArticleIdToUpdate(int articleIdToUpdate) {
		this.articleIdToUpdate = articleIdToUpdate;
	}
}
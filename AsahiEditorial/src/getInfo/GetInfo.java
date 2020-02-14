package getEditorial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetEditorial {
	public static void main(String[] args) {
		try {

			// 朝日新聞のサイトから社説のタイトル・アップロード日付・内容を取得する
			
			// 必要なURLの設定
			final String BASED_URL = "https://www.asahi.com";
			final String TARGET_URL = "https://www.asahi.com/news/editorial.html?iref=comtop_gnavi";

			// 社説一覧ページからDocumentを取得する
            Document doc = Jsoup.connect(TARGET_URL).get();

            // 社説一覧から記事ごとのURLを取得する
            Elements elms = doc.select("#CurrentMonth").select("ul").select("li").select("dd").select("a");

            // 記事のURLを詰めおく
            List<String> editorialUrlList = new ArrayList<>();
            for(Element elm : elms) {
            	editorialUrlList.add(BASED_URL + elm.attr("href"));
            }

            // 記事ごとのDocumentを取得しておく
            List<Document> editorialList = new ArrayList<>();
            for(String editorialUrl : editorialUrlList) {
                editorialList.add(Jsoup.connect(editorialUrl).get());
            }

			// 確認のための表示処理
            for(Document editorial : editorialList) {
            	System.out.println(editorial.select(".Title").select("h1"));
            	System.out.println(editorial.select(".Title").select("time"));
            	System.out.println(editorial.select(".ArticleText").select("p"));
            }

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

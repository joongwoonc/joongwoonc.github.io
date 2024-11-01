package kr.top2blue.national33_31;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NationalRepresentativeList {
	public static void main(String[] args) {
		// 오마이뉴스 민족대표 33인 열전 : URL로 못읽어 요소를 복사해 HTML로 만들고 리스트 중간의 input태그 제거 후 읽음
		String url = "https://www.ohmynews.com/NWS_Web/Series/series_premium_list.aspx?SRS_CD=0000011986";
		List<NationalRepresentativeVO> list = new ArrayList<NationalRepresentativeVO>();
		try {
			File file = new File("src/main/resources/nationalNews.html");
			Document doc = Jsoup.parse(file,"UTF-8", url);
			
			Elements elements = doc.select("ul.general_list li");
			System.out.println(elements.size());
			for(Element e : elements) {
				String t = e.selectFirst("strong.title").text();
				int index = Integer.parseInt(t.substring(0, t.indexOf("화")));
				
				String title = t.substring(t.indexOf("화")+1);
				System.out.println(index + " : " + title);
				
				String subtitle = e.selectFirst("p.title_sub").text();
				System.out.println(subtitle);
				
				String description = e.selectFirst("div.desc_atc").text();
				System.out.println(description);
				
				String imageUrl = e.selectFirst("img").attr("src");
				System.out.println(imageUrl);
				
				String linkAddress = "https://www.ohmynews.com" + e.selectFirst("a").attr("href");
				System.out.println(linkAddress);
				
				if(index>1) {
					NationalRepresentativeVO vo = new NationalRepresentativeVO();
					vo.setIndex(index-1);
					vo.setTitle(title);
					vo.setSubtitle(subtitle);
					vo.setDescription(description);
					vo.setImageUrl(imageUrl);
					vo.setLinkAddress(linkAddress);
					
					list.add(vo);
				}
			}
			
			list.sort(new Comparator<NationalRepresentativeVO>() {
				@Override
				public int compare(NationalRepresentativeVO o1, NationalRepresentativeVO o2) {
					return o1.getIndex()-o2.getIndex();
				}
			});

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			PrintWriter pw = new PrintWriter("src/main/resources/NationalRepresentative.json");
			gson.toJson(list, pw);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

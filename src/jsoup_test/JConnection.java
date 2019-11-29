package jsoup_test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JConnection {
	public void JCPost(String baseUrl) {

		Document doc = null; // 전체 페이지 데이터를 담는 변수
		String forwardUrl = null;

		try {
			doc = Jsoup.connect(baseUrl).post();
			// 포워딩 url 반환
			forwardUrl = getForwardUrl(doc);

			// 도큐먼트를 포워드url로 재 설정

			// 해당 도메인은 모바일 전용이기때문에 userAgent를 모바일데이터를 가져오도록 설정한다.
			doc = Jsoup.connect(forwardUrl)
					.userAgent(
							"Mozilla/5.0(Linux; U; Android 4.2; en-gb; LG-P500 Build/FRF91) AppleWebKit/533.0 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
					.post();

			getDataCrawling(doc);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getForwardUrl(Document doc) {
		String forwardUrl = null;

		// script 태그만 전체를 가져온다.
		Elements scriptElements = doc.getElementsByTag("script");

		// 2번째 스크립트 요소를 가져온다. ( forawrdUrl 함수를 가지고 있는 script를 가져옴)
		Element element = scriptElements.get(1);

		// 중간출력......
		// System.out.println("All
		// Scripts....========================================");
		// System.out.println(element);
		// System.out.println("=========================================================");

		// window.location.href 키워드가 있는지 체크
		if (element.data().contains("window.location.href")) {
			// .* 임의의 문자가 매우 많을 수 있음
			// \\. 이어지는 특수 문자는 .이다.
			// () 패턴 그룹
			// [^;] ;를 제외한 모든 문자를 허용
			// * 뒤 문자의 한계는 없다.
			Pattern pattern = Pattern.compile(".*window\\.location\\.href=([^;]*)"); // 패턴
																						// 조합
			Matcher matcher = pattern.matcher(element.data());// 크롤링으로 반환 받은
																// 데이터와 매칭

			// 필수 구현 요소
			if (matcher.find()) {// 매칭 요소가 있는지 확인
				// System.out.println("Get Forwarding
				// URL....========================================");
				// System.out.println(matcher.group(1));
				forwardUrl = matcher.group(1);
				// System.out.println("Get Forwarding
				// URL....========================================");
			} else {
				// 이후 예외 처리 필요
				System.err.println("No match found!");
			} // end of if(matcher.find())
		} // end of if(element.data().contains())

		// 불필요한 문자열 제거 후, 포워딩 url을 조합한다.
		forwardUrl = "http://m.lotteimall.com" + forwardUrl.substring(1, forwardUrl.length() - 1);
		return forwardUrl;
	}// end of getForwardUrl

	private void getDataCrawling(Document doc) {
		// 데이터 확인용 코드..
		// dataSaveToFile(doc.toString());
		Element element = null;

		// element = doc.select("a.pd_thumb img").first();
		// System.out.println("섬네일 이미지 :" + element.absUrl("src"));
		// element = doc.select("div.bd_name strong").first();
		// System.out.println("브랜드 네임 : " + element.text());
		// element = doc.select("span.pd_name").first();
		// System.out.println("상품 명 : " + element.text());
		// element = doc.select("span.price strong.sale span.num").first();
		// System.out.println("상품 가격 : " + element.text());

		element = doc.selectFirst("input#recommendStyleYn");
		System.out.println(element.val());
		element = doc.selectFirst("input#goods_no");
		System.out.println(element.val());
		element = doc.selectFirst("input#goods_nm");
		System.out.println(element.val());
		element = doc.selectFirst("input#sale_price");
		System.out.println(element.val());
		element = doc.selectFirst("input#final_sale_price");
		System.out.println(element.val());
		element = doc.selectFirst("input#lvl2_cate_no");
		System.out.println(element.val());
		element = doc.selectFirst("input#ad_goods_nm");
		System.out.println(element.val());
		element = doc.selectFirst("input#goods_url");
		System.out.println(element.val());
		element = doc.selectFirst("input#goods_img");
		System.out.println(element.val());


		System.out.println("\n상세정보 섹션 전체 코드===========================");
		Elements elements = doc.select("section.tab_cont.pd_detail_content");
		System.out.println(elements);
	}

	// 파일 저장용 임시 코드
	private void dataSaveToFile(String text) {
		String fn = "d:/crowlingtext.txt";

		File file = new File(fn);

		FileWriter fw = null;
		try {
			file.createNewFile();
			fw = new FileWriter(file, false);
			fw.write(text);
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

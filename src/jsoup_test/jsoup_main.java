package jsoup_test;


public class jsoup_main {
	
	public static void main(String[] args){
		JConnection jc = new JConnection();
		String baseUrl = "http://www.lotteimall.com/coop/affilGate.lotte?chl_no=141370&chl_dtl_no=2540914&returnUrl=/goods/viewGoodsDetail.lotte?goods_no=1033900041";
		
		jc.JCPost(baseUrl);
	}
}



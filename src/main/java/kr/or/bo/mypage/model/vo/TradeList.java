package kr.or.bo.mypage.model.vo;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.product.model.vo.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TradeList {
	private int tradeNo;
	private int productBoardNo;
	private String consumer;
	private String tradeRequestDate;
	private String tradeCompleteDate;
	private String tradeCompleteDone;
	
	private Member member;
	

}

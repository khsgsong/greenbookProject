package kr.or.bo.wish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.FileUtil;
import kr.or.bo.member.model.vo.Member;
import kr.or.bo.wish.model.service.WishListService;
import kr.or.bo.wish.model.vo.WishListData;

@Controller
@RequestMapping(value = "/wish")
public class WishListController {
	@Autowired
	private WishListService wishListService;
	@Autowired
	private FileUtil fileUtil;
	@Value("${file.root}")
	public String root;
	
	/* 로그인 인터셉터로 해결
	//로그인 체크하기
	@GetMapping(value = "/loginCheck")
	public String loginCheck(Model model, @SessionAttribute(required = false) Member m) {
		//로그인이 되지 않은 경우 -> 관심도서 볼 수 없음
		if(m == null) {
			model.addAttribute("title", "접근 권한 없음");
			model.addAttribute("msg", "로그인 후 볼 수 있습니다.");
			model.addAttribute("loc", "/");
			return "common/msg";
		//로그인이 된 경우
		}else {
			return "redirect:/wish/myWishList?reqPage=1";	
		}
	}*/
	
	//관심 도서 등록
	@ResponseBody
	@GetMapping(value = "/insertWish")
	public int insertWish(int productBoardNo, String memberId, Model model) {
		int result = wishListService.insertWish(productBoardNo, memberId);
		return result;
	}
	
	//관심 도서 해제
	@ResponseBody
	@GetMapping(value = "/deleteWish")
	public int deleteWish(int productBoardNo, String memberId, Model model) {
		int result = wishListService.deleteWish(productBoardNo, memberId);
		return result;
	}
	
	//마이페이지 - 관심 도서 불러오기
	@GetMapping(value = "/myWishList")
	public String myWishList(Model model, int reqPage, @SessionAttribute(required = false) Member m) {
		WishListData wld = wishListService.selectMyWishList(m.getMemberId(), reqPage);
		model.addAttribute("list", wld.getMyWishList());
		model.addAttribute("pageNavi", wld.getPageNavi());
		return "mypage/myWishList";
	}
	
	//헤더 아이콘 - 관심 도서 불러오기
	@GetMapping(value = "/mainWishList")
	public String mainWishList(Model model, int reqPage, @SessionAttribute(required = false) Member m) {
		WishListData wld = wishListService.selectMainWishList(m.getMemberId(), reqPage);
		model.addAttribute("list", wld.getMyWishList());
		model.addAttribute("pageNavi", wld.getPageNavi());
		return "mypage/mainWishList";
	}
	
	@GetMapping(value="/list")
	public String photoList(Model model) {
		int totalCount = wishListService.totalCount();
		model.addAttribute("totalCount", totalCount);
		return "common/mainContents";
	}
	
	@ResponseBody
	@GetMapping(value="/more")
	public List more(int start, int end) {
		//System.out.println(start);
		//System.out.println(end);
		List wishlist = wishListService.selectWishlist(start,end);
		return wishlist;
	}
}

package kr.or.bo.msg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.msg.model.service.MsgService;
import kr.or.bo.msg.model.vo.Msg;

@Controller
@RequestMapping(value = "/msg")
public class MsgController {
	@Autowired
	private MsgService msgService;
	
	//로그인 체크하기
	@GetMapping(value = "/loginCheck")
	public String loginCheck(Model model, @SessionAttribute(required = false) Member m) {
		//로그인이 되지 않은 경우 -> 쪽지리스트 볼 수 없음
		if(m == null) {
			model.addAttribute("title", "접근 권한 없음");
			model.addAttribute("msg", "로그인 후 볼 수 있습니다.");
			model.addAttribute("icon", "warning");
			model.addAttribute("loc", "/");
			return "common/msg";
		//로그인이 된 경우
		}else {
			return "redirect:/msg/receiveList";
		}
	}
	
	//쪽지 리스트
	@GetMapping(value = "/receiveList")
	public String receiveList(Model model, @SessionAttribute(required = false) Member m) {
		List list = msgService.selectReceiveList(m.getMemberId());
		model.addAttribute("list", list);
		return "msg/msglist";
	}
	
	//관리자에게 쪽지 보내기
	@PostMapping(value = "/sendMsgToAdmin")
	public String sendMsgToAdmin(Msg msg, Model model) {
		int result = msgService.sendMsgToAdmin(msg);
		//쪽지 전송 성공
		if(result > 0) {
			model.addAttribute("title", "전송 완료");
			model.addAttribute("msg", "관리자에게 쪽지가 성공적으로 전송되었습니다.");
			model.addAttribute("icon", "success");
		//쪽지 전송 실패
		}else {
			model.addAttribute("title", "전송 실패");
			model.addAttribute("msg", "관리자에게 쪽지가 성공적으로 전송되었습니다.");
			model.addAttribute("icon", "error");
		}
		model.addAttribute("loc", "/msg/receiveList");
		return "common/msg";
	}
	
	//쪽지 상세 보기
	@GetMapping(value = "/receiveView")
	public String receiveView(int mid, Model model) {
		Msg msg = msgService.selectReceiveView(mid);
		if(msg != null) {
			model.addAttribute("msg", msg);
			return "msg/msglist";
		}else {
			model.addAttribute("title", "조회 실패");
			model.addAttribute("msg", "쪽지 상세 조회에 실패하였습니다.");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/msg/receiveList");
			return "common/msg";
		}
		
	}

}
package study.spring.clip.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import study.spring.clip.model.User;
import study.spring.clip.service.LoginService;

@Slf4j
@Controller
public class LoginController{

	@Autowired
	LoginService loginService; //로그인 서비스 객체 주입
	
	/** "/프로젝트이름" 에 해당하는 ContextPath 변수 주입 */
	// --> import org.springframework.beans.factory.annotation.Value;
	@Value("#{servletContext.contextPath}")
	String contextPath;
	
	/*로그인 페이지 로그인 ajax처리*/
	@ResponseBody
	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String loginProcess(HttpSession session,
			@RequestParam(value="user_id") String id,
			@RequestParam(value="user_pw") String pw) {
		
		if(loginService.loginCheck(session, id, pw)) { return "1"; }
		return "0";
	}
	
	/*로그아웃처리 (세션 초기화)*/
	 @RequestMapping(value="logout.do",method=RequestMethod.GET)
	 public void logoutProcess(HttpSession session, HttpServletResponse response) { 
		session.invalidate();
		 try {
			response.sendRedirect(contextPath + "/home");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 /*로그인 페이지로 이동*/
	 @RequestMapping(value="Login",method=RequestMethod.GET)
	 public String enterLogin() { 
		 return "Login"; 
	 }
	 
	 
	 /*내정보 보기*/
	 @RequestMapping(value="MY_information",method=RequestMethod.GET)
	 public String enterMyInformation(HttpSession session, HttpServletResponse response
			 ,Model model) { 
		 if ( session.getAttribute("id") == null ) {
				try {
					response.sendRedirect(contextPath + "/Login");
					return "Login";
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
		 
		 int user_no = (Integer)session.getAttribute("user_no");
		 User user = loginService.randerUser(user_no);
		 model.addAttribute("name",user.getName());
		 model.addAttribute("gender",user.getGender());
		 model.addAttribute("birthdate",user.getBirthdate());
		 model.addAttribute("email",user.getEmail());
		 model.addAttribute("card",user.getCard());
		 return "MY_information"; 
	 }
	 
	 
	 /*약관 동의 페이지*/
	 @RequestMapping(value="Sign_up_agree",method=RequestMethod.GET)
	 public String enterSignUp() { 
		 return "Sign_up_agree"; 
	 }
	 
	 /*약관동의 후 다음페이지 넘어가기 전 이메일 수신 동의 여부 판별 세션 생성*/
	  @ResponseBody
	  @RequestMapping(value="agree.do",method=RequestMethod.POST)
	  public void agree(HttpSession session, HttpServletResponse response,
			  @RequestParam(value="should") String should) { 
		  session.setAttribute("should", should);
	  }
	  
	  /*아이디 중복검사*/
	  @ResponseBody
	  @RequestMapping(value="idOverlapCheck.do",method=RequestMethod.POST)
	  public String idOverlapCheck(HttpServletResponse response,
			  @RequestParam(value="id") String id) { 
		  if(loginService.idOverlapCheck(id)) {return "1";}
		  return "0";
	  }
	  
	  /*이메일 중복검사*/
	  @ResponseBody
	  @RequestMapping(value="emailOverlapCheck.do",method=RequestMethod.POST)
	  public String emailOverlapCheck(HttpServletResponse response,
			  @RequestParam(value="email") String email) { 
		  if(loginService.emailOverlapCheck(email)) {return "1";}
		  return "0";
	  }
	  
	  /*세션과 비교하여 비밀번호 검사*/
	  @ResponseBody
	  @RequestMapping(value="pwCheck.do",method=RequestMethod.POST)
	  public String pwCheck(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="pw") String pw) { 
		  String id = (String)session.getAttribute("id");
		  if(loginService.pwCheck(id, pw)) {return "1";}
		  return "0";
	  }
	  
	  
	  /*회원정보 기입 페이지*/
	  @RequestMapping(value="Sign_up_information",method=RequestMethod.GET)
	  public String enterSignUpInfo() {
			 
		  return "Sign_up_information"; 
	  }
		 
	  /*회원가입 처리 입력값과 세션의 이메일 수신동의 여부 파라미터로 전달*/
	  @ResponseBody
	  @RequestMapping(value="signUp.do",method=RequestMethod.POST)
	  public void signUp(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="id") String id,
			  @RequestParam(value="name") String name,
			  @RequestParam(value="email") String email,
			  @RequestParam(value="pw") String pw,
			  @RequestParam(value="edit") String edit,
			  @RequestParam(value="birth") String birth,
			  @RequestParam(value="gender") String gender) { 
		  String agree = (String)session.getAttribute("should");
		  loginService.signUp(agree, id, name, email, pw, edit, birth, gender);
	  }
	  
	  /*회원 탈퇴*/
	  @ResponseBody
	  @RequestMapping(value="deleteUser.do",method=RequestMethod.GET)
	  public void deleteUser(HttpSession session) { 
		 int user_no = (Integer)session.getAttribute("user_no");
		 loginService.deleteUser(user_no);
		 session.invalidate();
	  }
	  
	  /*비밀번호 변경*/
	  @ResponseBody
	  @RequestMapping(value="pwChange.do",method=RequestMethod.POST)
	  public void pwChange(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="pw") String pw) { 
		  int user_no = (Integer)session.getAttribute("user_no");
		  loginService.pwChange(user_no, pw);	 
	  }
	  
	  /*이메일 변경*/
	  @ResponseBody
	  @RequestMapping(value="emailChange.do",method=RequestMethod.POST)
	  public void emailChange(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="email") String email) { 
		  int user_no = (Integer)session.getAttribute("user_no");
		  loginService.emailChange(user_no, email);
	  }
	  
	  /*카드 등록/변경*/
	  @ResponseBody
	  @RequestMapping(value="cardChange.do",method=RequestMethod.POST)
	  public void cardChange(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="card") String card) { 
		  int user_no = (Integer)session.getAttribute("user_no");
		  loginService.cardChange(user_no, card);
	  }
	  
	  /*회원정보 기입 페이지*/
	  @RequestMapping(value="Search_id_for_email",method=RequestMethod.GET)
	  public String enterSearchId() {
			 
		  return "Search_id_for_email"; 
	  }
	  
	  /*회원정보 기입 페이지*/
	  @RequestMapping(value="Search_password_for_id",method=RequestMethod.GET)
	  public String enterSearchPw() {
			 
		  return "Search_password_for_id"; 
	  }
	  
	  /*회원정보 기입 페이지*/
	  @RequestMapping(value="Search_password_for_email",method=RequestMethod.GET)
	  public String enterSearchPwToEmail() {
			 
		  return "Search_password_for_email"; 
	  }
	  
	  /*카드 등록/변경*/
	  @ResponseBody
	  @RequestMapping(value="emailCheckToName.do",method=RequestMethod.POST)
	  public String emailCheckToName(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="name") String name,
			  @RequestParam(value="email") String email) { 
		  
		  if(loginService.emailCheckToName(name, email)) { return "0"; }
		  return "1";
	  }
	  
	  /*아이디찾기*/
	  @ResponseBody
	  @RequestMapping(value="randerID.do",method=RequestMethod.POST)
	  public String [] randerID(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="email") String email) { 
		  User user = loginService.randerID(email);
		  String [] item = {user.getId(),user.getEditdate()};
		  return item;
	  }
	  
	  /*비밀번호찾기 아이디 검사*/
	  @ResponseBody
	  @RequestMapping(value="idCheck.do",method=RequestMethod.POST)
	  public String idCheck(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="id") String id) { 
		  if(loginService.idOverlapCheck(id, session)) {return "1";}
		  return "0";
	  }
	  
	  @ResponseBody
	  @RequestMapping(value="searchUserToChangePW.do",method=RequestMethod.POST)
	  public String searchUserToChangePW(HttpServletResponse response,
			  @RequestParam(value="id") String id,
			  @RequestParam(value="email") String email,
			  @RequestParam(value="name") String name) { 
		  if(loginService.searchUserToChangePW(id, email, name)) {return "1";}
		  return "0";
	  }
	  
	  @ResponseBody
	  @RequestMapping(value="searchpwToChange.do",method=RequestMethod.POST)
	  public void searchpwToChange(HttpServletResponse response, HttpSession session,
			  @RequestParam(value="pw") String pw) { 
		  String id = (String)session.getAttribute("id_check");
		  loginService.searchpwToChange(id, pw);
	  }
}
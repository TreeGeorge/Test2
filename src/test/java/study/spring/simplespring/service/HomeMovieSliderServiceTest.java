package study.spring.simplespring.service;

import org.apache.ibatis.session.SqlSession;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import lombok.extern.slf4j.Slf4j;
import study.spring.clip.model.HomeMovieSlider;
import study.spring.clip.service.FreeMovieService;
import study.spring.clip.service.HomeMovieSliderService;

/** Lombok의 Log4j 객체 */
//import lombok.extern.slf4j.Slf4j;
@Slf4j
/** JUnit에 의한 테스트 클래스로 정의 */
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
/** Spring의 설정 파일들을 읽어들이도록 설정 (**은 `모든` 이라는 의미) */
//import org.springframework.test.context.ContextConfiguration;
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
/** 웹 어플리케이션임을 명시 */
//import org.springframework.test.context.web.WebAppConfiguration;
@WebAppConfiguration
/** 메서드 이름순서로 실행하도록 설정 (설정하지 않을 경우 무작위 순서로 실행됨) */
//import org.junit.FixMethodOrder;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomeMovieSliderServiceTest {

	/** Service 객체 주입 설정 */
	@Autowired
	private SqlSession sqlSession;
	
	/** Service 객체 주입 설정 */
	@Autowired
	private HomeMovieSliderService homeMovieSliderService;

	/** 추천영화(최신날짜순 desc) 조회 테스트 */
	@Test
	public void testA() {
		homeMovieSliderService.getHomeMovieSliderList();
	}
	
	/** 액션영화 조회 테스트 */
	@Test
	public void testB() {
		// 검색조건으로 사용될 POJO 클래스 객체
		HomeMovieSlider input = new HomeMovieSlider();
		// 영화타입이 액션일 경우
		input.setName("액션");
		sqlSession.selectList("HomeMovieSliderMapper.JangreMovieSlider",input);
	}
	
}

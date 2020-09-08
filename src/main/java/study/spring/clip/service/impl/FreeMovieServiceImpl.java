package study.spring.clip.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.clip.model.FreeMovie;
import study.spring.clip.service.FreeMovieService;

@Service
public class FreeMovieServiceImpl implements FreeMovieService {

	/** MyBatis 세션 객체 주입 설정 */
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<FreeMovie> getFreeMovieList(){
		
		List<FreeMovie> result = sqlSession.selectList("FreeMovieMapper.freeList");
		
		return result;
	}

	@Override
	public int getFreeMovieCount(){
		
		int result = sqlSession.selectOne("FreeMovieMapper.selectCountFree");
		
		return result;
	}

	@Override
	public void editFreeMovie() {
		// TODO 10개로 바꿔야함
		while (getFreeMovieCount() < 3) {

			FreeMovie input = new FreeMovie();
			
			// 무료가 아닌 영화 가져오기
			input = sqlSession.selectOne("FreeMovieMapper.notFreeMovieSearch");
			
			// 무료 기간 정해주기
			Calendar now = Calendar.getInstance();
			now.add ( Calendar.DATE, +20 );
			
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1;
			int day = now.get(Calendar.DATE);
			
			String period = year + "-" + month + "-" + day;
			
			input.setPeriod(period);
			sqlSession.update("FreeMovieMapper.setFreeEdit", input);
		}
	}

	@Override
	public void endFreeMovie() {
		
		FreeMovie input = new FreeMovie();
		
		// 현재 날짜 가져오기
		Calendar now = Calendar.getInstance();
		
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		
		String today = year + "-" + month + "-" + day;
		
		input.setPeriod(today);
		
		// 동시에 무료영화 기간이 끝날 수 있기 때문에
		while ( null != sqlSession.selectOne("FreeMovieMapper.endFreeMovieSearch", input) ) {
			int sale = 0;
			// 30% 확률로 세일하게 해줌
			if ((int)(Math.random() * 3) + 1 == 1) {
				sale = (int)(Math.random() * 55) + 5;
			}
			input = sqlSession.selectOne("FreeMovieMapper.endFreeMovieSearch", input);
			
			input.setSale(sale);
			sqlSession.update("FreeMovieMapper.endFreeEdit", input);
		}
		
	}

	@Override
	public boolean saleMovieCount() {
		// TODO 최신영화 x개 제외하고 에서 숫자 바꿔줘야됨 현재 2
		int sale = sqlSession.selectOne("FreeMovieMapper.selectCountSale");
		int all = sqlSession.selectOne("FreeMovieMapper.selectCountAll");
		
		if (sale == 0) {
			return true;
		}
		
		// 전체 영화의 1/3이 할인영화가 되도록 유지
		if (all/sale <= 3) {
			return false;
		}
		return true;
	}

	@Override
	public void editSaleMovie() {
		// TODO 나중에 FreeMovie 객체가아니라 SaleMovie 객체로 바꾸던가 해야함
		FreeMovie input = new FreeMovie();
		
		while (saleMovieCount()) {
			input = sqlSession.selectOne("FreeMovieMapper.notSaleMovieSearch", input);
			sqlSession.update("FreeMovieMapper.setSaleEdit", input);
		}
	}

}

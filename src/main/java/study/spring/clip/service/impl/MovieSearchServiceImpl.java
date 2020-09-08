package study.spring.clip.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import study.spring.clip.model.MovieSearch;
import study.spring.clip.service.MovieSearchService;

@Slf4j
@Service
public class MovieSearchServiceImpl implements MovieSearchService {

	/** MyBatis 세션 객체 주입 설정 */
	@Autowired
	SqlSession sqlSession;

	@Override
	public MovieSearch getMovieSearchItem(MovieSearch input) throws Exception {
		MovieSearch result = null;

		try {
			result = sqlSession.selectOne("MovieSearchMapper.oneMovieSearch", input);

			if (result == null) {
				throw new NullPointerException("resut=null");
			}
		} catch (NullPointerException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}
}

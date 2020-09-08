package study.spring.clip.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import study.spring.clip.model.CategorySorted;
import study.spring.clip.service.CategorySortedService;

@Slf4j
@Service
public class CategorySortedServiceImpl implements CategorySortedService {

	@Autowired
	SqlSession sqlSession;

	@Override
	public List<CategorySorted> getCategorySorted(int category_type_no) {

		List<CategorySorted> result = sqlSession.selectList("CategorySortedMapper.OneCategorySorted", category_type_no);

		return result;

	}

	@Override
	public int getTotalCount(int category_type_no) {
		
		int result = sqlSession.selectOne("CategorySortedMapper.TotalCategorySorted", category_type_no);

		return result;
	}

	

}

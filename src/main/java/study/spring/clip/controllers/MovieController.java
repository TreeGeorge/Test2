package study.spring.clip.controllers;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import study.spring.clip.model.Movie;
import study.spring.clip.service.MovieService;


@Controller
public class MovieController {
	
	
		@Autowired
		MovieService movieService;
		@Value("#{servletContext.contextPath}")
		String contextPath;

		@RequestMapping(value="Movie_information",method=RequestMethod.GET)
		public String movieinfo(Model model,HttpServletResponse response,
				@RequestParam(value="movieNo") int movie_no) {
			
			// 조회필요값 빈즈에저장
			Movie input =new Movie();
			input.setMovie_no(movie_no);
			Movie input1=new Movie();
			input1.setMovie_no(movie_no);
			Movie input2=new Movie();
			input2.setMovie_no(movie_no);
			Movie input3=new Movie();
			input3.setMovie_no(movie_no);
			
			// 조회결과를 저장할 객체 선언
			Movie output = null;
			int output1 = 0;
			double output2 = 0;
			int output3  =0;
			
			try {
				//데이터조회
				output = movieService.getMovieItem(input);
				output1 = movieService.getMovieLike(input1);
				output2 = movieService.getMovieStar(input2);
				output3 = movieService.getMoviePeople(input3);
			}catch(Exception e) {
				e.printStackTrace();
			}
			//view  처리
			model.addAttribute("movie_no",output.getMovie_no());
			model.addAttribute("movie_name",output.getName());
			model.addAttribute("movie_director",output.getDirector());		
			model.addAttribute("movie_runtime",output.getRuntime());
			model.addAttribute("movie_price", output.getPrice());
			model.addAttribute("movie_like",output1);
			model.addAttribute("movie_star",output2);
			model.addAttribute("movie_people",output3);
			model.addAttribute("movie_type",output.getType());
			
			
			
//			try {
//				response.sendRedirect(contextPath + "/Movie_information?movieNo="+movie_no);
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			
			return "Movie_information";
		}

		

}

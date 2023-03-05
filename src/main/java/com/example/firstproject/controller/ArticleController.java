package com.example.firstproject.controller;


import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller //Controller 선언
@Slf4j // 로깅을 위한 어노테이션
public class ArticleController {
    @Autowired//스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결!
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    } // new.mustache 반환

    @PostMapping("/articles/create") // 제출버튼을 누르면, 실행이 되고, Form 을 받아와 ArticleForm(dto)로 보내 생성한다.
    public String createArticle(ArticleForm form){
        log.info(form.toString());

        //1. Dto를 Entity로 변환!
        Article article = form.toEntity();
        log.info(article.toString());

        //2.  Repository에게 Entity를 Db안에 저장하게 함!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/"+ saved.getId();
    }

    @GetMapping("/articles/{id}") //id를 파라미터로 받아오기위해 @PathVariable 사용, Model 사용하기 위해 Model 선언
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        //1. id로 데이터를 가져옴
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        //2. 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos",commentDtos );

        //3. 보여줄 페이지 설정!

        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        //1. 모든 Articles 가져온다
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정 할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //model 에 데이터를 등록
        model.addAttribute("article", articleEntity);

        //뷰페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        // 1. dto를 entity로 변환
        Article articleEntity = form.toEntity();

        // 2. Entitiy를 DB로 저장
        // 2-1. db에서 기존데이터를 가져온다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터 값을 갱신
        if (target != null){
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return"redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!");
        // 1. 삭제 대상 가져온다.
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2. 대상을 삭제한다
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 3. 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles";
    }

}

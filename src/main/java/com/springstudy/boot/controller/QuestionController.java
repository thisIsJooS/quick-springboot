package com.springstudy.boot.controller;

import com.springstudy.boot.domain.Question;
import com.springstudy.boot.domain.SiteUser;
import com.springstudy.boot.form.AnswerForm;
import com.springstudy.boot.form.QuestionForm;
import com.springstudy.boot.repository.QuestionRepository;
import com.springstudy.boot.service.QuestionService;
import com.springstudy.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

//    @RequestMapping("/list")
//    public String list(Model model){
//        List<Question> questionList = this.questionService.getList();
//        model.addAttribute("questionList", questionList);
//        return "question_list";
//    }

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page){
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }

//    @PostMapping("/create")
//    public String questionCreate(@RequestParam String subject, @RequestParam String content){
//        this.questionService.create(subject, content);
//        return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";

    }
}

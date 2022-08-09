package com.springstudy.boot.controller;

import com.springstudy.boot.domain.Question;
import com.springstudy.boot.form.QuestionForm;
import com.springstudy.boot.repository.QuestionRepository;
import com.springstudy.boot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @RequestMapping("/list")
    public String list(Model model){
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }

//    @PostMapping("/create")
//    public String questionCreate(@RequestParam String subject, @RequestParam String content){
//        this.questionService.create(subject, content);
//        return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
//    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";

    }
}

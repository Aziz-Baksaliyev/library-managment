package com.library.managment.controller.viewcontroller;

import com.library.managment.model.dto.author.AuthorRequest;
import com.library.managment.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library")
public class AuthorViewController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authorRequest", new AuthorRequest());
        model.addAttribute("editMode", false);

        return "author-form";
    }




}

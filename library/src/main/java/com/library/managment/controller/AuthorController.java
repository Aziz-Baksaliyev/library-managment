package com.library.managment.controller;

import com.library.managment.model.dto.author.AuthorRequest;
import com.library.managment.model.dto.author.AuthorResponse;
import com.library.managment.model.entity.Author;
import com.library.managment.service.AuthorService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/")
    public List<AuthorResponse> getAllAuthors() {
        return authorService.getAll();
    }

    @GetMapping("/paginated")
    public Page<AuthorResponse> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return authorService.getAllPaginated(page, size);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getById(id);
    }

    @PostMapping("/")
    public AuthorResponse creatAuthor(@RequestBody AuthorRequest authorRequest) {
        return authorService.create(authorRequest);
    }

    @PutMapping("/update/{id}")
    public AuthorResponse updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest request) {
        return authorService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
    }
}
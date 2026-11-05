package com.library.managment.controller.viewcontroller;

import com.library.managment.model.dto.book.BookRequest;
import com.library.managment.model.entity.Book;
import com.library.managment.service.AuthorService;
import com.library.managment.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/library")
public class BookViewController {

    private final BookService bookService;
    private final AuthorService authorService;


    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "books";
    }

    @GetMapping("/book/add")
    public String addBookPage(Model model) {

        model.addAttribute("bookRequest", new BookRequest());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("editMode", false);

        return "book-form";
    }

    @PostMapping("/books/add")
    public String addBook(@Valid @ModelAttribute BookRequest request,
                          Model model,
                          BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("bookRequest", new BookRequest());
            model.addAttribute("editMode", false);

            return "book-form";
        }

        bookService.create(request);
        return "redirect:/library/books";
    }

    @GetMapping("/books/{id}/edit")

    public String editBookPage(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);

        bookRequest request = new BookRequest();
        request.setTitle(book.getTitle());
        request.setGenre(book.getGenre());
        request.setPrice(book.getPrice());
        request.setAuthorId(book.getAuthor().getId());

        model.addAttribute("bookRequest", request);
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("bookId", id);
        model.addAttribute("editMode", true);

        return "book-form";
    }

    @PostMapping("/books/{id}/edit")
    public String updateBook(@Valid @ModelAttribute BookRequest request,
                             @PathVariable Long id,
                             Model model,
                             BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("bookRequest", authorService.getAll());
            model.addAttribute("bookId", id);
            model.addAttribute("editMode", true);

            return "book-form";
        }

        bookService.update(id, request);
        return "redirect:/library/books";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);

        return "redirect:/library/books";
    }
}
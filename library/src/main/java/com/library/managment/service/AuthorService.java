package com.library.managment.service;

import com.library.managment.model.dto.author.AuthorRequest;
import com.library.managment.model.dto.author.AuthorResponse;
import com.library.managment.model.entity.Author;
import com.library.managment.model.repository.AuthorRepository;
import com.library.managment.restapi.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorResponse> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author is not found:" + id));

        return author;
    }

    public Author create(AuthorRequest author) {
        return authorRepository.save(author);
    }

    public Author update(Long id, AuthorRequest newAuthor) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author is not found:" + id));

        author.setFullname(newAuthor.getFullname());
        author.setCountry(newAuthor.getCountry());
        author.setStyle(newAuthor.getStyle());

        return authorRepository.save(author);
    }

    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author is not found:" + id));
        authorRepository.delete(author);
    }

    private AuthorResponse convertToResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getFullname(),
                author.getCountry(),
                author.getStyle()
        );
    }

    public Page<AuthorResponse> getAllPaginated(int page, int size) {
        return null;
    }
}
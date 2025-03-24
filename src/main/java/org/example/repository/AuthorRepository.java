package org.example.repository;

import org.example.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findByEmail(String email);
    Author save(Author author);
}

package org.example.repository;

import org.example.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findByEmail(String email); // ✅ 이메일로 검색
    Author save(Author author); // ✅ 사용자 입력 정보 저장
}

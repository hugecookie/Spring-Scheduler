package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorDto {
    private String name; // 작성자 이름
    private String email; // 작성자 email
}

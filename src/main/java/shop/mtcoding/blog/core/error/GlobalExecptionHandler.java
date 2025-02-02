package shop.mtcoding.blog.core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blog.core.error.ex.*;
import shop.mtcoding.blog.core.util.Script;

@RestControllerAdvice
public class GlobalExecptionHandler {
    // 유효성 검사 실패
    @ExceptionHandler(Exception400.class)
    public String ex(Exception400 e) {
        return Script.back(e.getMessage());
    }

    // 인증 실패
    @ExceptionHandler(Exception401.class)
    public String ex(Exception401 e) {
        return Script.href("인증되지 않았습니다.", "/login-form");
    }

    // 권한 실패
    @ExceptionHandler(Exception403.class)
    public String ex(Exception403 e) {
        return Script.back(e.getMessage());
    }

    // 페이지 찾을 수 없을 때
    @ExceptionHandler(Exception404.class)
    public String ex(Exception404 e) {
        return Script.back(e.getMessage());
    }

    // 심각한 오류
    @ExceptionHandler(Exception500.class)
    public String ex(Exception500 e) {
        return Script.back(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String ex(Exception e) {
        return Script.back(e.getMessage());
    }
}

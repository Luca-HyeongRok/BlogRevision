package shop.mtcoding.blog.user;


import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        // 자바-> db 변환시켜서 DB에 전해줘야 하니까
        public User toEntity() {
            return User.builder().username(username).password(password).email(email).build();
        }
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}

package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // DB에서 조회하면 자동으로 메핑이 된다!!    하이버네이트 DB프레임워크다-DB 자바 데이터 타입이 달라서 연결? 해주는 것
//자동 테이블 생성해준다
public class Board {
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto_increments 설정, 시퀀스 설정
    @Id //Pk 설정
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    private Timestamp createdAt;

    //private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY) // 외래키로 가져온다.
    private User user;


    @Builder
    public Board(Integer id, String title, String content, Timestamp createdAt, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}

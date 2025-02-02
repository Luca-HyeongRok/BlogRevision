package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


//클래스명 뒤에 Test붙이는 것도 컨밴션이다
//@SpringBootTest
@DataJpaTest // h2, em
@Import(BoardRepository.class)  //br띄운다  이러면 다른거 안뜸
public class BoardRepositoryTest {

    //Autowired해야 가져올 수 있다
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void updateById_test() {
        //given
        int id = 1;
        String title = "제목1변경";
        String content = "내용1변경";

        //when
        boardRepository.updateById(title, content, id);
        //eye
        Board board = boardRepository.findById(id);
        //then
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1변경");
    }

    @Test
    public void deleteById_test() {
        //given
        int id = 1;
        //when
        boardRepository.deleteById(id);
        //findById에서 터지는지 확인   어제 익셉션 해서 터진거 컨트롤 가능
        //e.getMessage가 글 하고 같으면 기대한거 하고 맞음  (이거는 스태틱으로 띄워두는 게 좋음 잘못적을 수도 있기 때문에)
        try {
            boardRepository.findById(id);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("게시글 id를 찾을 수 없습니다");
        }
        //eye
    }


    //공장에 기계가 2개 있어 콩 10cm 있다   두번째 10cm 받아서 잘 간다
    //첫번째는 10cm이하로 갈리는지확인
    //두번째  10cm이하로 갈리는거 확인 할 필요 없음  임의로 갈린다는 것 만 넣으면 된다 이게 기븐
    @Test
    public void findById_test() {
        //given
        int id = 1;
        //when
        Board board = boardRepository.findById(id);

        //eye  사실은 then 원래는 메서드로 해야 하는데 눈으로 해서 (어썰션으로 한다)(통합테스트 할 때 본다) 검증(눈으로)
        System.out.println(board.getId());
        System.out.println(board.getTitle());
        System.out.println(board.getContent());

        //then   검증(코드로)  제목1이길 기대함
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1");
    }

    @Test
    public void findAll_test() {
        //given  findall할때 필요한 매개변수를 적어준다

        //when
        List<Board> boardList = boardRepository.findAll();


        //eye
        //싸이즈 부터 보자
        System.out.println("사이즈 : " + boardList.size());
        //값 확인 타이틀 컨텐트만 보자 일단
        for (Board board : boardList) {
            System.out.println(board.getTitle());
            System.out.println(board.getContent());
        }
    }


    // save 테스트 할거면   이게 컨밴션이다   메스드명_test
    //테스트 메서드에서는 매개변수를 사용할 수 없다.
    @Test
    public void save_test() {
        //given  (매개변수를 강제로 만들어준다)
        String title = "제목1";
        String content = "내용1";

        //when 여기서 태스트 한다   인수 2개 매개변수 적어야 하는게 이거를 given에 적는다
        // boardRepository.save(title, content);

        //eye  그냥 눈으로 확인
    }


}

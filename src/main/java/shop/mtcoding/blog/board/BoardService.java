package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception403;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponse.DetailDTO 상세보기(int id, User sessionUser) {
        Board board = boardRepository.findById(id);
        boolean isOwner = false;
        return new BoardResponse.DetailDTO(board, sessionUser);
    }

    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        //누가 적었는지 알아야 해서 세션 넣어야 함
        boardRepository.save(saveDTO.toEntity(sessionUser));
        //게시글 인증이 여기서 해야 할까? 안 해도 됨 DB안 가도 되니까 컨트롤러에서 해도 욈
    }

    @Transactional
    public void 게시글삭제(int id, User sessionUser) {
        Board board = boardRepository.findById(id);
        // 게시글이 있는가? 이거는 리퍼지토리의 findById에서 알아서 throw해준다!(설정 했었음)

        // 2. 내가 쓴 글인가?
        if (sessionUser.getId() != board.getUser().getId()) {
            throw new Exception403("본인이 작성한 글이 아닙니다");
        }
        // 정상 로직 실행
        boardRepository.deleteById(id);
    }

    public List<Board> 게시글목록보기() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    //권한체크 반드시 해야 해서 조회만 한다고 끝나지 않음
    public Board 게시글수정화면가기(int id, User sessionUser) {
        //일단 조회를 해!   만약 터트리는 거를 null로 설정 했으면 따로 해줘야 함
        Board board = boardRepository.findById(id);
        // 앞에서 처리했다 생각하고 이 코드를 적은 거임 안 했으면 null이 뜸...
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }
        return board;
    }

    @Transactional
    public void 게시글수정(int id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {
        //1. 조회(없으면 404)
        Board board = boardRepository.findById(id);
        //2. 권한 체크
        if (board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }
        //3. 게시글 수정
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());
    }
}

package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception404;

import java.util.List;

@Repository
public class BoardRepository {
    //2.    IoC에 있는 객체를 찾아온
    @Autowired
    //1.
    private EntityManager em;

    @Transactional
    public void updateById(String title, String content, int id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);
        query.executeUpdate();
    }

    //삭제는 바로 다이렉트하게 삭제 X 먼저 게시물 있는지 확인하고 찾은거 삭제한다
    //만약 6트렌잭션 거는 순간 다른 애들 write할 때 멈춘다 결국
    //지금은 묶어주는 레이어 없어서 일단 트랜젝션 적는다
    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id =?");
        query.setParameter(1, id);
        query.executeUpdate();
    }


    //상세보기할 때 사용
    //resultset해서 하나씩 파싱해서 받아야 하는데 Board.class 하면 안해도 됨
    public Board findById(int id) {
        Query query = em.createQuery("select b from Board b join fetch b.user U where b.id =:id", Board.class);
        query.setParameter("id", id);
        //여러건이 아니니까 single,  빨간줄 뜨는 이유는 다운케스팅만 해주면 됨
        try {
            Board board = (Board) query.getSingleResult();
            return board;
        } catch (Exception e) {
            //익셉션을 내가 잡은 것 까지 배운 - 처리방법은 v2에서 배우기
            //터트리기는 해야 함 throw
            e.printStackTrace();
            throw new Exception404("게시글 id를 찾을 수 없습니다");
        }
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = query.getResultList();
        return boardList;
    }

    //책임 -> insert 메서드 책임이 2개면 안좋다!쓰기 힘듬   3.
    @Transactional
    public void save(Board board) {
        em.persist(board);
    }
}

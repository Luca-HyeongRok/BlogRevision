package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception401;


@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public User findByUsername(String username) {
        Query query = em.createQuery("select u from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void save(User user) {
        System.out.println("담기 전 : " + user.getId());
        em.persist(user);
        System.out.println("담은 후 :" + user.getId());
    }

    //조회커리
    public User findByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("select u from User u where u.username = :username and u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            throw new Exception401("인증되지 않았습니다.");
        }
    }
}

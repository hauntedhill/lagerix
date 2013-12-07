package de.hscoburg.etif.vbis.lagerix.backend.dao;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author zuch1000
 */
@Stateless
public class UserDAO extends BaseDAO<User> {

    public List<User> findAll() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> pet = cq.from(User.class);
        cq.select(pet);
        TypedQuery<User> q = em.createQuery(cq);

        return q.getResultList();

    }

    public List<User> findAllByGroupAndStorage(Group g, Storage s) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> pet = cq.from(User.class);
        Join<User, Groups> groupsRoot = pet.join(User_.groups);
        Join<Groups, Storage> storageRoot = groupsRoot.join(Groups_.storage);
        cq.select(pet).where(cb.and(cb.equal(groupsRoot.get(Groups_.groups), g), cb.equal(storageRoot.get(Storage_.id), s.getId())));
        TypedQuery<User> q = em.createQuery(cq);

        return q.getResultList();

    }

    public User findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> pet = cq.from(User.class);
        cq.select(pet).where(cb.equal(pet.get(User_.email), name));
        TypedQuery<User> q = em.createQuery(cq);

        List<User> result = q.getResultList();

        return result.size() == 1 ? result.get(0) : null;
    }

    public void save(User user) {
        em.persist(user);
    }

    public void save(Groups group) {
        em.persist(group);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void remove(String email) {
        User user = find(email);
        if (user != null) {
            em.remove(user);
        }
    }

    public void remove(User user) {
        em.remove(user);

    }

    public void remove(Groups user) {

        em.remove(user);

    }

    public User find(String email) {
        return em.find(User.class, email);
    }

    public void detach(User user) {
        em.detach(user);
    }
}

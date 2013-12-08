/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hscoburg.etif.vbis.lagerix.backend.services.base;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Article_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Group;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User_;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Base Service with with all access methods to the JPA provider.
 *
 * @author zuch1000
 */
public class BaseService {

    @PersistenceContext
    protected EntityManager em;

    /**
     * Saves an objet in the DB and flush the chanes imidiatly
     *
     * @param obj
     */
    public void save(Object obj) {
        em.persist(obj);
        em.flush();
    }

    /**
     * Search for an Object in the DB
     *
     * @param <T> - Class of the object
     * @param obj - The class of the object
     * @param id - The id of the object
     * @return The object or null
     */
    public <T> T findById(Class<T> obj, Integer id) {

        return em.find(obj, id);
    }

    /**
     * Removes an object from the DB and flush it to the db
     *
     * @param obj
     */
    public void remove(Object obj) {
        em.remove(obj);
        em.flush();
        em.clear();
    }

    /**
     * Merge an object from the DB and flush it to the db
     *
     * @param obj
     */
    public void merge(Object obj) {
        em.merge(obj);
        em.flush();
    }

    /**
     * Gives all Movements for an articleType as list
     *
     * @param articleTypeId - articleTypeId
     * @return List with all movements of the type
     */
    public List<Movement> getMovementsForArticleTypeId(int articleTypeId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Movement> movementCriteria = cb.createQuery(Movement.class);
        Root<Movement> movementRoot = movementCriteria.from(Movement.class);
        // Person.address is an embedded attribute
        Join<Movement, Article> articelRoot = movementRoot.join(Movement_.article);
        // Address.country is a ManyToOne
        Join<Article, ArticleType> articleTypeRoot = articelRoot.join(Article_.articleType);

        movementCriteria.select(movementRoot).where(cb.equal(articleTypeRoot.get(ArticleType_.id), articleTypeId));
        TypedQuery<Movement> q = em.createQuery(movementCriteria);

        return q.getResultList();
    }

    /**
     * Search an articleType for the parameters
     *
     * @param articleTypeName - The name
     * @param articleTypeDescription - The description
     * @param articleTypeMinimumStock - The stock amaount
     * @return A list with all articleTypes found for the criterias
     */
    public List<ArticleType> getArticleTypesBy(String articleTypeName, String articleTypeDescription, String articleTypeMinimumStock) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<ArticleType> movementCriteria = cb.createQuery(ArticleType.class);
        Root<ArticleType> movementRoot = movementCriteria.from(ArticleType.class);

        List<Predicate> wheres = new ArrayList<Predicate>();

        if (articleTypeName != null && !"".equals(articleTypeName.trim())) {
            wheres.add(cb.like(cb.upper(movementRoot.get(ArticleType_.name)), "%" + articleTypeName.toUpperCase() + "%"));
        }
        if (articleTypeDescription != null && !"".equals(articleTypeDescription.trim())) {
            wheres.add(cb.like(cb.upper(movementRoot.get(ArticleType_.description)), "%" + articleTypeDescription.toUpperCase() + "%"));
        }
        if (articleTypeMinimumStock != null && !"".equals(articleTypeMinimumStock.trim())) {
            wheres.add(cb.equal(movementRoot.get(ArticleType_.minimumStock), new Integer(articleTypeMinimumStock)));
        }

        movementCriteria.select(movementRoot).where(wheres.toArray(new Predicate[0]));

        TypedQuery<ArticleType> q = em.createQuery(movementCriteria);

        return q.getResultList();
    }

    /**
     * Returns all available articleTypes
     *
     * @return List with all AritcleTypes
     */
    public List<ArticleType> getAllArticleTypes() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ArticleType> cq = cb.createQuery(ArticleType.class);
        Root<ArticleType> root = cq.from(ArticleType.class);
        cq.select(root);
        TypedQuery<ArticleType> q = em.createQuery(cq);

        return q.getResultList();
    }

    /**
     * Give the stock count of an articleType in the storage
     *
     * @param articleType - The articleTypeid
     * @return Count of all articles in stock
     */
    public long getArticleTypeStock(ArticleType articleType) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> articleCriteria = cb.createQuery(Long.class);
        Root<ArticleType> articleRoot = articleCriteria.from(ArticleType.class);

        Join<ArticleType, Article> articelRoot = articleRoot.join(ArticleType_.articles);

        articleCriteria.select(cb.count(articleRoot)).where(cb.and(cb.isNotNull(articelRoot.get(Article_.yard)), cb.equal(articleRoot.get(ArticleType_.id), articleType.getId()))).groupBy(articleRoot.get(ArticleType_.id));

        TypedQuery<Long> q = em.createQuery(articleCriteria);

        return q.getResultList().size() == 1 ? q.getResultList().get(0).longValue() : 0;

    }

    /**
     * Returns all storages from the DB
     *
     * @return List with all storages
     */
    public List<Storage> getAllStoragesFromDB() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Storage> cq = cb.createQuery(Storage.class);
        Root<Storage> pet = cq.from(Storage.class);
        cq.select(pet);
        TypedQuery<Storage> q = em.createQuery(cq);

        return q.getResultList();
    }

    /**
     * Returns all users from the DB
     *
     * @return List with all users
     */
    public List<User> findAllUser() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> pet = cq.from(User.class);
        cq.select(pet);
        TypedQuery<User> q = em.createQuery(cq);

        return q.getResultList();

    }

    /**
     * Returns all User by group and storage
     *
     * @param g - The group of the User
     * @param s - The storage of the user
     * @return A list with all found users
     */
    public List<User> findAllUsersByGroupAndStorage(Group g, Storage s) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> pet = cq.from(User.class);
        Join<User, Groups> groupsRoot = pet.join(User_.groups);
        Join<Groups, Storage> storageRoot = groupsRoot.join(Groups_.storage);
        cq.select(pet).where(cb.and(cb.equal(groupsRoot.get(Groups_.groups), g), cb.equal(storageRoot.get(Storage_.id), s.getId())));
        TypedQuery<User> q = em.createQuery(cq);

        return q.getResultList();

    }

    /**
     * Find a user by its email
     *
     * @param email
     * @return The user or null
     */
    public User findUserByName(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> pet = cq.from(User.class);
        cq.select(pet).where(cb.equal(pet.get(User_.email), email));
        TypedQuery<User> q = em.createQuery(cq);

        List<User> result = q.getResultList();

        return result.size() == 1 ? result.get(0) : null;
    }

    /**
     * Removes an user by its email
     *
     * @param email - The email of the user
     */
    public void removeUser(String email) {
        User user = findUser(email);
        if (user != null) {
            em.remove(user);
        }
    }

    /**
     * Find a user by its email
     *
     * @param email - The email
     * @return The user object or null
     */
    public User findUser(String email) {
        return em.find(User.class, email);
    }

    /**
     * Detach the given Object from the entity manager
     *
     * @param obj - The object to detach
     */
    public void detach(Object obj) {
        em.detach(obj);
    }

    /**
     * Returns all Yards for an articleType in the storage
     *
     * @param articleTypeId - The articleType id
     * @return A list with all yards for the passed articleType
     */
    public List<Yard> getYardsForArticleTypeFromDB(int articleTypeId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Yard> movementCriteria = cb.createQuery(Yard.class);
        Root<Yard> movementRoot = movementCriteria.from(Yard.class);
        // Person.address is an embedded attribute
        Join<Yard, Article> articelRoot = movementRoot.join(Yard_.article);
        // Address.country is a ManyToOne
        Join<Article, ArticleType> articleTypeRoot = articelRoot.join(Article_.articleType);

        movementCriteria.select(movementRoot).where(cb.equal(articleTypeRoot.get(ArticleType_.id), articleTypeId));
        TypedQuery<Yard> q = em.createQuery(movementCriteria);

        return q.getResultList();

    }
}

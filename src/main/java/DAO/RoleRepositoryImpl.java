package DAO;

import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import module.Person;
import module.PersonRole;
import module.Role;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements Repository<Role> {
    private final Session session;

    @Override
    public Role find(long id) {
        return session.find(Role.class, id);
    }

    public Role findByRole(String role){
        Query query = session.createQuery("SELECT r FROM Role r WHERE role = :role", Role.class);
        query.setParameter("role", role);
        return (Role) query.getSingleResult();
    }

    public List<Role> findAllRoles(){
        return session.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role save(Role role) {
        session.persist(role);
        long id = (long) session.getIdentifier(role);
        return session.find(Role.class, id);

    }

    public void saveAll(Role... roles){
        Arrays.stream(roles).forEach(session::persist);
    }

    @Override
    public void update(Role role) {
        session.persist(role);
    }

    @Override
    public void delete(long id) {
        session.remove(id);
    }

    public List<Person> getPersonByRole(String role){
         Query query =
                 session.createQuery("SELECT p FROM Person p JOIN p.personRole pr JOIN pr.role r WHERE r.role = :role", Person.class);
         query.setParameter("role", role);
         return query.getResultList();

//        Query query = session.createQuery("SELECT r FROM Role r WHERE role = :role", Role.class);
//        query.setParameter("role", role);
//        Role roleFromDb = (Role) query.getSingleResult();
//        long id = roleFromDb.getId();
//        Query query1 =
//                session.createQuery("SELECT p.person_id FROM PersonRole p WHERE role_id = :id", PersonRole.class);
//        query1.setParameter("id", id);
//        List<Long> personIds = query1.getResultList();
//        Query query2 = session.createQuery("SELECT p FROM Person p WHERE id IN (:personIds)", Person.class);
//        query2.setParameter("personIds", personIds);
//        return query2.getResultList();

    }
}

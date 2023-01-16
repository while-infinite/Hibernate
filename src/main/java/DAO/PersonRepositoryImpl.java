package DAO;

import jakarta.persistence.Query;
import module.Person;
import module.PersonRole;
import module.Role;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonRepositoryImpl implements Repository<Person> {
    private final Session session;
    private final RoleRepositoryImpl roleRepository;

    public PersonRepositoryImpl(Session session, RoleRepositoryImpl roleRepository) {
        this.session = session;
        this.roleRepository = roleRepository;
    }


    @Override
    public Person find(long id) {
        Query query = session.createQuery("SELECT p FROM Person p WHERE p.id = :id", Person.class);
        query.setParameter("id", id);

        return (Person) query.getSingleResult();
    }

    public List<Person> findAllPerson(){
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();

    }

    @Override
    public Person save(Person person) {
        Set<PersonRole> personRoles = new HashSet<>();
        person.getPersonRoles().forEach(personRole -> {
            Role role = roleRepository.findByRole(personRole.getRole().getRole());
            PersonRole newPersonRole = new PersonRole();
            newPersonRole.setRole(role);
            newPersonRole.setPerson(person);
            personRoles.add(newPersonRole);
        });
        person.setPersonRoles(personRoles);
        session.persist(person);
        long id = (long) session.getIdentifier(person);
        return session.find(Person.class, id);
    }

    @Override
    public void update(Person person) {
        Person personFromDb = session.find(Person.class, person.getId());
        personFromDb.setName(person.getName());
        if(!person.getPersonRoles().isEmpty()){
            person.getPersonRoles().forEach(personRole -> {
                if(!personFromDb.getPersonRoles().contains(personRole)){
                    Role role = roleRepository.save(personRole.getRole());
                    PersonRole newPersonRole = new PersonRole();
                    newPersonRole.setPerson(personFromDb);
                    newPersonRole.setRole(role);
                    personFromDb.addPersonRole(newPersonRole);
                }
            });
        }
        session.persist(personFromDb);
    }

    @Override
    public void delete(long id) {
        Person person = session.find(Person.class, id);
        session.remove(person);
    }

    public List<Role> getRolesByPerson(long id){
        Query query =
                session.createQuery("SELECT r FROM Role r " +
                                                     "JOIN r.personRole pr " +
                                                     "JOIN pr.person p " +
                                                     "WHERE p.id = :id",
                        Role.class);
        query.setParameter("id", id);
//        Query query = session.createQuery("select r from Role r, Person p, PersonRole pr " +
//                                                        "WHERE pr.role_id = r.id " +
//                                                        "AND pr.person_id = p.id " +
//                                                        "AND p.id = :id",
//                Role.class);

        return query.getResultList();
    }
}

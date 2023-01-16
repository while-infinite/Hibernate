import DAO.PersonRepositoryImpl;
import DAO.RoleRepositoryImpl;
import lombok.Cleanup;
import module.Person;
import module.PersonRole;
import module.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    private static boolean rolesIsAdded;

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        @Cleanup SessionFactory sessionFactory = configuration.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl(session);
        PersonRepositoryImpl personRepository = new PersonRepositoryImpl(session, roleRepository);

        if(!rolesIsAdded)
            addRolesToDb(roleRepository, session);


        ///-----------------------------------------------------------

/////////////// Work with Person ////////////////////////////////////////////

        //Add Person with roles to DB

//        Role roleUser = new Role("user");
//        Role roleAdmin = new Role("admin");
//        PersonRole personRole = new PersonRole();
//        PersonRole personRole1 = new PersonRole();
//        personRole.setRole(roleUser);
//        personRole1.setRole(roleAdmin);
//
//        Person person = new Person();
//        person.setName("Mark");
//        person.addPersonRole(personRole);
//        person.addPersonRole(personRole1);
//
//        session.beginTransaction();
//        personRepository.save(person);
//        session.getTransaction().commit();

        ///-----------------------------------------------------------


        //Find All Person

//        List<Person> personList = personRepository.findAllPerson();
//        System.out.println(personList);
//

        ///-----------------------------------------------------------


        //Get person

//        Person person = personRepository.find(getPersonId(personRepository));
//        System.out.println(person);

        ///-----------------------------------------------------------


        //Delete Person

//        session.beginTransaction();
//        personRepository.delete(getPersonId(personRepository));
//        session.getTransaction().commit();

        ///-----------------------------------------------------------


        //Get roles by user

        List<Role> roles = personRepository.getRolesByPerson(getPersonId(personRepository));
        System.out.println(roles);


 /////////// Work with role ////////////////////////////////////


        //Get role
//        Role role = roleRepository.find(getRoleId(roleRepository));
//        System.out.println(role);

        ///-----------------------------------------------------------


        //Find by role
//        Role role = roleRepository.findByRole("user");
//        System.out.println(role);













    }

    private static long getPersonId(PersonRepositoryImpl personRepository){
        List<Person> personList = personRepository.findAllPerson();
        System.out.println(personList);
        System.out.println(personList.get(0).getId());

        return personList.get(0).getId();
    }

    private static long getRoleId(RoleRepositoryImpl roleRepository){
        List<Role> roleList = roleRepository.findAllRoles();
        System.out.println(roleList);
        System.out.println(roleList.get(0).getId());

        return roleList.get(0).getId();
    }

    private static void addRolesToDb(RoleRepositoryImpl roleRepository, Session session){
        Role roleUser = new Role("user");
        Role roleAdmin = new Role("admin");
        Role roleAdult = new Role("adult");

        session.beginTransaction();
        roleRepository.saveAll(roleUser, roleAdmin, roleAdult);
        List<Role> result = roleRepository.findAllRoles();
        System.out.println(result);
        session.getTransaction().commit();
        rolesIsAdded = true;
    }

}

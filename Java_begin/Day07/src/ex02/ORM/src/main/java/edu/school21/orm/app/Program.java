package edu.school21.orm;

import edu.school21.orm.Example;

public class Program {
    public static void main(String[] args) throws IllegalAccessException {
        OrmManager ormManager = new OrmManager();
        User user = new User();
        user.setAge(2);
        user.setFirstName("Vadim");
        user.setLastName("Adamov");
        ormManager.save(user);
        User userTwo = new User();
        userTwo.setAge(4);
        userTwo.setFirstName("11111");
        userTwo.setLastName("333333");
        ormManager.save(userTwo);
        user.setAge(10);
        user.setFirstName("Ruslan222");
        user.setLastName("2342342");
        ormManager.save(user);
        ormManager.update(user);
        User userFour = ormManager.findById(1L, User.class);
        System.out.println(userFour.getAge());

        Example man = new Example();
        man.setAddress("Lenina 20-22");
        man.setCity("Kazan");
        man.seteMail("kazan@kazan.ru");
        man.setMan(true);
        man.setWoman(false);
        man.setPhoneNumber(898888234556L);
        ormManager.save(man);
        Example woman = new Example();
        woman.setAddress("Baumana 10-54");
        woman.setCity("Kazan");
        woman.seteMail("alga@bk.ru");
        woman.setMan(false);
        woman.setWoman(true);
        woman.setPhoneNumber(85584225544L);
        ormManager.save(woman);
        woman.setMan(true);
        ormManager.save(woman);
        woman.setAddress("Izmeny 10-66");
        ormManager.update(woman);
        man.setCity("Zarevo");
        ormManager.update(man);
        Example copyMan = ormManager.findById(1L, Example.class);
        System.out.println(copyMan.getAddress());
    }
}

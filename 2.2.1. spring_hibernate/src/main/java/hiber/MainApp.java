package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User Denis = (new User( "Denis", "Terentyev", "Denis@mail.ru"));
        User Daniil =(new User( "Daniil", "Pyrkh", "Daniil@mail.ru"));
        User Aleksey =(new User("Aleksey", "Terentyev", "Aleksey@ mail.ru"));
        User Renat =(new User("Renat", "Araslanov", "Renat@mail.ru"));

        Car Audi = new Car("Audi", 5);
        Car Mercedes = new Car("Mercedes", 200);
        Car Volkswagen = new Car("Volkswagen", 2);
        Car Toyota = new Car("Toyota", 1);
        Car AudiBad = new Car("AudiBad", 100);

          userService.add(Denis.setCar(Toyota).setUser(Denis));
          userService.add(Daniil.setCar(Audi).setUser(Daniil));
          userService.add(Aleksey.setCar(Mercedes).setUser(Aleksey));
          userService.add(Renat.setCar(Volkswagen).setUser(Renat));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = "+user.getId());
            System.out.println("First Name = "+user.getFirstName());
            System.out.println("Last Name = "+user.getLastName());
            System.out.println("Email = "+user.getEmail());
            System.out.println();
        }

       //  user + car
        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        // user by car
        User user = userService.getUserByCar("Toyota", 1);
        System.out.println(user.toString());

        // не существующий user+car
        try {
            System.out.println(userService.getUserByCar("Audi", 6));
        } catch (NoResultException e) {
            System.out.println("Пользователь с авто " + AudiBad + " не найден");
        }
        context.close();
    }
}


/* 1. AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

          - создаём на основе конфигурационного класса "AppConfig" с помощью класса AnnotationConfigApplicationContext
            из Framework Spring контейнер Spring для управления бинами и их зависимостями

   2. UserService userService = context.getBean(UserService.class);

          - создаём объект "userService", т.е. мы из контейнера Spring c помощью метода getBean() объекта context
            запрашиваем объект типа "UserService" из контейнера Spring, который Spring, на основе его конфигурациии и
            управляемых зависимостей возвращает этот объект. Это позволит нам ипользовать этот объект для выполнения
            операций и вызова методов, определённых в классе "UserService".

   3. Создаём объекты "User" c полями: firstName, lastName, email.

        User Denis = (new User("Denis", "Terentyev", "Denis@mail.ru"));
        User Daniil =(new User("Daniil", "Pyrkh", "Daniil@mail.ru"));
        User Aleksey =(new User("Aleksey", "Terentyev", "Aleksey@mail.ru"));
        User Renat =(new User("Renat", "Araslanov", "Renat@mail.ru"));

   4. Создаём объекты "Car" c полями: model, series.

        Car Audi = new Car("Audi", 5);
        Car AudiBad = new Car("Audi", 6);
        Car Mercedes = new Car("Mercedes", 200);
        Car Volkswagen = new Car("Volkswagen", 2);
        Car Toyota = new Car("Toyota", 1);

   5. Извлекаем список пользователей из сервиса "userService". В этом списке будут содержаться все доступные пользователи.
      Далее в цикле for перебираем список "users" и каждый объект на каждой итерации с соответствующими полями
      выводим в консоль.

       List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = "+user.getId());
            System.out.println("First Name = "+user.getFirstName());
            System.out.println("Last Name = "+user.getLastName());
            System.out.println("Email = "+user.getEmail());
            System.out.println();

   6. Создаем объекты пользователлей Denis, Renat, Daniil, Aleksey устанавливает им объекты автомобиля Audi, Toyota, Mercedes, Volkswagen  и затем
      передаём этих пользователей Denis, Renat, Daniil, Aleksey для добавления в сервис userService.

        userService.add(Denis.setCar(Audi).setUser(Denis));
        userService.add(Renat.setCar(Toyota).setUser(Renat));
        userService.add(Daniil.setCar(Mercedes).setUser(Daniil));
        userService.add(Aleksey.setCar(Volkswagen).setUser(Aleksey));
        }

   7. В условиях этого цикла, мы с помощью метода "userService.listUsers()" извлекаем список пользователей с их
      автомобтлями и далее цикл проходит по каждому пользователю и на каждой итерации возвращает пользователя и
      автомобиль этого пользователя.

         for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

   8. Извлекааем из сервиса "userService" пользователя по авто: модели и серии

         System.out.println(userService.getUserByCar("Audi", 5));


                */
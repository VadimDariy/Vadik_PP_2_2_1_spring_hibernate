package hiber.service;

import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserByCar(String model, int series) {
      return userDao.getUserByCar(model, series);
   }
   @Transactional(readOnly = true)
   @Override
   public User getUserByCarS(Car car) {
      return userDao.getUserByCarS(car);
   }

}

/*
    1. public class UserServiceImp implements UserService

        - Объявляем класс "UserServiceImp", реализуем в нем интерфейс "UserService" и помечаем его аннотацией @Service
          что бы Spring Framework понимал, что этот класс используется как служба (сервис), позволяющая контейнеру
          Spring  управлять ими, автоматически обнаруживать и регистрировать в контексте приложения.
    2. @Autowired
       private UserDao userDao; с помощью аннотации @Autowired, Spring внедряет объект в переменную userDao.

    3. @Transactional: Эта аннотация указывает Spring на то, что метод должен выполняться в рамках транзакции.
                       Транзакция представляет собой логически связанный набор операций базы данных, который либо успешно
                       выполняется целиком, либо полностью отменяется в случае ошибки. Это обеспечивает целостность
                       данных и консистентность базы данных.

    4. @Override - переопределяем методы из интерфейса UserService
          */
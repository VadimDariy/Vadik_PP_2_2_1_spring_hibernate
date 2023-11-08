package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) { // добавить нового пользователя
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(String model, int series) {
      String hql = "from User user where user.car.model = :model and user.car.series = :series";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
      query.setParameter("model", model).setParameter("series", series);
      return query.setMaxResults(1).getSingleResult();
   }
}

/*
String hql = "from User user where user.car.model = :model and user.car.series = :series";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
      query.setParameter("model", car.getModel()).setParameter("series", car.getSeries());
      return query.setMaxResults(1).getSingleResult();

@Repository
public class UserDaoImp implements UserDao - объявляем класс "UserDaoImp", который реализует интерфейс "UserDao", и далее
                                   помечаем этот класс аннотацией "@Repository", и когда Spring будет  видеть эту
                                   аннотацию, то будет понимать, что этот класс является компонентом, содержащий методы
                                   с помощью которых класс(репозиторий) будет взаимодействовать с данными сущности.

     @Autowired
   private SessionFactory sessionFactory; - Объявляем переменную "sessionFactory" типа "SessionFactory" и помечаем её
                                  аннотацией "@Autowired" для внедрения объекта "SessionFactory", что бы использовать
                                  его для взаимодействия с базой данных через Hibernate.

-----------------------------------------------------------------------------------------------------------------------

    ДАЛЕЕ ПЕРЕОПРЕДЕЛЯЕМ МЕТОДЫ ИЗ ИНТЕРФЕЙСА "UserDao":

    1. @Override
       public void add(User user) { // добавить нового пользователя
       sessionFactory.getCurrentSession().save(user);
       }
       - объявляем метод add(User user) для добавления нового пользователя передставленного объектом User в БД. Для
         этого мы на объекте "sessionFactory" вызываем метод "getCurrentSession()", который создаст текущую сессию
         Hibernate, позволяющая обеспечить транзакцию для сохранения нового пользователя в БД.

 -----------------------------------------------------------------------------------------------------------------------

   2. @Override
      @SuppressWarnings("unchecked")
      public List<User> listUsers() { // извлечь всех пользователей из таблицы
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
      }
      а) @SuppressWarnings("unchecked") - это аннотация, предупреждает, что будет выполнено преобразование
        типа данных (cast) из результата запроса. В данном случае, запрос возвращает объекты User, и эта аннотация
        позволяет компилятору проигнорировать предупреждение о непроверенном преобразовании типа.

      б) public List<User> listUsers() - объявляем метод, который будет нам возвращать список пользователей типа "User";
                                        имя метода "listUsers".

      в) TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");

             - createQuery("from User") - создается запрос, который выбирает все записи объектов типа User из таблицы в
                                          базе данных.
             - параметр ("from User") - это запрос "Hibernate" означающий "выбрать все записи из таблицы User". Hibernate
                                        будет выполнять этот запрос к БД, в результате будет список объектов "User",
                                        представляющих все записи в таблице User.
             - sessionFactory.getCurrentSession() - получает текущую сессию Hibernate для выполнения запроса.
             - TypedQuery<User> query - создает подготовленный запрос query с ожидаемым типом данных User. Это
                                        обеспечивает типовую безопасность при работе с результатами запроса.

     По итогу мы с помощью метода "createQuery("from User")" создаём запрос, который выбирает все записи объектов из
     таблици в БД, а далее вызов метода "getCurrentSession()" на объекте "sessionFactory", будет использован для
     выполнения этого запроса в подготовленный объект-запрос "query", с типом данных TypedQuery<User>. А далее эту
     схему по выполнению запроса "Hibernate" запустит вызов метода "getResultList()" на объекте "query".

----------------------------------------------------------------------------------------------------------------------

    3. @Override
       @SuppressWarnings("unchecked")
       public User getUserByCar(String model, int series) { // получить пользователя по авто: модели и серии
       String hql = "from User user where user.car.model = :model and user.car.series = :series";
       TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
       query.setParameter("model", model).setParameter("series", series);
       return query.setMaxResults(1).getSingleResult();
   }

       Этот метод getUserByCar предназначен для получения пользователя (объект User) по данным автомобиля (модели и
       серии):

      а) String hql = "from User user where user.car.model = :model and user.car.series = :series";

         HQL (Hibernate Query Language) запрос, где:
         - from User user - Запрос начинается с выбора всех записей из таблицы, связанной с сущностью User. User - это
           класс, который представляет объекты пользователя.
         - where user.car.model = :model - Здесь устанавливается условие для выбора записей. Он говорит, что мы хотим
           выбрать записи, где значение поля "model" в свойстве "car" объекта "user" соответствует значению параметра
           :model. :model - это именованный параметр запроса, который будет заменен на конкретное значение при выполнении
           запроса.
         - and user.car.series = :series - Это дополнительное условие, которое связано с первым условием оператором "и"
           (AND). Он указывает, что также нужно проверить, чтобы значение поля series в свойстве car объекта user
           соответствовало значению параметра :series. :series - это еще один именованный параметр запроса.

      б) TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);

         Создается типизированный запрос query с ожидаемым типом данных User на основе HQL запроса hql. Запрос будет
         выполнен на текущей сессии Hibernate.

      в) query.setParameter("model", model).setParameter("series", series);

         Параметр "model" указывает  на имя параметра в HQL запросе, а конкретно на  :model, таким образом компилятор
         понимает, что при вызове метода setParameter("model", model), поставленный аргумент-значение, например "3" в
         параметр model - будет подставлено вместо :model = 3. То же самое касается "series" и series.

      г) return query.setMaxResults(1).getSingleResult();

         при вызове метода setMaxResults(1) будет вызван только один объект, это видно по аргументу этого метода.
         а конкретно какой объект, это определяют параметры в методе:
         query.setParameter("model", model).setParameter("series", series);
*/
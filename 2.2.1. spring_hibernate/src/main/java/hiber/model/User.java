package hiber.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "email")
   private String email;

    @MapsId
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

   public User() {
   }

   public User(String firstName, String lastName, String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

    public long getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Car getCar() {
      return car;
   }

   public Car setCar(Car car) {
      this.car = car;
      return car;
   }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", car=" + car +
                '}';
    }
}

/* 1. @Entity - аннотация в JPA (Java Persistence API/Application Programming Interface/Интерфейс прикладного
             программирования Java Persistence), используемая для обозначения класса как сущности, которая может
             быть сохранена в базе данных. Класс, помеченный @Entity, соответствует таблице в базе данных, а его поля
             представляют столбцы в этой таблице.

   2. @Table(name = "users") - создадим с помощью этой аннотации имя таблицы.

   3. @Id - аннотация, обозначающая поле id как первичный ключ сущности.
      @GeneratedValue(strategy = GenerationType.IDENTITY) - указывает, что значения первичного ключа будут автоматически
      генерироваться базой данных при вставке новых записей.

   4. @Column(name = "name")
      private String firstName;

      @Column(name = "last_name")
      private String lastName;

      @Column(name = "email")
      private String email;

      - С помощью аннотации "@Column(name = "name")" мы сначало инициализируем столбец в БД именем "name", а потом
        связываем поле "private String firstName" с этим столбцом("name") в таблице БД.
      - Тоже самое выполняем и для полей "private int firstName;", "private String lastName;", "private String email;"

   5. Создаём в классе "User" поле "private Car car;", которое будет содержать в себе класс "Car" и аннотируем его
      аннотацией:

      - @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
       эта аннотация "Один К Одному" применяется к связной сущности, которая будет обозначать отношения двух объектов
       одного в другом. В отношениях которых будет применяться каскад операций "cascade", всех типов "CascadeType.ALL",
       т.е. (вставка, обновление, удаление).
     - mappedBy = "user" указывает на свойство user в классе Car, которое является обратной стороной отношения
       'один к одному' между User и Car. Это означает, что Car является владельцем этой связи и информация о связи
       хранится в таблице Car через столбец, указанный в аннотации @JoinColumn(name = "user_id"). Таким образом,
       mappedBy определяет, как устанавливается отношение и как оно хранится в базе данных."
       */
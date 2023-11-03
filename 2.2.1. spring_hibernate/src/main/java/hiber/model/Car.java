package hiber.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    public Car() {}

    public Car(String model, int series) {
        this.model = model;
        this.series = series;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "series")
    private int series;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public User getUser() {
        return user;
    }

    public User setUser(User user) {
        this.user = user;
        return user;
    }

    @Override
    public String toString() {
        return "Car {" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", series=" + series +
                '}';
    }
}

/* 1. @Entity - аннотация в JPA (Java Persistence API/Application Programming Interface/Интерфейс прикладного
             программирования Java Persistence), используемая для обозначения класса как сущности, которая может
             быть сохранена в базе данных. Класс, помеченный @Entity, соответствует таблице в базе данных, а его поля
             представляют столбцы в этой таблице.

   2. @Table(name = "cars") - создадим с помощью этой аннотации имя таблицы.

   3. @Id - аннотация, обозначающая поле id как первичный ключ сущности.
      @GeneratedValue(strategy = GenerationType.IDENTITY) - указывает, что значения первичного ключа будут автоматически
      генерироваться базой данных при вставке новых записей.

   4. @Column(name = "model")
      private String model;

      @Column(name = "series")
      private int series;

      - С помощью аннотации "@Column(name = "model")" мы сначало инициализируем столбец в БД именем "model", а потом
        связываем поле "private String model" с этим столбцом("model") в таблице БД.
      - Тоже самое выполняем и для поля "private int series;"

   5. Создаём в классе "Сar" поле "private User user;", которое будет содержать в себе класс "User" и аннотируем его
      аннотациями:

      - @OneToOne(cascade = CascadeType.ALL);
       эта аннотация "Один К Одному" применяется к связной сущности, которая будет обозначать отношения двух объектов
       одного в другом. В отношениях которых будет применяться каскад операций "cascade", всех типов "CascadeType.ALL",
       т.е. (вставка, обновление, удаление).
     - @JoinColumn(name = "user_id");
       эта аннотация указывает на уже существующую колонку в таблице объекта "Car", имя которой "user_id". Она
       будет использованна для установки связи с таблицей "User" по внешнему ключу. Этот внешний ключ будет связывать
       записи в таблице Car с записями в таблице User, позволяя создавать связь между этими двумя сущностями в базе
       данных.   */
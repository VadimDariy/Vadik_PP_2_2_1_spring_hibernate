package hiber.config;

import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "hiber")
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() { // получить источник данных
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean getSessionFactory() { // получить фабрику сеансов
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setDataSource(getDataSource());
      
      Properties props = new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

      factoryBean.setHibernateProperties(props);
      factoryBean.setAnnotatedClasses(User.class, Car.class);
      return factoryBean;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() { // получить менеджера транзакций
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }
}

/*  Class AppConfig - Создаём конфигурационный класс, внутри которого пишем методы и помечаем их аннотацией "@Bean",
                      для того, что бы Spring сам создавал биновые экземпляры и внедрял их по соответствию типов данных.
                      А так же над классом указываем аннотации: @Configuration
                                                                @PropertySource("classpath:db.properties")
                                                                @EnableTransactionManagement
                                                                @ComponentScan(value = "hiber")

    @Configuration -  когда Spring видит эту аннотацию, он создаёт биновые объекты у себя в контейнере Spring,
                      готовые к внедрению, и внедряет их строго по соответствию типов данных.

    @PropertySource("classpath:db.properties") - когда Spring видит эту аннотацию, в параметрах которой указан адресс к
                      к файлу "db.properties", в котором прописаны настройки доступа к базе данных, он сканирует эту
                      информацию и сохраняет у себя к контейнере Spring. Данный файл позволяет хранить данные настроики
                      подключения к БД и др., без необходимости хранения их в коде.

    @EnableTransactionManagement - Когда Spring видит эту аннотацию, то он получает дополнительный функционал для
                                   управления транзакциями в приложении, в виде операций, которые выполняются как единое
                                   целое, обеспечивая атомарность, согласованность, изолированность и долговечность
                                   данных. Например: перевод денег, бронирование билетов или же управление инвентарем.

   @ComponentScan(value = "hiber") - Когда Spring видит эту аннотацию, то он понимает какую область нужно будет
                                     сканировать что бы обнаружить все компоненты и добавить их в контейнере Spring.

   @Autowired - Когда Spring видит эту аннотацию, то Spring автоматически внедряет (присваивает) эту зависимость, а в
                данном случае зависьмость типа "Environment" в поле "private Environment env", позволяя классу
                использовать настройки и свойства приложения.

   Environment - этот интерфейс, предоставляющий доступ к настройкам и свойствам приложения. Spring автоматически
                 создает объект типа Environment и внедряет его в класс, чтобы обеспечить доступ к настройкам из файла
                 "db.properties".
  ---------------------------------------------------------------------------------------------------------------------

   ОПИСАНИЕ МЕТОДА: public DataSource getDataSource()

   @Bean - помечаем метод "getDataSource()" этой аннотацией, что бы Spring сам создавал и управлял экземпляром этого
           объекта в контексте приложения, вплоть до внедрения текущего объекта в код по соответствующий типу  данных;

   public DataSource getDataSource() - Объявляем метод, который будет возвращать объект, реализущий интерфейс "DataSource"
                                       и отвечать за установления соединения с базой данных.
   DriverManagerDataSource dataSource = new DriverManagerDataSource();-
                                       Создаем объект класса "DriverManagerDataSource", в который мы пропишем все
                                       настройки для управления соединением с БД, включая: driver, url, username и password .
                                       "DriverManagerDataSource" является одной из реализаций интерфейса "DataSource"
                                       для управления соединением с БД.

   -прописываем драйвер:
  dataSource.setDriverClassName(env.getProperty("db.driver")); где,
      "db.driver" - это переменная, хранящая в себе значения имени класса драйвера в файле db.proherties
                    "com.mysql.cj.jdbc.Driver"
       getProperty("db.driver") - это метод получает значение настройки по заданному ключу ("db.driver") из файла
                                  db.properties Т.е. в этом файле ключ "db.driver" содержит в себе значение имя класса
                                  драйвера "com.mysql.cj.jdbc.Driver" и инициализирует им объект "env".
       setDriverClassName() - устанавливает имя класса драйвера(com.mysql.cj.jdbc.Driver), полученный в параметрах
                              методом getProperty(), который будет использоваться DriverManagerDataSource для создания
                              подключения к базе данных.

       Далее остальные три строки метода "DataSource getDataSource()" работают точно так же, как описан выше метод
  setDriverClassName():
  dataSource.setUrl(env.getProperty("db.url")); - возвращает url = jdbc:mysql://localhost:3306/vadim;
  dataSource.setUsername(env.getProperty("db.username")); - возвращает username = root;
  dataSource.setPassword(env.getProperty("db.password")); - возвращает password = 19nastena79;

  Таким образом объект "dataSource" класса "DriverManagerDataSource" содержит в себе конкретные настройки:

         1. driver = com.mysql.cj.jdbc.Driver,
         2. url = jdbc:mysql://localhost:3306/vadim,
         3. username = root,
         4. password = 19nastena79.
---------------------------------------------------------------------------------------------------------------------

  ОПИСАНИЕ МЕТОДА: LocalSessionFactoryBean getSessionFactory()

  @Bean - помечаем метод "LocalSessionFactoryBean getSessionFactory()" этой аннотацией, что бы Spring сам создавал и
          управлял экземпляром этого объекта в контексте приложения, вплоть до внедрения текущего объекта в код по
          соответствующий типу  данных;

  LocalSessionFactoryBean getSessionFactory() - объявляем метод, который будет возвращать экземпляр класса
                                                LocalSessionFactoryBean из Spring для интеграции Hibernate в контейнер
                                                Spring и настройки SessionFactory для взаимодействия с Hibernate в
                                                приложении.

   LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean(); - Создаём объект "factoryBean" класса
                                         "LocalSessionFactoryBean" в которы мы передадим настройки для подключения к БД

   factoryBean.setDataSource(getDataSource()); - с помощью метода "setDataSource()", устанавливаем подключение к БД
                                          в параметрах которого находится метод "getDataSource()", который возвращает
                                          объект "DataSource" содержащий все настройки описываемый выше.

   Properties props = new Properties(); - создаём объект "props" класса "Proherties", что бы передать в него настройки
                                          hibernate из файла db.properties.

    props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql")); - с помощью метода Put, который
                                          принимает у себя два параметра:
                                          а) ключ "hibernate.show_sql";
                                          б) метод "getProperty()", который получает значение "false" по ключу
                                          "hibernate.show_sql" из файла "db.properties" и передает его в объект "evn"
                                           А далее метод "put()" передаёт эти ключ и значение в объект "props" и затем
                                           это значение будет использоваться Hibernate для определения, нужно ли
                                           отображать SQL-запросы в консоли при выполнении операций с базой данных.
                                           Значение "false" указывает, что SQL-запросы не будут отображаться в консоли,
                                           в то время как значение "true" активировало бы вывод SQL-запросов.

   props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto")); - этот метод работает анологично
                                           предыдущему.

             Таким образом мы получили конкретные настройки для объекта "props":

             1. hibernate.show_sql=false
             2. hibernate.hbm2ddl.auto=update

   factoryBean.setHibernateProperties(props); - далее эти настройки мы устанавливаем в объект, который отвечает за
                                                подключение к БД.

   factoryBean.setAnnotatedClasses(User.class); - Эта строка указывает Hibernate, что класс User должен быть рассмотрен
                                                  как сущность, которая будет отображена на таблицу в базе данных.
----------------------------------------------------------------------------------------------------------------------

   ОПИСАНИЕ МЕТОДА: HibernateTransactionManager getTransactionManager()

   @Bean - помечаем метод "HibernateTransactionManager getTransactionManager()" этой аннотацией, что бы Spring сам
           создавал и управлял экземпляром этого объекта в контексте приложения, вплоть до внедрения текущего объекта в
           код по соответствующий типу  данных;

   HibernateTransactionManager getTransactionManager() - объявляем метод, который будет возвращать экземпляр класса
                                                         "HibernateTransactionManager" для управления транзакциями в
                                                         приложении, основываясь на Hibernate.

   HibernateTransactionManager transactionManager = new HibernateTransactionManager();- создаём объект "transactionManager"
                                     класса "HibernateTransactionManager" для предоставления Hibernate и служит для
                                     управления транзакциями в приложении.

   transactionManager.setSessionFactory(getSessionFactory().getObject()); - устанавливаем объект "SessionFactory" в
                                     менеджер транзакций "transactionManager", чтобы знать, какие данные в сеансах
                                     Hibernate он будет управлять в рамках транзакций.

   getObject() - это метод, используемый для извлечения реального объекта, представляемого прокси-объектом Spring, как в
                 случае получения настоящего SessionFactory из LocalSessionFactoryBean.
 */
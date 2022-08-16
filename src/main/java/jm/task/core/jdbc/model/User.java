/**
 * Практическая задача
 * В этом задании мы познакомимся с такими технологиями, как:
 *
 *  Maven  - https://maven.apache.org/
 *  JDBC - https://o7planning.org/ru/10167/java-jdbc-tutorial
 *
 *
 * Для выполнения задания необходимо:
 *
 *  Клонировать/скачать заготовку по ссылке
 *  Установить на компьютер MySqlServer и Workbench. Создать подключение и схему через Workbench. Протестировать возможность соединения с базой через идею с помощью встроенной утилиты, как показано на рисунке:
 * mysql
 *
 *
 *
 * Описание задачи:
 *
 * Необходимо ознакомиться с заготовкой и доработать приложение, которое взаимодействует с базой оперируя пользователем ( класс User ) и проверить свои методы заранее написанными JUnit тестами. По итогу все тесты должны быть пройдены. Разрешается посмотреть реализацию тестов.
 *
 * Для запуска теста необходимо найти класс в папке test ( показано в предыдущей лекции ) и при нажатии на него правой кнопкой мыши запустить, выбрав Run "Имя класса"
 *
 * Класс UserHibernateDaoImpl в рамках этой задачи не затрагивается (остаётся пустой)
 *
 * User представляет из себя сущность с полями:
 *
 * Long id
 * String name
 * String lastName
 * Byte age
 *          Архитектура приложения создана с опорой на паттерн проектирования MVC ( частично, у нас не WEB приложение)
 *
 * Ознакомиться с паттерном можно здесь
 *
 *
 *
 * Требования к классам приложения:
 *
 *  Классы dao/service должны реализовывать соответствующие интерфейсы
 *  Класс dao должен иметь конструктор пустой/по умолчанию
 *  Все поля должны быть private
 *  service переиспользует методы dao
 *  Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
 *  Класс Util должен содержать логику настройки соединения с базой данных
 *
 *
 * Необходимые операции:
 *
 *  Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
 *  Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
 *  Очистка содержания таблицы
 *  Добавление User в таблицу
 *  Удаление User из таблицы ( по id )
 *  Получение всех User(ов) из таблицы
 *
 *
 *         Алгоритм работы приложения:
 *
 *          В методе main класса Main должны происходить следующие операции:
 *
 *  Создание таблицы User(ов)
 *  Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 *  Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 *  Очистка таблицы User(ов)
 *  Удаление таблицы
 */
package jm.task.core.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("User {id = '%s', name = '%s', lastName = '%s', age = '%d'}", getId(), getName(), getLastName(), getAge());
    }
}

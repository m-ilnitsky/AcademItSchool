package ru.academit.ilnitsky.lectures;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Задачи с предпоследней лекции
 * Created by Mike on 11.12.2016.
 */
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        List<Person> list = Arrays.asList(
                new Person("Вася", 19),
                new Person("Коля", 16),
                new Person("Юля", 25),
                new Person("Рая", 18),
                new Person("Вася", 27),
                new Person("Юля", 17));

        System.out.println("1.Список уникальных имён.");
        Stream<String> stream = list.stream().map(x -> x.getName()).distinct();
        String namesSet = stream.collect(Collectors.joining(", ", "Имена: ", "."));
        System.out.println(namesSet);

        System.out.println("\n2.Средний возраст тех кто младше 18 лет.");
        OptionalDouble averageAge = list.stream().filter(x -> x.getAge() < 18).mapToInt(x -> x.getAge()).average();
        System.out.println("Средний возраст несовершеннолетних: " + averageAge.getAsDouble());

        System.out.println("\n3.Человеческие особи в возрасте от 20 до 45 лет.");
        String names = list.stream().filter(x -> x.getAge() >= 20 && x.getAge() <= 45).sorted((p1, p2) -> p2.getAge() - p1.getAge()).map(x -> x.getName()).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(names);

        System.out.println("\n4.Средний возраст для каждого имени.");
        Map<String, OptionalDouble> mapOfNames = list.stream().map(x -> x.getName()).distinct().collect(Collectors.toMap(
                p -> p,
                p -> list.stream().filter(x -> x.getName().equals(p)).mapToInt(x -> x.getAge()).average())
        );
        mapOfNames.forEach((name,age)->System.out.println(name + ": " + age.getAsDouble()));
    }
}

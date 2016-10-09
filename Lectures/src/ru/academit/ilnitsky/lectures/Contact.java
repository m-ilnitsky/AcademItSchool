package ru.academit.ilnitsky.lectures;

/**
 * Created by Mike on 09.10.2016.
 * Контакты человека
 */
public class Contact {
    private String name;
    private String surname;
    private String phone;

    public Contact(String name, String surname, String phone) {
        set(name, surname, phone);
    }

    public Contact() {
        this.name = "";
        this.surname = "";
        this.phone = "";
    }

    public void set(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String toString() {
        return String.format("И: %-8s Ф: %-15s Т: %-7s", name, surname, phone);
    }

    public static void main(String[] args) {
        Contact[] contact = new Contact[4];

        contact[0]=new Contact("Вася","Пупкин","12345");
        contact[1]=new Contact("Коля","Пупкин","12377");
        contact[2]=new Contact("Юля","Василькова","12775");
        contact[3]=new Contact("Оля","Шаландова","12771");

        for (Contact c: contact) {
            System.out.println(c);
        }

        contact[0].setName("Дуся");
        contact[1].setSurname("Попкин");
        contact[2].setPhone("!666!");
        contact[3].setName("Воля");
        contact[3].setSurname(contact[3].getSurname()+"нович");

        System.out.println();
        for (Contact c: contact) {
            System.out.println(c);
        }
    }
}

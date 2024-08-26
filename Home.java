//Напишите приложение, которое будет запрашивать у пользователя следующие данные,
//разделенные пробелом: Фамилия Имя Отчество дата_рождения номер_телефона пол
//Форматы данных: фамилия, имя, отчество - строки
//дата_рождения - строка формата dd.mm.yyyy
//номер_телефона - целое беззнаковое число без форматирования
//пол - символ латиницей f или m.
//Приложение должно проверить введенные данные по количеству. Если количество не совпадает, вернуть код
//ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
//Приложение должно распарсить полученную строку и выделить из них требуемые значения. Если форматы данных
//        не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы
//java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с
//информацией, что именно неверно.
//Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку
//должны записаться полученные данные, вида
//<Фамилия><Имя><Отчество><дата_рождения> <номер_телефона><пол>
//Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//Не забудьте закрыть соединение с файлом.
//При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
//пользователь должен увидеть стектрейс ошибки.


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;



public class Home {
    private static final int fields_number =6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в одну строку раделенные пробелами в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол");
        String input = scanner.nextLine();
        scanner.close();
        User User1 = ParsStr(input);
        System.out.println("Вы ввели следующие данные: ");
        System.out.println(User1);
        outToFile(User1);

    }




    public static User ParsStr(String str){//функция парсинга строки в User
        String[] filds = str.split(" ");
        if (filds.length != fields_number){
            System.err.println("Ошибка: Вы ввели количество полей равное: "+filds.length+", а должно быть: "+fields_number);
        }

        User user = new User();
            user.LastName = filds[0];//Фамилия
            user.FirstName = filds[1];//Имя
            user.MiddleName = filds[2]; //Отчество
            try {//присвоение даты рождения с проверкой
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                user.Birthday = LocalDate.parse(filds[3],formatter);

            } catch (DateTimeException e) {
                throw new RuntimeException("Не верный формат даты");
            }
            try{//Присвоение номера телефона с проверкой
                user.phoneNumber = Long.parseLong(filds[4]);
            } catch (NumberFormatException e){
                System.out.println("Не верный формат телефона");

            }
            if ((!"m".equals(filds[5])) && (!"f".equals(filds[5]))){
                System.err.println("В поле 'пол' долженбыть либо 'f' - женский, либо 'm'- мужской");
            } else { user.gender = filds[5];
            }
        return user; //Возвращаем заполненного userКузнецов Максим Александрович 11.11.1111 456 m
    }//конец ункции парсинга

    public static void outToFile(User user){//Функция записи в файл
        String fileName = user.LastName+".txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))){
            writer.write(user.toString()+"\n");
        } catch (IOException e){
            System.out.println("Ошибка записи в файл");
        }


    }//Функция записи в файл

}

class User{
    String LastName;
    String FirstName;
    String MiddleName;
    LocalDate Birthday;
    long phoneNumber;
    String gender;


    @Override
    public String toString() {
        return LastName+" "+FirstName+" "+MiddleName+" "+Birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+" "+phoneNumber+" "+gender;
    }
}
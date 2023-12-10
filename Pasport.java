import java.io.*;
import java.text.*;
import java.util.*;
/*Напишите приложение, которое будет запрашивать у пользователя следующие данные , разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол, для удобства, все данные можете представить в виде строк.

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида

<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

Не забудьте закрыть соединение с файлом.

При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки. */
public class Pasport {

    public static void main(String[] args) {
        // Создаем объект сканера для считывания ввода пользователя
        Scanner scanner = new Scanner(System.in,"ibm866");

        // Запрашиваем фамилию, имя и отчество
        System.out.println("Введите фамилию:");
        String surname = scanner.nextLine();
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите отчество:");
        String patronymic = scanner.nextLine();

        // Запрашиваем дату рождения в формате dd.MM.yyyy
        System.out.println("Введите дату рождения в формате dd.MM.yyyy:");
        String dateOfBirth = scanner.nextLine();

        // Запрашиваем номер телефона в формате XXXXXXXXX
        System.out.println("Введите номер телефона без знаков ");
        String phoneNumber = scanner.nextLine();

        // Запрашиваем пол (f или m)
        System.out.println("Введите пол (f или m):");
        String gender = scanner.nextLine();

        // Проверяем, что введенные данные соответствуют требуемым форматам
        try {
            // Проверяем, что фамилия, имя и отчество состоят из букв и пробелов
             if (!surname.matches("[А-Яа-яЁё\\s]+") || !name.matches("[А-Яа-яЁё\\s]+") || !patronymic.matches("[А-Яа-яЁё\\s]+")) {
                 throw new InvalidNameException("Фамилия, имя и отчество должны состоять из букв и пробелов");
             }
        
            // Проверяем, что дата рождения соответствует формату dd.MM.yyyy и является валидной
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            dateFormat.setLenient(false); // Устанавливаем строгий режим проверки даты
            Date date = dateFormat.parse(dateOfBirth); // Пытаемся преобразовать строку в дату

            // Проверяем, что номер телефона соответствует формату XXXXXXXXX и является валидным
            if (!phoneNumber.matches("\\d+")) {
                throw new InvalidPhoneNumberException("Номер телефона должен соответствовать формату XXXXXXXXX");
            }

            // Проверяем, что пол соответствует одному из двух вариантов: m или f
            if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
                throw new InvalidGenderException("Пол должен быть одним из двух вариантов: m или f");
            }

            // Если все данные введены и обработаны верно, создаем файл с названием, равным фамилии
            File file = new File(surname + ".txt");
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            // Записываем в файл полученные данные в одну строку
            writer.write(surname +" "+ name +" "+ patronymic +" " + dateOfBirth + " " + phoneNumber + " "+ gender );
            writer.write("\n");
            writer.close();

            // Выводим сообщение об успешном создании файла
            System.out.println("Файл " + file.getName() + " успешно создан");

        } catch (InvalidNameException e) {
             // Обрабатываем исключение, связанное с неверным форматом фамилии, имени или отчества
             System.out.println(e.getMessage());
        } catch (ParseException e) {
            // Обрабатываем исключение, связанное с неверным форматом даты рождения
            System.out.println("Дата рождения должна соответствовать формату dd.MM.yyyy и быть валидной");
        } catch (InvalidPhoneNumberException e) {
            // Обрабатываем исключение, связанное с неверным форматом номера телефона
            System.out.println(e.getMessage());
        } catch (InvalidGenderException e) {
            // Обрабатываем исключение, связанное с неверным форматом пола
            System.out.println(e.getMessage());
        } catch (IOException e) {
            // Обрабатываем исключение, связанное с ошибкой ввода-вывода
            System.out.println("Произошла ошибка при работе с файлом");
        }
    }
}

// Создаем свои классы исключений для разных типов проблем
class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}

class InvalidGenderException extends Exception {
    public InvalidGenderException(String message) {
        super(message);
    }
}
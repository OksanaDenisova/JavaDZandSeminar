import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DZ2 {
    /*    Задание

        1) Дана строка sql-запроса "select * from students where ". Сформируйте часть WHERE этого запроса,
        используя StringBuilder или String. Данные для фильтрации приведены ниже в виде json-строки.
            Если значение null, то параметр не должен попадать в запрос.
            Параметры для фильтрации: {"name":"Ivanov", "country":"Russia", "city":"Moscow", "age":"null"}

            Дополнительные задания

        2) Дана json-строка (можно сохранить в файл и читать из файла)
        [{"фамилия":"Иванов","оценка":"5","предмет":"Математика"},
        {"фамилия":"Петрова","оценка":"4","предмет":"Информатика"},
        {"фамилия":"Краснов","оценка":"5","предмет":"Физика"}]
            Написать метод(ы), который распарсит json и, используя StringBuilder,
            создаст строки вида: Студент [фамилия] получил [оценка] по предмету [предмет].
            Пример вывода:
            Студент Иванов получил 5 по предмету Математика.
            Студент Петрова получил 4 по предмету Информатика.
            Студент Краснов получил 5 по предмету Физика.*/
    public static void main(String[] args) {
        task1();
        task2();
    }
    static void task1() {
        String sql = "select * from students where ";
        String stringSQL = "\"name\":\"Ivanov\", \"country\":\"Russia\", \"city\":\"Moscow\", \"age\":\"null\"";
        String[] tmpSQL = stringSQL.split(","); // разделили на подстроки
        StringBuilder clearSQL = new StringBuilder();
        for (String s : tmpSQL) {
            if (!s.contains("null")) clearSQL.append(s);
        }
        stringSQL = clearSQL.toString();
        String[] res = stringSQL.split(" ");
        String[] resTMP;

        StringBuilder result = new StringBuilder(sql);
        for (int i = 0; i < res.length; i++) {
            resTMP = res[i].split(":");
            resTMP[0] = resTMP[0].replaceAll("\"", "");
            resTMP[1] = resTMP[1].replaceAll("\"", "'");
            res[i] = String.join(" = ", resTMP[0], resTMP[1]);
            if (i!=res.length-1){result.append(res[i].concat(" AND "));}
            else result.append(res[i]);
        }
        System.out.println(result);
    }
    static void task2(){
        File file = new File("./src/HW2FileJSON.txt");
        String [] student;
        String name = "";
        String grade = "";
        String subject = "";
        try (Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine()) {
                student = scanner.nextLine().replaceAll("\"","").split(",");
                for (int i = 0; i < student.length; i++) {
                    name = student[0].substring(student[0].indexOf(":")+1);
                    grade = student[1].substring(student[1].indexOf(":")+1);
                    subject = student[2].substring(student[2].indexOf(":")+1);
                }
                System.out.printf("Студент %s получил %s по предмету %s\n",name,grade,subject);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        System.out.println("Программа завершена");
    }
}

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.mindrot.jbcrypt.BCrypt;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

class HashPassword {

    private static final int WORK_FACTOR = 12;

    public static String encodePasskey(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt(WORK_FACTOR));

    }

    public static boolean verifyPasskey(String enteredPassword, String storedPassword) {

        try {
            return BCrypt.checkpw(enteredPassword, storedPassword);

        } catch (IllegalArgumentException mistake) {

            return false;
        }

    }

    public static boolean isValidLength(String password) {

        if (password == null)
            return false;

        return password.getBytes(StandardCharsets.UTF_8).length <= 72;
    }

}

class Utils {

    public static String currentDate() {

        LocalDate today = LocalDate.now();
        DateTimeFormatter how = DateTimeFormatter.ofPattern("EEE MMMM dd, yyyy", Locale.ENGLISH);

        String currentDate = today.format(how);
        return currentDate;

    }

    public static String currentTime() {

        LocalTime rightNow = LocalTime.now();
        DateTimeFormatter how = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

        String currentTime = rightNow.format(how);
        return currentTime;
    }

}

class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int iD;
    private Schedule schedule;

    public User(String username, String password, String firstName, String lastName, int iD, Schedule schedule) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.schedule = schedule;
        this.iD = iD;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getID() {
        return iD;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}

class Student extends User {

    private ArrayList<GradeRecord> grades = new ArrayList<>();
    private int studentCode;
    private String degree;

    public Student(String username, String password, String firstName, String lastName, int iD, Schedule schedule,
            int studentCode, String degree) {

        super(username, password, firstName, lastName, iD, schedule);
        this.studentCode = studentCode;
        this.degree = degree;

    }

    public ArrayList<GradeRecord> getGrades() {
        return grades;
    }

    public int getStudentCode() {
        return studentCode;
    }

    public String getDegree() {
        return degree;
    }

    public Credential getCredential() {
        return new Credential(
                getFirstName(),
                getLastName(),
                getUsername(),
                getDegree(),
                getID());
    }

    public void setGrades(ArrayList<GradeRecord> grades) {
        this.grades = grades;
    }

    public void setStudentCode(int studentCode) {
        this.studentCode = studentCode;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

}

class Employee extends User {

    private int employeeNumber;

    public Employee(String username, String password, String firstName, String lastName, int iD, Schedule schedule,
            int employeeNumber) {

        super(username, password, firstName, lastName, iD, schedule);
        this.employeeNumber = employeeNumber;

    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

}

class Teacher extends Employee {

    private String department;

    public Teacher(String username, String password, String firstName, String lastName, int iD, Schedule schedule,
            int employeeNumber, String department) {

        super(username, password, firstName, lastName, iD, schedule, employeeNumber);
        this.department = department;

    }

    public String getDepartment() {
        return department;
    }

    public Credential getCredential() {
        return new Credential(
                getFirstName(),
                getLastName(),
                getUsername(),
                getDepartment(),
                getID());
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object x) {

        if (this == x)
            return true;

        if (x == null || this.getClass() != x.getClass())
            return false;

        Teacher newone = ((Teacher) x);
        return this.getUsername().equalsIgnoreCase(newone.getUsername());
    }

    @Override
    public int hashCode() {
        return getUsername().toLowerCase().hashCode();
    }

}

class Administrator extends Employee {

    private String position;

    public Administrator(int employeeNumber, String username, String password, String firstName, String lastName,
            int iD, Schedule schedule, String position) {

        super(username, password, firstName, lastName, iD, schedule, employeeNumber);
        this.position = position;

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Credential getCredential() {
        return new Credential(
                getFirstName(),
                getLastName(),
                getUsername(),
                getPosition(),
                getID());

    }

}

class GradeRecord {

    private Subject subject;
    private String groupCode;

    private double firstPartial;

    private double secondPartial;
    private double thirdPartial;
    private double finalGrade;

    public GradeRecord(Subject subject, String groupCode, double firstPartial, double secondPartial,
            double thirdPartial, double finalGrade) {

        this.subject = subject;
        this.groupCode = groupCode;
        this.firstPartial = firstPartial;
        this.secondPartial = secondPartial;
        this.thirdPartial = thirdPartial;
        this.finalGrade = finalGrade;

    }

    public Subject getSubject() {
        return subject;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public double getFirstPartial() {
        return firstPartial;
    }

    public double getSecondPartial() {
        return secondPartial;
    }

    public double getThirdPartial() {
        return thirdPartial;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setFirstPartial(double firstPartial) {
        this.firstPartial = firstPartial;
    }

    public void setSecondPartial(double secondPartial) {
        this.secondPartial = secondPartial;
    }

    public void setThirdPartial(double thirdPartial) {
        this.thirdPartial = thirdPartial;
    }

    public void setFinalGrade(double finalGrade) {
        this.finalGrade = finalGrade;
    }

}

class Subject {

    private String code;
    private String name;

    public Subject(String code, String name) {

        this.code = code;
        this.name = name;

    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class ClassSession {

    private String classCode;
    private String groupCode;
    private Subject subject;
    private String time;
    private String day;

    public ClassSession(String classCode, String groupCode, Subject subject, String time, String day) {

        this.classCode = classCode;
        this.groupCode = groupCode;
        this.subject = subject;
        this.time = time;
        this.day = day;

    }

    public Subject getSubject() {
        return subject;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDay(String day) {
        this.day = day;
    }

}

class Schedule {

    private ArrayList<ClassSession> sessions = new ArrayList<>();

    public Schedule() {
    }

    public ArrayList<ClassSession> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<ClassSession> sessions) {
        this.sessions = sessions;
    }

}

class Group {

    private Subject subject;
    private Teacher teacher;
    private ArrayList<Student> students = new ArrayList<>();
    private String groupCode;

    public Group(Subject subject, Teacher teacher, String groupCode) {

        this.subject = subject;
        this.teacher = teacher;
        this.groupCode = groupCode;
    }

    public Subject getSubject() {
        return subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

}

class Credential {

    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private int iD;

    public Credential(String firstName, String lastName, String username, String role, int iD) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.iD = iD;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public int getID() {
        return iD;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

}

class AcademicManagement {

    private RepositorySQL repo;
    private SecureRandom random = new SecureRandom();

    public AcademicManagement(RepositorySQL repo) {

        this.repo = repo;
    }

    public boolean usernameIsAvailable(String username) {

        if (repo.findStudentByUsername(username) != null)
            return false;

        if (repo.findTeacherByUsername(username) != null)
            return false;

        if (repo.findAdministratorByUsername(username) != null)
            return false;

        return true;

    }

    public String generatePassword() {

        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%&*";

        String characters = upperCase + lowerCase + numbers + symbols;

        char[] generatedPassword = new char[10];

        generatedPassword[0] = upperCase.charAt(random.nextInt(upperCase.length()));
        generatedPassword[1] = lowerCase.charAt(random.nextInt(lowerCase.length()));
        generatedPassword[2] = numbers.charAt(random.nextInt(numbers.length()));
        generatedPassword[3] = symbols.charAt(random.nextInt(symbols.length()));

        for (int i = 4; i < generatedPassword.length; i++) {
            generatedPassword[i] = characters.charAt(random.nextInt(characters.length()));
        }

        return new String(generatedPassword);

    }

    public int generateStudentCode() {

        int studentCode;

        do {
            studentCode = 10000 + random.nextInt(90000);

        } while (repo.findStudentByStudentCode(studentCode) != null);

        return studentCode;
    }

    public int generateEmployeeNumber() {

        int employeeNumber;

        do {
            employeeNumber = 20000000 + random.nextInt(1000000);

        } while (repo.findTeacherByEmployeeNumber(employeeNumber) != null
                || repo.findAdministratorByEmployeeNumber(employeeNumber) != null);

        return employeeNumber;

    }

    public String generateSubjectCode(String subjectName) {

        if (subjectName == null || subjectName.isEmpty())
            return null;

        String cleanName = subjectName.replace(" ", "").toUpperCase();

        if (cleanName.length() < 3)
            return null;

        String letters = cleanName.substring(0, 3);
        String code;

        do {
            int number = 1000 + random.nextInt(9000);
            code = letters + number;

        } while (repo.findSubjectByCode(code) != null);

        return code;

    }

    public String generateGroupCode() {

        String groupCode;

        do {
            int number = 10000 + random.nextInt(90000);
            groupCode = String.valueOf(number);

        } while (repo.findGroupByCode(groupCode) != null);

        return groupCode;

    }

    public String generateClassCode() {

        String classCode;

        do {
            int number = 100000 + random.nextInt(900000);
            classCode = String.valueOf(number);

        } while (repo.findClassSessionByCode(classCode) != null);

        return classCode;
    }

    public User findUserByUsername(String username) {

        User user = repo.findStudentByUsername(username);

        if (user != null)
            return user;

        user = repo.findTeacherByUsername(username);
        if (user != null)
            return user;

        return repo.findAdministratorByUsername(username);
    }

    public Student findStudentByUsername(String username) {

        return repo.findStudentByUsername(username);
    }

    public Teacher findTeacherByUsername(String username) {

        return repo.findTeacherByUsername(username);
    }

    public Administrator findAdministratorByUsername(String username) {

        return repo.findAdministratorByUsername(username);
    }

    public Student findStudentByStudentCode(int studentCode) {

        return repo.findStudentByStudentCode(studentCode);
    }

    public Teacher findTeacherByEmployeeNumber(int employeeNumber) {

        return repo.findTeacherByEmployeeNumber(employeeNumber);
    }

    public Administrator findAdministratorByEmployeeNumber(int employeeNumber) {

        return repo.findAdministratorByEmployeeNumber(employeeNumber);
    }

    public Subject findSubjectByCode(String subjectCode) {

        return repo.findSubjectByCode(subjectCode);
    }

    public Group findGroupByCode(String code) {

        return repo.findGroupByCode(code);
    }

    public Student findStudentInGroup(String groupCode, String username) {

        return repo.findStudentInGroup(groupCode, username);
    }
    // ================== done so far ===================

    public boolean validateStudentLogIn(String username, String password) {

        Student user = findStudentByUsername(username);
        if (user == null)
            return false;

        String storedPassword = user.getPassword();
        boolean valid = HashPassword.verifyPasskey(password, storedPassword);
        return valid;

    }

    public boolean validateTeacherLogIn(String username, String password) {

        Teacher user = findTeacherByUsername(username);
        if (user == null)
            return false;

        String storedPassword = user.getPassword();
        boolean valid = HashPassword.verifyPasskey(password, storedPassword);
        return valid;
    }

    public boolean validateAdministratorLogIn(String username, String password) {

        Administrator user = findAdministratorByUsername(username);
        if (user == null)
            return false;

        String storedPassword = user.getPassword();
        return HashPassword.verifyPasskey(password, storedPassword);

    }

    public Schedule getStudentSchedule(String username) {

        Student student = findStudentByUsername(username);

        if (student == null)
            return null;

        return repo.getStudentSchedule(student.getStudentCode());

    }

    public Schedule getTeacherSchedule(String username) {

        Teacher teacher = findTeacherByUsername(username);

        if (teacher == null)
            return null;

        return repo.getTeacherSchedule(teacher.getEmployeeNumber());
    }

    public Credential getStudentCredential(String username) {

        Student user = findStudentByUsername(username);
        if (user == null)
            return null;

        return user.getCredential();

    }

    public Credential getTeacherCredential(String username) {

        Teacher user = findTeacherByUsername(username);
        if (user == null)
            return null;

        return user.getCredential();
    }

    public ArrayList<Group> getGroupsOfTeacher(Teacher teacher) {

        if (teacher == null)
            return new ArrayList<>();

        return repo.getGroupsOfTeacher(teacher.getEmployeeNumber());
    }

    public ArrayList<Student> getStudentsFromGroup(String groupCode) {

        if (groupCode == null || groupCode.isEmpty())
            return new ArrayList<>();

        return repo.getStudentsFromGroup(groupCode);

    }

    public GradeRecord getGradeFromStudent(String groupCode, String username) {

        if (groupCode == null || groupCode.isEmpty())
            return null;

        if (username == null || username.isEmpty())
            return null;

        Student student = findStudentInGroup(groupCode, username);

        if (student == null)
            return null;

        return repo.getGradeFromStudent(student.getStudentCode(), groupCode);
    }

    public boolean assignStudentGrade(String groupCode, String username, int partialOption, double newGrade) {

        if (partialOption < 1 || partialOption > 4)
            return false;

        Student student = findStudentInGroup(groupCode, username);
        if (student == null)
            return false;

        GradeRecord record = repo.getGradeFromStudent(student.getStudentCode(), groupCode);
        if (record == null)
            return false;

        return repo.updateGrade(student.getStudentCode(), groupCode, partialOption, newGrade);

    }

    public ArrayList<GradeRecord> getStudentGrades(String username) {

        Student student = findStudentByUsername(username);

        if (student == null)
            return new ArrayList<>();

        return repo.getStudentGrades(student.getStudentCode());

    }

    public boolean registerStudent(Student student) {

        if (student == null)
            return false;

        String hashedPass = HashPassword.encodePasskey(student.getPassword());

        return repo.insertStudent(student, hashedPass) != -1;
    }

    public boolean registerTeacher(Teacher teacher) {

        if (teacher == null)
            return false;

        String hashedPass = HashPassword.encodePasskey(teacher.getPassword());

        return repo.insertTeacher(teacher, hashedPass) != -1;
    }

    public boolean registerAdministrator(Administrator admin) {

        if (admin == null)
            return false;

        String hashedPass = HashPassword.encodePasskey(admin.getPassword());

        return repo.insertAdministrator(admin, hashedPass) != -1;

    }

    public boolean createSubject(Subject subject) {

        if (subject == null)
            return false;

        return repo.insertSubject(subject);

    }

    public boolean createAcademicGroup(Group group) {

        if (group == null)
            return false;

        return repo.insertAcademicGroup(group);
    }

    public boolean createClassSessions(ArrayList<ClassSession> sessions) {

        if (sessions == null || sessions.isEmpty())
            return false;

        return repo.insertClassSessions(sessions);
    }

    public boolean enrollStudent(Student student, Group group) {

        if (student == null || group == null)
            return false;

        return repo.insertEnrollment(student.getStudentCode(), group.getGroupCode());

    }

    public boolean validPasswordLength(String password) {

        if (password == null || password.isEmpty())
            return false;

        return HashPassword.isValidLength(password);
    }

    public boolean updatePassword(String username, String newPassword) {

        if (username == null || username.isEmpty())
            return false;

        if (!validPasswordLength(newPassword))
            return false;

        String passwordHash = HashPassword.encodePasskey(newPassword);

        return repo.updatePassword(username, passwordHash);

    }

    public boolean updateFirstName(String username, String newFirstName) {

        if (username == null || username.isEmpty())
            return false;

        if (newFirstName == null || newFirstName.isEmpty())
            return false;

        return repo.updateFirstName(username, newFirstName);

    }

    public boolean updateLastName(String username, String newLastName) {

        if (username == null || username.isEmpty())
            return false;

        if (newLastName == null || newLastName.isEmpty())
            return false;

        return repo.updateLastName(username, newLastName);

    }

    public boolean updateStudentDegree(int studentCode, String degree) {

        if (degree == null || degree.isEmpty())
            return false;

        return repo.updateStudentDegree(studentCode, degree);
    }

    public boolean updateTeacherDepartment(int employeeNumber, String department) {

        if (department == null || department.isEmpty())
            return false;

        return repo.updateTeacherDepartment(employeeNumber, department);

    }

    public boolean updateAdministratorPosition(int employeeNumber, String position) {

        if (position == null || position.isEmpty())
            return false;

        return repo.updateAdministratorPosition(employeeNumber, position);

    }

    public boolean deleteUser(User user) {

        if (user == null)
            return false;

        if (user instanceof Student) {
            Student student = (Student) user;
            return repo.deleteStudent(student.getStudentCode());
        }

        if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            return repo.deleteTeacher(teacher.getEmployeeNumber());
        }

        if (user instanceof Administrator) {
            Administrator administrator = (Administrator) user;
            return repo.deleteAdministrator(administrator.getEmployeeNumber());
        }

        return false;
    }

}

class RepositorySQL {

    private Connection connection;

    public RepositorySQL() {

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:snowflake.db");

            try (Statement statement = connection.createStatement()) {
                statement.execute("PRAGMA foreign_keys = ON");
            }

        } catch (SQLException mistake) {
            connection = null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public boolean executeToCreate(String parameter, Statement statement) {

        try {

            statement.execute(parameter);
            return true;

        } catch (SQLException mistake) {
            return false;
        }
    }

    public boolean createTableUsers(Statement statement) {

        String users = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    role TEXT NOT NULL
                );
                """;

        return executeToCreate(users, statement);
    }

    public boolean createTableStudents(Statement statement) {

        String students = """
                CREATE TABLE IF NOT EXISTS students (
                    user_id INTEGER PRIMARY KEY,
                    student_code INTEGER NOT NULL UNIQUE,
                    degree TEXT NOT NULL,
                    FOREIGN KEY (user_id)
                        REFERENCES users(id)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(students, statement);
    }

    public boolean createTableEmployees(Statement statement) {

        String employees = """
                CREATE TABLE IF NOT EXISTS employees (
                    user_id INTEGER PRIMARY KEY,
                    employee_number INTEGER NOT NULL UNIQUE,
                    FOREIGN KEY (user_id)
                        REFERENCES users(id)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(employees, statement);
    }

    public boolean createTableTeachers(Statement statement) {

        String teachers = """
                CREATE TABLE IF NOT EXISTS teachers (
                    user_id INTEGER PRIMARY KEY,
                    department TEXT NOT NULL,
                    FOREIGN KEY (user_id)
                        REFERENCES employees(user_id)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(teachers, statement);
    }

    public boolean createTableAdministrators(Statement statement) {

        String admins = """
                CREATE TABLE IF NOT EXISTS administrators (
                    user_id INTEGER PRIMARY KEY,
                    position TEXT NOT NULL,
                    FOREIGN KEY (user_id)
                        REFERENCES employees(user_id)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(admins, statement);
    }

    public boolean createTableSubjects(Statement statement) {

        String subjects = """
                CREATE TABLE IF NOT EXISTS subjects (
                    code TEXT PRIMARY KEY,
                    name TEXT NOT NULL
                );
                """;

        return executeToCreate(subjects, statement);
    }

    public boolean createTableAcademicGroups(Statement statement) {

        String academicGroups = """
                CREATE TABLE IF NOT EXISTS academic_groups (
                    group_code TEXT PRIMARY KEY,
                    subject_code TEXT NOT NULL,
                    teacher_id INTEGER,
                    FOREIGN KEY (subject_code)
                        REFERENCES subjects(code)
                        ON DELETE CASCADE,
                    FOREIGN KEY (teacher_id)
                        REFERENCES teachers(user_id)
                        ON DELETE SET NULL
                );
                """;

        return executeToCreate(academicGroups, statement);
    }

    public boolean createTableEnrollments(Statement statement) {

        String enrollments = """
                CREATE TABLE IF NOT EXISTS enrollments (
                    student_code INTEGER NOT NULL,
                    group_code TEXT NOT NULL,
                    PRIMARY KEY (student_code, group_code),
                    FOREIGN KEY (student_code)
                        REFERENCES students(student_code)
                        ON DELETE CASCADE,
                    FOREIGN KEY (group_code)
                        REFERENCES academic_groups(group_code)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(enrollments, statement);
    }

    public boolean createTableClassSessions(Statement statement) {

        String classSessions = """
                CREATE TABLE IF NOT EXISTS class_sessions (
                    class_code TEXT NOT NULL,
                    group_code TEXT NOT NULL,
                    time TEXT NOT NULL,
                    day TEXT NOT NULL,
                    PRIMARY KEY (class_code, day),
                    FOREIGN KEY (group_code)
                        REFERENCES academic_groups(group_code)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(classSessions, statement);
    }

    public boolean createTableGrades(Statement statement) {

        String grades = """
                CREATE TABLE IF NOT EXISTS grades (
                    student_code INTEGER NOT NULL,
                    group_code TEXT NOT NULL,
                    first_partial REAL,
                    second_partial REAL,
                    third_partial REAL,
                    final_grade REAL,
                    PRIMARY KEY (student_code, group_code),
                    FOREIGN KEY (student_code, group_code)
                        REFERENCES enrollments(student_code, group_code)
                        ON DELETE CASCADE
                );
                """;

        return executeToCreate(grades, statement);
    }

    public boolean createTables() {

        if (!isConnected())
            return false;

        try (Statement statement = connection.createStatement()) {

            if (!createTableUsers(statement)
                    || !createTableStudents(statement)
                    || !createTableEmployees(statement)) {

                return false;
            }

            if (!createTableTeachers(statement)
                    || !createTableAdministrators(statement)
                    || !createTableSubjects(statement)) {

                return false;
            }

            if (!createTableAcademicGroups(statement)
                    || !createTableEnrollments(statement)) {

                return false;
            }

            if (!createTableClassSessions(statement)
                    || !createTableGrades(statement)) {

                return false;
            }

            return true;

        } catch (SQLException mistake) {
            return false;
        }
    }

    private int insertUser(User user, String role, String hashedPassword) throws SQLException {

        if (!isConnected())
            throw new SQLException();

        String sql = """
                INSERT INTO users (username, password, first_name, last_name, role) VALUES (?, ?, ?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, role);

            statement.executeUpdate();

            try (ResultSet newKeys = statement.getGeneratedKeys()) {

                if (newKeys.next()) {
                    return newKeys.getInt(1);
                }
            }
        }

        throw new SQLException();
    }

    private void insertEmployeeData(Employee employee, int userID) throws SQLException {

        String sql = """
                INSERT INTO employees (user_id, employee_number) VALUES (?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userID);
            statement.setInt(2, employee.getEmployeeNumber());

            statement.executeUpdate();
        }
    }

    private int insertEmployee(Employee employee, String role, String hashedPassword) throws SQLException {

        int userID = insertUser(employee, role, hashedPassword);

        insertEmployeeData(employee, userID);

        return userID;
    }

    public int insertStudent(Student student, String hashedPassword) {

        if (!isConnected())
            return -1;

        String sql = """
                INSERT INTO students (user_id, student_code, degree) VALUES (?, ?, ?);
                """;

        try {

            connection.setAutoCommit(false);

            int userID = insertUser(student, "STUDENT", hashedPassword);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, userID);
                statement.setInt(2, student.getStudentCode());
                statement.setString(3, student.getDegree());
                statement.executeUpdate();
            }

            connection.commit();
            return userID;

        } catch (SQLException mistake) {

            try {
                connection.rollback();

            } catch (SQLException rollBackError) {
            }

            return -1;

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitError) {
            }

        }

    }

    public int insertTeacher(Teacher teacher, String hashedPassword) {

        if (!isConnected())
            return -1;

        String sql = """
                INSERT INTO teachers (user_id, department) VALUES (?, ?);
                """;

        try {

            connection.setAutoCommit(false);

            int userID = insertEmployee(teacher, "TEACHER", hashedPassword);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, userID);
                statement.setString(2, teacher.getDepartment());
                statement.executeUpdate();
            }

            connection.commit();
            return userID;

        } catch (SQLException mistake) {

            try {
                connection.rollback();

            } catch (SQLException rollBackError) {
            }

            return -1;

        } finally {

            try {
                connection.setAutoCommit(true);

            } catch (SQLException autoCommitError) {
            }
        }

    }

    public int insertAdministrator(Administrator administrator, String hashedPassword) {

        if (!isConnected())
            return -1;

        String sql = """
                INSERT INTO administrators (user_id, position) VALUES (?, ?);
                """;

        try {

            connection.setAutoCommit(false);

            int userID = insertEmployee(administrator, "ADMINISTRATOR", hashedPassword);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, userID);
                statement.setString(2, administrator.getPosition());
                statement.executeUpdate();
            }

            connection.commit();
            return userID;

        } catch (SQLException mistake) {

            try {
                connection.rollback();

            } catch (SQLException rollBackError) {
            }

            return -1;

        } finally {

            try {
                connection.setAutoCommit(true);

            } catch (SQLException autoCommitError) {
            }

        }

    }

    public boolean insertSubject(Subject subject) {

        if (!isConnected())
            return false;

        String sql = """
                INSERT INTO subjects (code, name) VALUES (?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subject.getCode());
            statement.setString(2, subject.getName());
            statement.executeUpdate();

            return true;

        } catch (SQLException mistake) {
            return false;
        }

    }

    public boolean insertAcademicGroup(Group group) {

        if (!isConnected())
            return false;

        String sql = """
                INSERT INTO academic_groups (group_code, subject_code, teacher_id) VALUES (?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, group.getGroupCode());
            statement.setString(2, group.getSubject().getCode());

            if (group.getTeacher() == null) {

                statement.setNull(3, java.sql.Types.INTEGER);

            } else {

                statement.setInt(3, group.getTeacher().getID());
            }

            statement.executeUpdate();
            return true;

        } catch (SQLException mistake) {
            return false;
        }

    }

    public boolean insertEnrollment(int studentCode, String groupCode) {

        if (!isConnected())
            return false;

        String enrollmentSQL = """
                INSERT INTO enrollments (student_code, group_code) VALUES (?, ?);
                """;

        String gradeSQL = """
                INSERT INTO grades (student_code, group_code) VALUES (?, ?);
                """;

        try {

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(enrollmentSQL)) {

                statement.setInt(1, studentCode);
                statement.setString(2, groupCode);
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement(gradeSQL)) {

                statement.setInt(1, studentCode);
                statement.setString(2, groupCode);
                statement.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (SQLException mistake) {

            try {
                connection.rollback();

            } catch (SQLException rollbackError) {
            }

            return false;

        } finally {

            try {
                connection.setAutoCommit(true);

            } catch (SQLException autoCommitError) {
            }

        }

    }

    public boolean insertClassSessions(ArrayList<ClassSession> sessions) {

        try {
            connection.setAutoCommit(false);

            for (ClassSession session : sessions) {
                insertClassSessionData(session);
            }

            connection.commit();
            return true;

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException ex) {
            }

            return false;

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
            }
        }
    }

    private void insertClassSessionData(ClassSession session)
            throws SQLException {

        String sql = """
                INSERT INTO class_sessions
                (class_code, group_code, time, day)
                VALUES (?, ?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, session.getClassCode());
            statement.setString(2, session.getGroupCode());
            statement.setString(3, session.getTime());
            statement.setString(4, session.getDay());

            statement.executeUpdate();
        }
    }

    public boolean insertGrade(Student student, GradeRecord grade) {

        if (!isConnected())
            return false;

        String sql = """
                INSERT INTO grades (student_code, group_code, first_partial, second_partial,
                    third_partial, final_grade) VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, student.getStudentCode());
            statement.setString(2, grade.getGroupCode());
            statement.setDouble(3, grade.getFirstPartial());
            statement.setDouble(4, grade.getSecondPartial());
            statement.setDouble(5, grade.getThirdPartial());
            statement.setDouble(6, grade.getFinalGrade());
            statement.executeUpdate();

            return true;

        } catch (SQLException mistake) {

            return false;

        }

    }

    public Student findStudentByUsername(String username) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT users.id, users.username, users.password, users.first_name,
                    users.last_name, students.student_code, students.degree FROM users JOIN students
                    ON users.id = students.user_id WHERE users.username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String storedUsername = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int studentCode = result.getInt("student_code");
                    String degree = result.getString("degree");

                    return new Student(storedUsername, password, firstName,
                            lastName, iD, new Schedule(), studentCode, degree);

                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;

        }

    }

    public Teacher findTeacherByUsername(String username) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT users.id, users.username, users.password, users.first_name, users.last_name,
                    employees.employee_number, teachers.department FROM users JOIN employees ON users.id = employees.user_id
                    JOIN teachers ON employees.user_id = teachers.user_id WHERE users.username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String storedUsername = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int employeeNumber = result.getInt("employee_number");
                    String department = result.getString("department");

                    return new Teacher(storedUsername, password, firstName, lastName, iD, new Schedule(),
                            employeeNumber, department);

                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public Administrator findAdministratorByUsername(String username) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT users.id, users.username, users.password,
                    users.first_name, users.last_name, employees.employee_number,
                    administrators.position FROM users JOIN employees
                    ON users.id = employees.user_id JOIN administrators
                    ON employees.user_id = administrators.user_id
                    WHERE users.username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String storedUsername = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int employeeNumber = result.getInt("employee_number");
                    String position = result.getString("position");

                    return new Administrator(employeeNumber, storedUsername, password, firstName,
                            lastName, iD, new Schedule(), position);
                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;

        }

    }

    public Student findStudentByStudentCode(int studentCode) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT users.id, users.username,
                       users.password, users.first_name,
                       users.last_name, students.student_code,
                       students.degree FROM users
                       JOIN students
                       ON users.id = students.user_id
                       WHERE students.student_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int studentCodeResult = result.getInt("student_code");
                    String degree = result.getString("degree");

                    return new Student(username, password, firstName,
                            lastName, iD, new Schedule(), studentCodeResult, degree);
                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;

        }

    }

    public Teacher findTeacherByEmployeeNumber(int employeeNumber) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT users.id, users.username, users.password, users.first_name, users.last_name,
                    employees.employee_number, teachers.department FROM users JOIN employees
                    ON users.id = employees.user_id JOIN teachers ON employees.user_id = teachers.user_id
                    WHERE employees.employee_number = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int employeeNumberResult = result.getInt("employee_number");
                    String department = result.getString("department");

                    return new Teacher(username, password, firstName, lastName,
                            iD, new Schedule(), employeeNumberResult, department);
                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public Administrator findAdministratorByEmployeeNumber(int employeeNumber) {

        if (!isConnected()) {
            return null;
        }

        String sql = """
                SELECT users.id, users.username,
                    users.password, users.first_name,
                    users.last_name, employees.employee_number,
                    administrators.position FROM users
                    JOIN employees ON users.id = employees.user_id
                    JOIN administrators ON employees.user_id = administrators.user_id
                    WHERE employees.employee_number = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int employeeNumberResult = result.getInt("employee_number");
                    String position = result.getString("position");

                    return new Administrator(employeeNumberResult, username, password, firstName,
                            lastName, iD, new Schedule(), position);

                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public Subject findSubjectByCode(String subjectCode) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT code, name FROM subjects WHERE code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subjectCode);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String code = result.getString("code");
                    String name = result.getString("name");

                    return new Subject(code, name);
                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;
        }
    }

    public Group findGroupByCode(String groupCode) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT academic_groups.group_code,
                    academic_groups.subject_code, subjects.name,
                    users.id, users.username,
                    users.password, users.first_name,
                    users.last_name, employees.employee_number,
                    teachers.department FROM academic_groups
                    JOIN subjects ON academic_groups.subject_code = subjects.code
                    LEFT JOIN teachers ON academic_groups.teacher_id = teachers.user_id
                    LEFT JOIN employees ON teachers.user_id = employees.user_id
                    LEFT JOIN users ON teachers.user_id = users.id
                    WHERE academic_groups.group_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, groupCode);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String code = result.getString("group_code");
                    String subjectCode = result.getString("subject_code");
                    String subjectName = result.getString("name");

                    Subject subject = new Subject(subjectCode, subjectName);
                    Teacher teacher = null;
                    String username = result.getString("username");

                    if (username != null) {

                        int iD = result.getInt("id");
                        String password = result.getString("password");
                        String firstName = result.getString("first_name");
                        String lastName = result.getString("last_name");
                        int employeeNumber = result.getInt("employee_number");
                        String department = result.getString("department");

                        teacher = new Teacher(username, password, firstName, lastName, iD,
                                new Schedule(), employeeNumber, department);
                    }

                    return new Group(subject, teacher, code);

                }

            }

            return null;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public Student findStudentInGroup(String groupCode, String username) {

        if (!isConnected()) {
            return null;
        }

        String sql = """
                SELECT users.id, users.username, users.password,
                    users.first_name, users.last_name, students.student_code,
                    students.degree FROM users JOIN students
                    ON users.id = students.user_id
                    JOIN enrollments
                    ON students.student_code = enrollments.student_code
                    WHERE enrollments.group_code = ?
                    AND users.username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, groupCode);
            statement.setString(2, username);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    int iD = result.getInt("id");
                    String storedUsername = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int studentCode = result.getInt("student_code");
                    String degree = result.getString("degree");

                    return new Student(storedUsername, password, firstName, lastName,
                            iD, new Schedule(), studentCode, degree);
                }

            }

            return null;

        } catch (SQLException mistake) {
            return null;
        }
    }

    public ClassSession findClassSessionByCode(String classCode) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT class_sessions.class_code, class_sessions.group_code,
                       class_sessions.time, class_sessions.day,
                       subjects.code, subjects.name
                       FROM class_sessions JOIN academic_groups
                       ON class_sessions.group_code = academic_groups.group_code
                       JOIN subjects ON academic_groups.subject_code =
                       subjects.code WHERE class_sessions.class_code = ? LIMIT 1;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, classCode);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String storedClassCode = result.getString("class_code");
                    String groupCode = result.getString("group_code");
                    String time = result.getString("time");
                    String day = result.getString("day");

                    Subject subject = new Subject(result.getString("code"), result.getString("name"));

                    return new ClassSession(storedClassCode, groupCode, subject, time, day);
                }
            }

            return null;

        } catch (SQLException mistake) {
            return null;
        }

    }

    public Schedule getStudentSchedule(int studentCode) {

        if (!isConnected()) {
            return null;
        }

        Schedule schedule = new Schedule();

        String sql = """
                SELECT academic_groups.group_code, academic_groups.subject_code, class_sessions.class_code,
                    subjects.name, class_sessions.day, class_sessions.time FROM enrollments
                    JOIN academic_groups ON enrollments.group_code = academic_groups.group_code
                    JOIN subjects ON academic_groups.subject_code = subjects.code
                    JOIN class_sessions ON academic_groups.group_code = class_sessions.group_code
                    WHERE enrollments.student_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String subjectCode = result.getString("subject_code");
                    String subjectName = result.getString("name");
                    String classCode = result.getString("class_code");
                    String groupCode = result.getString("group_code");
                    String day = result.getString("day");
                    String time = result.getString("time");

                    Subject subject = new Subject(subjectCode, subjectName);

                    ClassSession session = new ClassSession(classCode, groupCode, subject, time, day);

                    schedule.getSessions().add(session);
                }
            }

            return schedule;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public Schedule getTeacherSchedule(int employeeNumber) {

        if (!isConnected())
            return null;

        Schedule schedule = new Schedule();

        String sql = """
                SELECT academic_groups.group_code,subjects.code,
                    subjects.name, class_sessions.class_code,
                    class_sessions.day, class_sessions.time
                    FROM employees JOIN teachers
                    ON employees.user_id = teachers.user_id
                    JOIN academic_groups ON teachers.user_id = academic_groups.teacher_id
                    JOIN subjects ON academic_groups.subject_code = subjects.code
                    JOIN class_sessions ON academic_groups.group_code = class_sessions.group_code
                    WHERE employees.employee_number = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String subjectCode = result.getString("code");
                    String subjectName = result.getString("name");
                    String classCode = result.getString("class_code");
                    String groupCode = result.getString("group_code");
                    String day = result.getString("day");
                    String time = result.getString("time");

                    Subject subject = new Subject(subjectCode, subjectName);

                    ClassSession session = new ClassSession(classCode, groupCode, subject, time, day);

                    schedule.getSessions().add(session);

                }

            }

            return schedule;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public ArrayList<GradeRecord> getStudentGrades(int studentCode) {

        if (!isConnected())
            return null;

        ArrayList<GradeRecord> grades = new ArrayList<>();

        String sql = """
                SELECT subjects.code, subjects.name,
                    enrollments.group_code, grades.first_partial,
                    grades.second_partial, grades.third_partial,
                    grades.final_grade FROM subjects
                    JOIN academic_groups ON subjects.code = academic_groups.subject_code
                    JOIN enrollments ON academic_groups.group_code = enrollments.group_code
                    JOIN grades ON enrollments.student_code = grades.student_code
                    AND enrollments.group_code = grades.group_code WHERE enrollments.student_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String subjectCode = result.getString("code");
                    String subjectName = result.getString("name");
                    String groupCode = result.getString("group_code");
                    double firstPartial = result.getDouble("first_partial");
                    double secondPartial = result.getDouble("second_partial");
                    double thirdPartial = result.getDouble("third_partial");
                    double finalGrade = result.getDouble("final_grade");
                    Subject subject = new Subject(subjectCode, subjectName);

                    GradeRecord newGrade = new GradeRecord(subject, groupCode, firstPartial, secondPartial,
                            thirdPartial, finalGrade);

                    grades.add(newGrade);

                }
            }

            return grades;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public GradeRecord getGradeFromStudent(int studentCode, String groupCode) {

        if (!isConnected())
            return null;

        String sql = """
                SELECT subjects.code, subjects.name, grades.first_partial,
                    grades.second_partial, grades.third_partial,
                    grades.final_grade FROM subjects JOIN academic_groups ON subjects.code = academic_groups.subject_code
                    JOIN enrollments ON academic_groups.group_code = enrollments.group_code
                    JOIN grades ON enrollments.student_code = grades.student_code
                    AND enrollments.group_code = grades.group_code WHERE enrollments.student_code = ?
                    AND enrollments.group_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);
            statement.setString(2, groupCode);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    String subjectCode = result.getString("code");
                    String subjectName = result.getString("name");
                    double firstPartial = result.getDouble("first_partial");
                    double secondPartial = result.getDouble("second_partial");
                    double thirdPartial = result.getDouble("third_partial");
                    double finalGrade = result.getDouble("final_grade");

                    Subject subject = new Subject(subjectCode, subjectName);

                    return new GradeRecord(subject, groupCode, firstPartial,
                            secondPartial, thirdPartial, finalGrade);
                }

            }

            return null;

        } catch (SQLException mistake) {
            return null;
        }

    }

    public ArrayList<Group> getGroupsOfTeacher(int employeeNumber) {

        if (!isConnected())
            return null;

        ArrayList<Group> groups = new ArrayList<>();

        String sql = """
                SELECT subjects.code, subjects.name, teachers.department, academic_groups.group_code,
                    users.username, users.password, users.first_name,users.last_name, users.id
                    FROM subjects JOIN academic_groups ON subjects.code = academic_groups.subject_code
                    JOIN teachers ON academic_groups.teacher_id = teachers.user_id JOIN employees
                    ON teachers.user_id = employees.user_id JOIN users ON employees.user_id = users.id
                    WHERE employees.employee_number = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);

            try (ResultSet result = statement.executeQuery()) {

                Teacher teacher = null;

                while (result.next()) {

                    String subjectCode = result.getString("code");
                    String subjectName = result.getString("name");
                    String groupCode = result.getString("group_code");

                    Subject subject = new Subject(subjectCode, subjectName);

                    if (teacher == null) {

                        String username = result.getString("username");
                        String password = result.getString("password");
                        String firstName = result.getString("first_name");
                        String lastName = result.getString("last_name");
                        int iD = result.getInt("id");
                        String department = result.getString("department");

                        teacher = new Teacher(username, password, firstName, lastName,
                                iD, new Schedule(), employeeNumber, department);

                    }

                    Group group = new Group(subject, teacher, groupCode);

                    groups.add(group);

                }
            }

            return groups;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public ArrayList<Student> getStudentsFromGroup(String groupCode) {

        if (!isConnected())
            return null;

        ArrayList<Student> students = new ArrayList<>();

        String sql = """
                SELECT users.id, users.username, users.password, users.first_name, users.last_name,
                    students.student_code, students.degree FROM users JOIN students ON users.id = students.user_id
                    JOIN enrollments ON students.student_code = enrollments.student_code WHERE enrollments.group_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, groupCode);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    String username = result.getString("username");
                    String password = result.getString("password");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int iD = result.getInt("id");
                    int studentCode = result.getInt("student_code");
                    String degree = result.getString("degree");

                    Student student = new Student(username, password, firstName, lastName, iD,
                            new Schedule(), studentCode, degree);

                    students.add(student);

                }

            }

            return students;

        } catch (SQLException mistake) {

            return null;
        }

    }

    public boolean updateGrade(int studentCode, String groupCode, int partialOption, double newGrade) {

        if (!isConnected())
            return false;

        String partial;

        switch (partialOption) {

            case 1:
                partial = "first_partial";
                break;

            case 2:
                partial = "second_partial";
                break;

            case 3:
                partial = "third_partial";
                break;

            case 4:
                partial = "final_grade";
                break;

            default:
                return false;
        }

        String sql = "UPDATE grades SET " + partial + " = ? WHERE student_code = ? AND group_code = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, newGrade);
            statement.setInt(2, studentCode);
            statement.setString(3, groupCode);

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword) {

        if (!isConnected())
            return false;

        String sql = """
                UPDATE users SET password = ? WHERE username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newPassword);
            statement.setString(2, username);

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;

        }

    }

    public boolean updateFirstName(String username, String newFirstName) {

        if (!isConnected())
            return false;

        String sql = """
                UPDATE users SET first_name = ? WHERE username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newFirstName);
            statement.setString(2, username);

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            return false;
        }

    }

    public boolean updateLastName(String username, String newLastName) {

        if (!isConnected())
            return false;

        String sql = """
                UPDATE users SET last_name = ? WHERE username = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newLastName);
            statement.setString(2, username);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {

            return false;

        }

    }

    public boolean updateStudentDegree(int studentCode, String newDegree) {

        if (!isConnected())
            return false;

        String sql = """
                UPDATE students SET degree = ? WHERE student_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newDegree);
            statement.setInt(2, studentCode);

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean updateTeacherDepartment(int employeeNumber, String newDepartment) {

        if (!isConnected())
            return false;

        String sql = """
                UPDATE teachers SET department = ? WHERE user_id = (SELECT user_id FROM employees WHERE employee_number = ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newDepartment);
            statement.setInt(2, employeeNumber);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean updateAdministratorPosition(int employeeNumber, String newPosition) {

        if (!isConnected())
            return false;

        String sql = """
                UPDATE administrators SET position = ? WHERE user_id = (SELECT user_id FROM employees WHERE employee_number = ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newPosition);
            statement.setInt(2, employeeNumber);

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean deleteEnrollment(int studentCode, String groupCode) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM enrollments WHERE student_code = ? AND group_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);
            statement.setString(2, groupCode);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;

        }

    }

    public boolean deleteClassSession(String classCode) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM class_sessions WHERE class_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, classCode);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean deleteGrade(int studentCode, String groupCode) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM grades WHERE student_code = ? AND group_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);
            statement.setString(2, groupCode);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean deleteAcademicGroup(String groupCode) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM academic_groups WHERE group_code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, groupCode);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean deleteSubject(String subjectCode) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM subjects
                WHERE code = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subjectCode);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException mistake) {

            return false;
        }

    }

    public boolean deleteStudent(int studentCode) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM users WHERE id = (SELECT user_id FROM students WHERE student_code = ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentCode);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            return false;

        }

    }

    public boolean deleteTeacher(int employeeNumber) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM users WHERE id = (SELECT e.user_id FROM employees e
                    JOIN teachers t ON t.user_id = e.user_id WHERE e.employee_number = ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);
            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            return false;

        }

    }

    public boolean deleteAdministrator(int employeeNumber) {

        if (!isConnected())
            return false;

        String sql = """
                DELETE FROM users WHERE id = (SELECT e.user_id FROM employees e JOIN administrators a
                        ON a.user_id = e.user_id WHERE e.employee_number = ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeNumber);
            int result = statement.executeUpdate();
            return result > 0;

        } catch (SQLException e) {

            return false;
        }

    }

}

// ******************************************************************************
class ConsoleUI {

    Scanner scanner = new Scanner(System.in);
    private AcademicManagement x;

    public ConsoleUI(AcademicManagement x) {
        this.x = x;
    }

    public int askIntegral() {

        System.out.println("\nENTER THE OPTION: \n");

        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            System.out.println("\nTHIS INPUT CANNOT BE EMPTY. TRY AGAIN: \n");
            return -1;
        }

        try {

            int value2 = Integer.parseInt(value);
            return value2;

        } catch (NumberFormatException mistake) {
            System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
            return -1;
        }
    }

    public double askDouble() {

        String value;
        while (true) {
            value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println("\nTHIS PARAMETER CANNOT BE EMPTY. TRY AGAIN: \n");
                continue;
            }

            try {

                double variable = Double.parseDouble(value);
                return variable;

            } catch (NumberFormatException x) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN: \n");
                continue;
            }
        }

    }

    public String readBasicString() {

        String text = scanner.nextLine().trim();

        if (text.isEmpty()) {
            System.out.println("\nTHIS INPUT CANNOT BE EMPTY. TRY AGAIN: \n");
            return null;
        }

        return text;
    }

    public String readLongString() {

        String text = scanner.nextLine().trim();

        if (text.isEmpty()) {
            System.out.println("\nTHIS INPUT CANNOT BE EMPTY. TRY AGAIN: \n");
            return null;
        }

        if (text.length() < 6) {
            System.out.println("\nMUST HAVE AT LEAST 6 CHARACTERS. TRY AGAIN: \n");
            return null;
        }

        return text;
    }

    public boolean confirmLogOut() {

        while (true) {

            System.out.println("ARE YOU SURE TO LOG OUT?");
            System.out.println("----------------------------------");
            System.out.println("LOG OUT -> 1 ");
            System.out.println("STAY -> 2 ");
            System.out.println("----------------------------------");

            int opt = askIntegral();
            if (opt == -1) {
                continue;
            }

            if (opt != 1 && opt != 2) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            if (opt == 1) {
                System.out.println("\nLEAVING... \n");
                return true;
            }

            System.out.println("\n");
            return false;
        }

    }

    public boolean confirmAction() {

        while (true) {
            System.out.println("\nARE YOU SURE TO CARRY OUT THIS ACTION?");
            System.out.println("----------------------------------------");
            System.out.println("CONFIRM -> 1");
            System.out.println("CANCEL -> 2");
            System.out.println("----------------------------------------");
            int opt = askIntegral();
            if (opt == -1)
                continue;

            if (opt != 1 && opt != 2) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            if (opt == 1)
                return true;

            else if (opt == 2) {
                return false;
            }
        }

    }

    public boolean afterFunction() {

        while (true) {
            System.out.println("\nNOW WHAT?");
            System.out.println("-----------------------");
            System.out.println("LOG OUT -> 1");
            System.out.println("RETURN MAIN MENU -> 2");
            System.out.println("-----------------------");
            int opt = askIntegral();
            if (opt == -1)
                continue;

            if (opt != 1 && opt != 2) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            System.out.println("\n");

            if (opt == 1) {
                if (confirmLogOut())
                    return true;
            }

            return false;

        }

    }

    public String getSubjectFromSlot(ArrayList<ClassSession> sessions, String day, String time) {

        for (ClassSession session : sessions) {
            if (session.getDay().equalsIgnoreCase(day) && session.getTime().equalsIgnoreCase(time)) {

                return session.getSubject().getCode();
            }
        }

        return "---";
    }

    public void showSchedule(ArrayList<ClassSession> sessions, ArrayList<String> times) {

        System.out.println("\n----------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-6s | %-6s | %-6s | %-6s | %-6s | %-6s |\n",
                "TIME", "MDY", "TDY", "WDY", "THDY", "FDY", "SDY");
        System.out.println("----------------------------------------------------------------------------");

        for (String time : times) {

            System.out.printf("| %-8s | %-6s | %-6s | %-6s | %-6s | %-6s | %-6s |\n",
                    time, getSubjectFromSlot(sessions, "MDY", time), getSubjectFromSlot(sessions, "TDY", time),
                    getSubjectFromSlot(sessions, "WDY", time), getSubjectFromSlot(sessions, "THDY", time),
                    getSubjectFromSlot(sessions, "FDY", time), getSubjectFromSlot(sessions, "SDY", time));

        }

        System.out.println("----------------------------------------------------------------------------\n");

    }

    public void studentSchedule(String username) {

        Schedule schedule = x.getStudentSchedule(username);
        if (schedule == null || schedule.getSessions().isEmpty()) {
            System.out.println("\nTHE SCHEDULE COULD NOT BE FOUND OR IT'S CURRENTLY EMPTY\n");
            return;
        }

        ArrayList<ClassSession> sessions = schedule.getSessions();
        ArrayList<String> times = new ArrayList<>();

        for (ClassSession session : sessions) {
            if (!times.contains(session.getTime())) {
                times.add(session.getTime());
            }
        }

        showSchedule(sessions, times);
    }

    public void showGrades(ArrayList<GradeRecord> grades) {

        if (grades == null || grades.isEmpty()) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER\n");
            return;
        }

        System.out.println("\n-------------------------------------------------------------------------------");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                "SUBJECT", "FIRST PARTIAL", "SECOND PARTIAL", "THIRD PARTIAL", "FINAL");
        System.out.println("-------------------------------------------------------------------------------");

        for (GradeRecord grade : grades) {
            System.out.printf("| %-15s | %-15.2f | %-15.2f | %-15.2f | %-15.2f |\n", grade.getSubject().getName(),
                    grade.getFirstPartial(), grade.getSecondPartial(), grade.getThirdPartial(), grade.getFinalGrade());

        }

        System.out.println("-------------------------------------------------------------------------------\n");

    }

    public void studentGrades(String username) {

        ArrayList<GradeRecord> grades = x.getStudentGrades(username);
        if (grades == null || grades.isEmpty()) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER\n");
            return;
        }

        showGrades(grades);

    }

    public void showCredential(Credential z) {

        System.out.println("\n");
        System.out.println("--------------------------------------------------");
        System.out.printf("| %-46s |\n", "CREDENTIAL DIGITAL");
        System.out.println("--------------------------------------------------");
        System.out.printf("| %-30s %-14s |\n", "", ".----------.");
        System.out.printf("| %-30s %-14s |\n", "", "|  PHOTO   |");
        System.out.printf("| %-30s %-14s |\n", "", "| NO IMAGE |");
        System.out.printf("| %-30s %-14s |\n", "", "'----------'");
        System.out.printf("| %-46s |\n", "");
        System.out.printf("| %-46s |\n", z.getFirstName() + " " + z.getLastName());
        System.out.printf("| %-46s |\n", "");
        System.out.printf("| %-46s |\n", z.getRole());
        System.out.printf("| %-46s |\n", "");
        System.out.printf("| %-46s |\n", "ID: " + z.getID());
        System.out.printf("| %-46s |\n", "");
        System.out.printf("| %-46s |\n", "USERNAME: " + z.getUsername());
        System.out.printf("| %-46s |\n", "");
        System.out.printf("| %-46s |\n", "");
        System.out.println("--------------------------------------------------\n");

    }

    public void studentCredential(String username) {

        Credential credential = x.getStudentCredential(username);
        if (credential == null) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER\n");
            return;
        }

        showCredential(credential);

    }

    public void studentMainMenu(String username) { // requires username and password

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.println("SESSION STARTED ON " + Utils.currentDate() + " AT " + Utils.currentTime());
        System.out.println("-----------------------------------------------------------------------\n");

        Student student = x.findStudentByUsername(username);
        if (student == null) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        System.out.println("\nHELLO, " + student.getFirstName() + " " + student.getLastName() + "\n");

        while (true) {
            System.out.println("\n       MAIN MENU      ");
            System.out.println("----------------------");
            System.out.println("SCHEDULE -> 1");
            System.out.println("GRADES -> 2");
            System.out.println("CREDENTIAL -> 3");
            System.out.println("EXIT -> 0");
            System.out.println("----------------------");

            int opt = askIntegral();
            if (opt == -1)
                continue;

            if (opt < 0 || opt > 3) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            switch (opt) {

                case 1:
                    studentSchedule(username);

                    break;

                case 2:
                    studentGrades(username);

                    break;

                case 3:
                    studentCredential(username);

                    break;

                case 0:
                    System.out.println("\n");
                    if (confirmLogOut())
                        return;

                    System.out.println("\n");
                    break;

                default:
                    System.out.println("\nINVALID OPTION. TRY AGAIN LATER\n");
                    return;

            }


            if (afterFunction()) {
                System.out.println("\n");
                return;
            }

        }

    }

    public void teacherSchedule(String username) { // requires username and password.

        Schedule s = x.getTeacherSchedule(username);
        if (s == null || s.getSessions().isEmpty()) {
            System.out.println("\nSCHEDULE COULD NOT BE FOUND OR IT'S CURRENTLY EMPTY\n");
            return;
        }

        ArrayList<ClassSession> sessions = s.getSessions();
        ArrayList<String> times = new ArrayList<>();

        for (ClassSession session : sessions) {
            if (!times.contains(session.getTime())) {
                times.add(session.getTime());
            }
        }

        showSchedule(sessions, times);

    }

    public void teacherCredential(String username) {

        Credential credential = x.getTeacherCredential(username);
        if (credential == null) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        showCredential(credential);
    }


    public void viewGroupGrades() {

        String groupCode;
        Group group;

        while (true) {
            System.out.println("\nGROUP CODE IS REQUIRED: ");
            groupCode = readBasicString();
            if (groupCode == null)
                continue;

            group = x.findGroupByCode(groupCode);
            if (group == null) {
                System.out.println("\nINVALID CODE. TRY AGAIN: \n");
                continue;
            }

            break;
        }

        ArrayList<Student> students = group.getStudents();
        if (students == null || students.isEmpty()) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER\n");
            return;
        }

        for (Student user : students) {
            String username = user.getUsername();
            GradeRecord grade = x.getGradeFromStudent(groupCode, username);

            if (grade == null) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
                return;
            }

            System.out.println("\nSTUDENT: " + user.getFirstName() + " " + user.getLastName());
            System.out.println("USERNAME: " + user.getUsername() + " \n");

            ArrayList<GradeRecord> singleGrade = new ArrayList<>();
            singleGrade.add(grade);

            showGrades(singleGrade);

        }

    }



    public void assignGrades() {
        String code;
        String user;
        Student student;

        while (true) {
            System.out.println("\nGROUP CODE REQUIRED: ");
            code = readBasicString();
            if (code == null)
                continue;

            Group group = x.findGroupByCode(code);
            if (group == null) {
                System.out.println("\nINVALID CODE. TRY AGAIN: \n");
                continue;
            }

            while (true) {

                System.out.println("ENTER STUDENT'S USERNAME: ");
                user = readLongString();
                if (user == null)
                    continue;

                student = x.findStudentInGroup(code, user);
                if (student == null) {
                    System.out.println("\nINVALID USERNAME. TRY AGAIN: \n");
                    continue;
                }

                break;
            }

            break;
        }

        System.out.println("\nUSER FOUND: " + student.getFirstName() + " " + student.getLastName() + "\n");

        int opt;
        while (true) {

            System.out.println("\nENTER THE GRADE TO EDIT: ");
            System.out.println("---------------------------");
            System.out.println("FIRST PARTIAL -> 1");
            System.out.println("SECOND PARTIAL -> 2 ");
            System.out.println("THIRD PARTIAL -> 3");
            System.out.println("FINAL GRADE -> 4 ");
            System.out.println("CANCEL -> 0 ");
            opt = askIntegral();
            if (opt == -1)
                continue;

            if (opt < 0 || opt > 4) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            if (opt == 0) {
                System.out.println("\nNO CHANGES MADE.\n");
                return;
            }

            break;
        }

        System.out.println("\nENTER THE NEW GRADE: ");
        double newGrade = askDouble();

        if (!confirmAction())
            return;

        if (x.assignStudentGrade(code, user, opt, newGrade)) {
            System.out.println("\nGRADE WAS SUCCESSFULLY EDITED ;)\n");
            return;
        }

        System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");

    }

    public void teacherMainMenu(String username) {

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.println("SESSION STARTED ON " + Utils.currentDate() + " AT " + Utils.currentTime());
        System.out.println("-----------------------------------------------------------------------\n");

        Teacher teacher = x.findTeacherByUsername(username);
        if (teacher == null) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        System.out.println("\nHELLO " + teacher.getFirstName() + " " + teacher.getLastName() + "\n");

        while (true) {
            System.out.println("\n         MAIN MENU          ");
            System.out.println("----------------------------");
            System.out.println("VIEW MY SCHEDULE -> 1 ");
            System.out.println("CREDENTIAL -> 2");
            System.out.println("VIEW GROUP GRADES -> 3");
            System.out.println("ASSIGN GRADES -> 4");
            System.out.println("EXIT -> 0");
            System.out.println("----------------------------");
            int opt = askIntegral();
            if (opt == -1)
                continue;

            if (opt < 0 || opt > 4) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            switch (opt) {

                case 1:
                    teacherSchedule(username);

                    break;

                case 2:
                    teacherCredential(username);

                    break;

                case 3:
                    viewGroupGrades();

                    break;

                case 4:
                    assignGrades();

                    break;

                case 0:
                    System.out.println("\n");
                    if (confirmLogOut())
                        return;

                    System.out.println("\n");
                    break;

                default:
                    System.out.println("\nINVALID OPTION. TRY AGAIN LATER\n");
                    return;

            }


            if (afterFunction()) {
                System.out.println("\n");
                return;
            }


        }

    }

    public void addStudent() {

        while (true) {

            System.out.println("\nENTER THE STUDENT'S USERNAME: ");
            String username = readLongString();
            if (username == null)
                continue;

            if (!x.usernameIsAvailable(username)) {
                System.out.println("\nTHIS USERNAME IS ALREADY IN USE");
                continue;
            }

            System.out.println("\nENTER THE STUDENT'S FIRST NAME: ");
            String firstName = readBasicString();
            if (firstName == null)
                continue;

            System.out.println("\nENTER THE STUDENT'S LAST NAME: ");
            String lastName = readBasicString();
            if (lastName == null)
                continue;

            System.out.println("\nENTER DEGREE: ");
            String degree = readBasicString();
            if (degree == null)
                continue;

            String password = x.generatePassword();
            int studentCode = x.generateStudentCode();

            if (password == null || studentCode < 10000) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER. ");
                return;
            }

            Student student = new Student(username,
                    password,
                    firstName,
                    lastName,
                    0,
                    new Schedule(),
                    studentCode,
                    degree);

            if (x.registerStudent(student) == false) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER. ");
                return;
            }

            System.out.println("\nSTUDENT SUCCESFULLY REGISTERED. ");
            System.out.println("USERNAME: " + username);
            System.out.println("PASSWORD: " + password);
            System.out.println("STUDENT CODE: " + studentCode + "\n");

            break;

        }

    }

    public void addTeacher() {

        while (true) {

            System.out.println("\nENTER THE TEACHER'S USERNAME: ");
            String username = readLongString();
            if (username == null)
                continue;

            if (!x.usernameIsAvailable(username)) {
                System.out.println("\nTHIS USERNAME IS ALREADY IN USE");
                continue;
            }

            System.out.println("\nENTER THE TEACHER'S FIRST NAME: ");
            String firstName = readBasicString();
            if (firstName == null)
                continue;

            System.out.println("\nENTER THE TEACHER'S LAST NAME: ");
            String lastName = readBasicString();
            if (lastName == null)
                continue;

            System.out.println("\nENTER DEPARTMENT: ");
            String department = readBasicString();
            if (department == null)
                continue;

            String password = x.generatePassword();
            int employeeNumber = x.generateEmployeeNumber();

            if (password == null || employeeNumber < 20000000) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER");
                return;
            }

            Teacher teacher = new Teacher(
                    username,
                    password,
                    firstName,
                    lastName,
                    0,
                    new Schedule(),
                    employeeNumber,
                    department);

            if (x.registerTeacher(teacher) == false) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER");
                return;
            }

            System.out.println("\nTEACHER SUCCESSFULLY REGISTERED");
            System.out.println("USERNAME: " + username);
            System.out.println("PASSWORD: " + password);
            System.out.println("EMPLOYEE NUMBER: " + employeeNumber);

            break;

        }

    }

    public void addAdministrator() {

        while (true) {

            System.out.println("\nENTER THE ADMINISTRATOR'S USERNAME: ");
            String username = readLongString();
            if (username == null)
                continue;

            if (!x.usernameIsAvailable(username)) {
                System.out.println("\nTHIS USERNAME IS ALREADY IN USE");
                continue;
            }

            System.out.println("\nENTER THE ADMINISTRATOR'S FIRST NAME: ");
            String firstName = readBasicString();
            if (firstName == null)
                continue;

            System.out.println("\nENTER THE ADMINISTRATOR'S LAST NAME: ");
            String lastName = readBasicString();
            if (lastName == null)
                continue;

            System.out.println("\nENTER POSITION: ");
            String position = readBasicString();
            if (position == null)
                continue;

            String password = x.generatePassword();
            int employeeNumber = x.generateEmployeeNumber();

            if (password == null || employeeNumber < 20000000) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER ");
                return;
            }

            Administrator administrator = new Administrator(
                    employeeNumber,
                    username,
                    password,
                    firstName,
                    lastName,
                    0,
                    new Schedule(),
                    position);

            if (x.registerAdministrator(administrator) == false) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER");
                return;
            }

            System.out.println("\nADMINISTRATOR SUCCSSFULLY REGISTERED ");
            System.out.println("USERNAME: " + username);
            System.out.println("PASSWORD: " + password);
            System.out.println("EMPLOYEE NUMBER: " + employeeNumber);

            break;

        }

    }

    public void addSubject() {

        while (true) {
            System.out.println("\nENTER SUBJECT NAME: ");
            String name = readBasicString();
            if (name == null)
                continue;

            String code = x.generateSubjectCode(name);
            if (code == null) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.");
                return;
            }

            Subject subject = new Subject(name, code);
            if (!x.createSubject(subject)) {
                System.out.println("SOMETHING WENT WRONG. TRY AGAIN LATER");
                return;
            }

            break;

        }

    }

    public void addAcademicGroup() {

        while (true) {

            System.out.println("\nENTER THE SUBJECT CODE: ");
            String code = readBasicString();
            if (code == null) {
                continue;
            }

            Subject subject = x.findSubjectByCode(code);
            if (subject == null) {
                System.out.println("\nSUBJECT COULD NOT BE FOUND. TRY AGAIN: \n");
                continue;
            }

            int number;
            Teacher teacher;

            while (true) {

                System.out.println("\nENTER EMPLOYEE NUMBER TO FIND TEACHER: ");
                number = askIntegral();
                if (number == -1) {
                    continue;
                }

                teacher = x.findTeacherByEmployeeNumber(number);
                if (teacher == null) {
                    System.out.println("\nTEACHER COULD NOT BE FOUND. TRY AGAIN: ");
                    continue;
                }

                break;
            }

            String groupCode = x.generateGroupCode();

            Group group = new Group(subject, teacher, groupCode);
            if (!x.createAcademicGroup(group)) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
                return;
            }

            System.out.println("\nGROUP SUCCESFULLY CREATED");
            System.out.println("GROUP CODE: " + groupCode + " \n");
            return;

        }

    }

    public ArrayList<String> addDaysToClass() {

        ArrayList<String> days = new ArrayList<>();

        boolean monday = true;
        boolean tuesday = true;
        boolean wednesday = true;
        boolean thursday = true;
        boolean friday = true;

        int whichday = 1;

        while (true) {

            System.out.println("\nENTER DAY #" + whichday + ": ");

            if (monday) {
                System.out.println("\nMONDAY -> 1");
            }

            if (tuesday) {
                System.out.println("TUESDAY -> 2 ");
            }

            if (wednesday) {
                System.out.println("WEDNESDAY -> 3");
            }

            if (thursday) {
                System.out.println("THURSDAY -> 4");
            }

            if (friday) {
                System.out.println("FRIDAY -> 5");
            }

            System.out.println("FINISHED -> 0");
            int opt = askIntegral();
            if (opt == -1)
                continue;

            if (opt < 0 || opt > 5) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            switch (opt) {

                case 1:
                    if (!monday) {
                        System.out.println("\nDAY ALREADY SELECTED. ADD A DIFFERENT DAY: \n");
                        continue;
                    }

                    monday = false;
                    days.add("MDY");
                    break;

                case 2:
                    if (!tuesday) {
                        System.out.println("\nDAY ALREADY SELECTED. ADD A DIFFERENT DAY: \n");
                        continue;

                    }

                    tuesday = false;
                    days.add("TDY");
                    break;

                case 3:
                    if (!wednesday) {
                        System.out.println("\nDAY ALREADY SELECTED. ADD A DIFFERENT DAY: \n");
                        continue;

                    }

                    wednesday = false;
                    days.add("WDY");
                    break;

                case 4:
                    if (!thursday) {
                        System.out.println("\nDAY ALREADY SELECTED. ADD A DIFFERENT DAY: \n");
                        continue;
                    }

                    thursday = false;
                    days.add("THDY");
                    break;

                case 5:
                    if (!friday) {
                        System.out.println("\nDAY ALREADY SELECTED. ADD A DIFFERENT DAY: \n");
                        continue;
                    }

                    friday = false;
                    days.add("FDY");
                    break;

                case 0:
                    if (days.isEmpty()) {
                        System.out.println("\nNO CLASS IS BEING CREATED\n");
                        if (!confirmAction())
                            continue;

                        return days;

                    }

                    return days;

                default:
                    System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                    continue;
            }

            whichday++;

            if (days.size() == 5)
                return days;

        }

    }

    public String assignTime() {

        while (true) {

            System.out.println("\nENTER THE CLASS TIME: \n");
            System.out.println("--------------------------");
            System.out.println("8:30 -> 1");
            System.out.println("10:00 -> 2");
            System.out.println("11:30 -> 3");
            System.out.println("13:00 -> 4");
            System.out.println("14:30 -> 5");
            System.out.println("16:00 - 6");
            System.out.println("CANCEL -> 0");
            System.out.println("--------------------------\n");
            int opt = askIntegral();

            if (opt == -1)
                continue;

            if (opt < 0 || opt > 6) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            switch (opt) {

                case 1:
                    System.out.println("YOU ARE SELECTING 8:30");
                    if (confirmAction())
                        return "8:30";

                    System.out.println("\nTIME WAS NOT SELECTED\n");
                    continue;

                case 2:
                    System.out.println("YOU ARE SELECTING 10:00");
                    if (confirmAction())
                        return "10:00";

                    System.out.println("\nTIME WAS NOT SELECTED\n");
                    continue;

                case 3:
                    System.out.println("YOU ARE SELECTING 11:30");
                    if (confirmAction())
                        return "11:30";

                    System.out.println("\nTIME WAS NOT SELECTED\n");
                    continue;

                case 4:
                    System.out.println("YOU ARE SELECTING 13:00");
                    if (confirmAction())
                        return "13:00";

                    System.out.println("\nTIME WAS NOT SELECTED\n");
                    continue;

                case 5:
                    System.out.println("YOU ARE SELECTING 14:30");
                    if (confirmAction())
                        return "14:30";

                    System.out.println("\nTIME WAS NOT SELECTED\n");
                    continue;

                case 6:
                    System.out.println("YOU ARE SELECTING 16:00");
                    if (confirmAction())
                        return "16:00";

                    System.out.println("\nTIME WAS NOT SELECTED\n");
                    continue;

                case 0:
                    System.out.println("NO CLASS IS BEING CREATED");
                    if (confirmAction())
                        return "";

                    System.out.println("\nACTION CANCELED\n");
                    continue;

                default:
                    System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                    continue;

            }
        }

    }

    public void addClassSession() {

        String classCode = x.generateClassCode();
        String groupCode;
        Group group;

        while (true) {
            System.out.println("\nGROUP CODE REQUIRED: \n");
            groupCode = readBasicString();
            if (groupCode.isEmpty())
                continue;

            group = x.findGroupByCode(groupCode);

            if (group == null) {
                System.out.println("\nINVALID CODE. TRY AGAIN: \n");
                continue;
            }

            break;
        }

        Subject subject = group.getSubject();

        String time = assignTime();
        if (time.isEmpty())
            return;

        ArrayList<String> days = addDaysToClass();
        if (days.isEmpty()) {
            return;
        }

        ArrayList<ClassSession> classes = new ArrayList<>();

        for (String day : days) {

            ClassSession classSession = new ClassSession(classCode, groupCode, subject, time, day);
            classes.add(classSession);

        }

        if (!x.createClassSessions(classes)) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        System.out.println("\nCLASS SUCCESFULLY CREATED.");
        System.out.println("\nCLASS CODE: " + classCode + "\n");

    }



    public void enrollStudent() {

        Student student;
        Group group;
        String groupCode;

        while (true) {
            System.out.println("\nSTUDENT CODE IS REQUIRED: ");
            int studentCode = askIntegral();
            if (studentCode == -1)
                continue;

            student = x.findStudentByStudentCode(studentCode);
            if (student == null) {
                System.out.println("\nSTUDENT COULD NOT BE FOUND. TRY AGAIN LATER.\n");
                return;
            }

            break;
        }

        while (true) {

            System.out.println("\nGROUP CODE IS REQUIRED: ");
            groupCode = readBasicString();
            if (groupCode.isEmpty())
                continue;

            group = x.findGroupByCode(groupCode);
            if (group == null) {
                System.out.println("\nGROUP COULD NOT BE FOUND. TRY AGAIN LATER.\n");
                return;
            }

            break;
        }

        String username = student.getUsername();

        if (x.findStudentInGroup(groupCode, username) != null) {
            System.out.println("\nSTUDENT ALREADY ENROLLED IN THIS GROUP.");
            return;
        }

        if (!x.enrollStudent(student, group)) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        System.out.println("\nSTUDENT SUCCESSFULLY ENROLLED.");

    }




    public void changeUserPassword() {

        String username;
        User user;
        String newPassword;

        while (true) {

            System.out.println("\nUSERNAME REQUIRED: ");
            username = readBasicString();

            if (username.isEmpty())
                continue;

            user = x.findUserByUsername(username);

            if (user == null) {
                System.out.println("\nUSER COULD NOT BE FOUND. TRY AGAIN LATER\n");
                return;
            }

            break;
        }

        System.out.println("\nUSER " + user.getFirstName() + " " + user.getLastName() + " WAS FOUND");

        while (true) {

            System.out.println("\nENTER THE NEW PASSWORD: \n");
            newPassword = readLongString();

            if (newPassword.isEmpty())
                continue;

            if (!x.validPasswordLength(newPassword)) {
                System.out.println("\nPASSWORD EXCEEDS THE MAXIMUM SIZE SUPPORTED BY THE SYSTEM.\n");
                continue;
            }

            if (!x.updatePassword(username, newPassword)) {
                System.out.println("\nPASSWORD COULD NOT BE UPDATED. TRY AGAIN LATER");
                return;
            }

            break;

        }

        System.out.println("\nPASSWORD SUCCESSFULLY UPDATED\n");

    }


    public User findUserForAction() {

        while (true) {

            System.out.println("\nENTER USERNAME: ");
            String username = readBasicString();

            if (username.isEmpty())
                continue;

            User user = x.findUserByUsername(username);

            if (user == null) {
                System.out.println("\nUSER COULD NOT BE FOUND.\n");
                return null;
            }

            System.out.println("\nUSER " + user.getFirstName() + " " + user.getLastName() + " WAS FOUND");

            return user;
        }

    }

    public String askNewValue(String field) {

        while (true) {

            System.out.println("\nENTER NEW " + field + ": ");
            String value = readBasicString();

            if (value.isEmpty())
                continue;

            System.out.println("\nNEW " + field + ": " + value);

            if (!confirmAction())
                continue;

            return value;
        }

    }

    public boolean updateFirstName(User user) {

        String firstName = askNewValue("FIRST NAME");

        return x.updateFirstName(user.getUsername(), firstName);
    }

    public boolean updateLastName(User user) {

        String lastName = askNewValue("LAST NAME");

        return x.updateLastName(user.getUsername(), lastName);

    }

    public String getSpecificField(User user) {

        if (user instanceof Student)
            return "DEGREE";

        if (user instanceof Teacher)
            return "DEPARTMENT";

        if (user instanceof Administrator)
            return "POSITION";

        return "";

    }

    public boolean updateSpecificField(User user) {

        if (user instanceof Student) {

            Student student = (Student) user;
            String degree = askNewValue("DEGREE");

            return x.updateStudentDegree(student.getStudentCode(), degree);

        }

        if (user instanceof Teacher) {

            Teacher teacher = (Teacher) user;
            String department = askNewValue("DEPARTMENT");

            return x.updateTeacherDepartment(teacher.getEmployeeNumber(), department);
        }

        if (user instanceof Administrator) {

            Administrator administrator = (Administrator) user;
            String position = askNewValue("POSITION");

            return x.updateAdministratorPosition(administrator.getEmployeeNumber(), position);

        }

        return false;

    }

    public void updateUserui() {

        User user = findUserForAction();

        if (user == null)
            return;

        while (true) {

            System.out.println("\nWHAT WOULD YOU LIKE TO UPDATE?");
            System.out.println("--------------------------------");
            System.out.println("FIRST NAME -> 1");
            System.out.println("LAST NAME -> 2");
            System.out.println(getSpecificField(user) + " -> 3");
            System.out.println("CANCEL -> 0");
            System.out.println("--------------------------------");
            int opt = askIntegral();

            if (opt == -1)
                continue;

            boolean result;

            switch (opt) {

                case 1:
                    result = updateFirstName(user);
                    break;

                case 2:
                    result = updateLastName(user);
                    break;

                case 3:
                    result = updateSpecificField(user);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("\nINVALID OPTION. TRY AGAIN.\n");
                    continue;
            }

            if (!result) {
                System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
                return;
            }

            System.out.println("\nUSER SUCCESSFULLY UPDATED.\n");
            return;
        }

    }

    public void deleteUserui() {

        User user = findUserForAction();

        if (user == null)
            return;

        System.out.println("\nYOU ARE ABOUT TO DELETE:");
        System.out.println(user.getFirstName() + " " + user.getLastName());
        System.out.println("USERNAME: " + user.getUsername());

        if (!confirmAction())
            return;

        if (!x.deleteUser(user)) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        System.out.println("\nUSER SUCCESSFULLY DELETED.\n");

    }

    public void adminMainMenu(String username) {

        Administrator administrator = x.findAdministratorByUsername(username);

        if (administrator == null) {
            System.out.println("\nSOMETHING WENT WRONG. TRY AGAIN LATER.\n");
            return;
        }

        System.out.println("\n-----------------------------------------------------------------------");
        System.out.println("SESSION STARTED ON " + Utils.currentDate() + " AT " + Utils.currentTime());
        System.out.println("-----------------------------------------------------------------------\n");

        System.out.println("\nHELLO " + administrator.getFirstName() + " " + administrator.getLastName() + "\n");

        while (true) {

            System.out.println("\nENTER THE OPTION TO CARRY OUT:");
            System.out.println("----------------------------------------");
            System.out.println("ADD NEW STUDENT -> 1");
            System.out.println("ADD NEW TEACHER -> 2");
            System.out.println("ADD NEW ADMINISTRATOR -> 3");
            System.out.println("ADD NEW SUBJECT -> 4");
            System.out.println("ADD NEW ACADMIC GROUP -> 5");
            System.out.println("ADD NEW CLASS SESSION -> 6");
            System.out.println("ENROLL STUDENT -> 7");
            System.out.println("UPDATE USER -> 8");
            System.out.println("DELETE USER -> 9");
            System.out.println("CHANGE PASSWORD -> 10");
            System.out.println("EXIT -> 0");
            System.out.println("----------------------------------------");
            int opt = askIntegral();

            if (opt == -1)
                continue;

            if (opt < 0 || opt > 10) {
                System.out.println("\nINVALID OPTION. TRY AGAIN: \n");
                continue;
            }

            switch (opt) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    addTeacher();
                    break;

                case 3:
                    addAdministrator();
                    break;

                case 4:
                    addSubject();
                    break;

                case 5:
                    addAcademicGroup();
                    break;

                case 6:
                    addClassSession();
                    break;

                case 7:
                    enrollStudent();
                    break;

                case 8:
                    updateUserui();
                    break;

                case 9:
                    deleteUserui();
                    break;

                case 10:
                    changeUserPassword();
                    break;

                case 0:

                    System.out.println("\n");

                    if (confirmLogOut())
                        return;

                    System.out.println("\n");
                    continue;

            }

            if (afterFunction()) {
                System.out.println("\n");
                return;
            }

        }

    }



    public void logInScreen() {

        while (true) {

            System.out.println("\n");
            System.out.println("==================================================");
            System.out.println("|                 SNOWFLAKE                      |");
            System.out.println("|              ACADEMIC SYSTEM                   |");
            System.out.println("==================================================");
            System.out.println("|  1. STUDENT                                    |");
            System.out.println("|  2. TEACHER                                    |");
            System.out.println("|  3. ADMINISTRATOR                              |");
            System.out.println("|  0. EXIT                                       |");
            System.out.println("==================================================");
            int opt = askIntegral();


            if (opt == -1) continue;

            
            if (opt == 0) {
                System.out.println("\nSYSTEM CLOSED.\n");
                return;
            }

            if (opt < 1 || opt > 3) {
                System.out.println("\n[!] INVALID OPTION.\n");
                continue;
            }

            System.out.println("\n--------------------------------------------------");

            System.out.print("USERNAME: ");
            String username = readLongString();

            if (username == null) {
                System.out.println("\n[!] USERNAME CANNOT BE EMPTY.\n");
                continue;
            }

            System.out.print("PASSWORD: ");
            String password = readLongString();

            if (password == null) {
                System.out.println("\n[!] PASSWORD CANNOT BE EMPTY.\n");
                continue;
            }

            System.out.println("--------------------------------------------------");

            switch (opt) {

                case 1:

                    if (x.validateStudentLogIn(username, password)) {
                        studentMainMenu(username);
                    } else {
                        System.out.println("\n[X] ACCESS DENIED.\n");
                    }

                    break;

                case 2:

                    if (x.validateTeacherLogIn(username, password)) {
                        teacherMainMenu(username);
                    } else {
                        System.out.println("\n[X] ACCESS DENIED.\n");
                    }

                    break;

                case 3:

                    if (x.validateAdministratorLogIn(username, password)) {
                        adminMainMenu(username);
                    } else {
                        System.out.println("\n[X] ACCESS DENIED.\n");
                    }

                    break;
            }

        }
        
    }

}

class SNOWFLAKE {
    public static void main(String[] args) {

        System.out.println("\n");
        System.out.println("=================================================");
        System.out.println("|                  WELCOME                       |");
        System.out.println("=================================================");

        RepositorySQL repoSQL = new RepositorySQL();

        if (!repoSQL.isConnected()) {
            System.out.println("\n[X] DATABASE CONNECTION FAILED.");
            System.out.println("[X] SYSTEM STARTUP ABORTED.\n");
            return;
        }


        System.out.println("\n[+] DATABASE CONNECTION ESTABLISHED.");


        if (!repoSQL.createTables()) {
            System.out.println("[X] DATABASE INITIALIZATION FAILED.");
            System.out.println("[X] SYSTEM STARTUP ABORTED.\n");
            return;
        }

        System.out.println("[+] DATABASE READY.");
        System.out.println("[+] SYSTEM READY.");
        System.out.println("==================================================\n");


        AcademicManagement system = new AcademicManagement(repoSQL);
        ConsoleUI console = new ConsoleUI(system);

        console.logInScreen();

    }

}






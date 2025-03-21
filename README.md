# 📚 Инструкция по сдаче заданий

## 1. Создайте форк репозитория
1. Войдите в свой аккаунт (если еще не вошли).
2. Перейдите в [репозиторий](https://github.com/AlgosStile/automation-course).
3. Нажмите кнопку **Fork** в правом верхнем углу далее Create fork (зеленая кнопка)

## 2. Клонируйте репозиторий
Откройте терминал (Git Bash, Command Prompt или другой) и выполните:
```bash
git clone https://github.com/ВАШ_ЛОГИН/automation-course.git
```
 * Замените ВАШ_ЛОГИН и НАЗВАНИЕ_РЕПОЗИТОРИЯ на свои данные.

# Пример для студента с логином student123

## 1. Клонирование репозитория

```bash
git clone https://github.com/student123/automation-course.git
```

## 2. Настройка окружения

### Установите Java 17:
- [Скачать Eclipse Temurin](https://adoptium.net/)

### Установите Maven:
- [Инструкция по установке](https://maven.apache.org/install.html)

### Откройте проект в IntelliJ IDEA или Eclipse.

В терминале idea введите команду:
### Для Linux/macOS:
`mkdir -p .github/workflows`

### Для Windows (в командной строке или PowerShell):
`mkdir .github\workflows`

В итоге вы создадите 2 папки и файл  с названием tests.yml

Переходим в tests.yml и вставляем команду ниже:

```
name: Java CI

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4

      - name: Set up Java 17
        uses: actions/setup-java@v3
        with:
          distribution: temurin
          java-version: "17"

      - name: Cache Maven dependencies
        uses: actions/cache@v3
        with:
          path: ~/.m2/repository
          key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
          restore-keys: |
            ${{ runner.os }}-maven-

      - name: Install system dependencies
        run: |
          sudo apt-get update
          sudo apt-get install -y xvfb libgbm-dev x11-xkb-utils  

      - name: Run tests
        run: |
          export DISPLAY=:0
          Xvfb :0 -screen 0 1280x720x24 >/dev/null 2>&1 &  
          mvn clean test -B
   ```

## 3. Реализация тестов

Добавьте код автотестов в папку `src/test/java`.

### Проверьте тесты локально:

```bash
mvn test
```

## 4. Отправка кода на GitHub

```bash
git add .
git commit -m "Добавлены автотесты"
git push origin main
```

## 5. Проверка в GitHub Actions

Перейдите в ваш репозиторий → вкладка Actions:  
[https://github.com/ВАШ_ЛОГИН/automation-course/actions](https://github.com/ВАШ_ЛОГИН/automation-course/actions)

Убедитесь, что workflow **Run Tests** завершился с ✅ **Success**:


## 6. Сдача задания на Stepik

Скопируйте ссылку на успешный запуск:

1. Нажмите на название workflow (например, **Run Tests**).
2. URL в адресной строке GitHub будет выглядеть так:  
   `https://github.com/ВАШ_ЛОГИН/automation-course/actions/runs/123456789`

### Пример для студента student123:
`https://github.com/student123/automation-course/actions/runs/987654321`

Вставьте эту ссылку в поле ответа на Stepik.

## 🔧 Если что-то пошло не так

### ❓ Тесты упали:
Проверьте логи во вкладке Actions → Run Tests → Run tests.

### ❓ Если тесты не запускаются:
- Убедитесь, что тесты в папке `src/test/java`
- Проверьте, что классы называются `*Test.java`
- Если видите ошибку `No tests found`, добавьте пустой тест:
  ```java
  @Test
  void dummyTest() {
   assertTrue(true); 
  }
  ```

### ❓ Проверьте структуру проекта

#### Должно быть:
```plaintext
src/
└── test/
    └── java/
        └── YourTest.java - Название должно заканчиваться на Test.java
```

### ❓ Ошибка доступа:
Убедитесь, что ваш репозиторий публичный, а не приватный.

### ❓ GitHub Actions не запускается:
Убедитесь, что файл `.github/workflows/tests.yml` существует.




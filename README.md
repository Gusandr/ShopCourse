# ShopCourse - Android приложение для курсов

## Описание проекта

**ShopCourse** - это Android-приложение для просмотра и управления IT-курсами. Приложение построено с использованием современных Android-технологий и принципов Clean Architecture.

### Основные возможности

- 🔐 **Авторизация пользователей** с валидацией email
- 📱 **Просмотр каталога курсов** с загрузкой данных из API
- ❤️ **Избранные курсы** с локальным сохранением в базе данных
- 🔄 **Сортировка курсов** по дате публикации
- 📋 **Bottom Navigation** для удобной навигации между разделами

## Установка и запуск

### Шаги установки
Клонировать репозиторий:
```bash
git clone https://github.com/Gusandr/ShopCourse.git
```

И после этого открыть репозиторий в Android Studio

## Технический стек

### Core Technologies
- **Kotlin** - основной язык разработки
- **Clean Architecture** - архитектурный подход
- **MVVM** - паттерн представления данных
- **Многомодульность** - разделение проекта на модули

### Android Libraries
- **Retrofit** - работа с HTTP API
- **Kotlin Coroutines** - асинхронное программирование
- **Flow** - реактивное программирование
- **Hilt** - Dependency Injection
- **Room** - локальная база данных
- **Navigation Component** - навигация между экранами

## Архитектура проекта

Проект разбит на три модуля:

```
ShopCourse/
├── app/
│   └── src/main/java/ru/gusandr/shopcourse/
│       ├── di/
│       └── presentation/
│           ├── navigation/
│           └── screens/
│               ├── auth/
│               │   ├── login/
│               │   └── registration/
│               ├── courses/
│               │   ├── adapter/
│               │   ├── favorites/
│               │   └── list/
│               ├── onboarding/
│               └── profile/
│
├── data/
│   └── src/main/java/ru/gusandr/data/
│       ├── local/
│       │   └── storage/
│       │       ├── dao/
│       │       ├── database/
│       │       └── entities/
│       ├── mapper/
│       ├── remote/
│       │   └── dto/
│       └── repository/
│
└── domain/
    └── src/main/java/ru/gusandr/domain/
        ├── model/
        ├── repository/
        └── usecase/
```

**Зависимости:**
- app → domain + data
- data → domain
- domain - независимый

**Слои:**
- **Domain** - модели и бизнес-логика
- **Data** - работа с API и базой данных
- **Presentation** - экраны и UI

## Основные экраны

### 1. Экран входа
- Валидация email по маске `текст@текст.текст`
- Проверка заполненности полей
- Интеграция с ВК и Одноклассниками
- Переход на главный экран после успешной авторизации

### 2. Главный экран
- Отображение списка курсов из API
- Сортировка по дате публикации
- Добавление/удаление курсов из избранного

### 3. Избранное
- Отображение курсов, добавленных в избранное
- Локальное хранение в базе данных Room
- Синхронизация состояния с главным экраном

### 4. Bottom Navigation
- Главная (по умолчанию)
- Избранное
- Аккаунт (заглушка)

## API Integration

Приложение работает с JSON API для получения данных о курсах:

**Структура данных курса:**
```json
{
  "id": "string",
  "title": "string",
  "text": "string",
  "price": "number",
  "rate": "number",
  "startDate": "string",
  "hasLike": "boolean",
  "publishDate": "string"
}
```
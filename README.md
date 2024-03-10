## Как войти в админку
В любом созданном ресторане всегда есть только один аккаунт админа, он создается автоматически. Чтобы войти в него, надо ввести логин **admin** и пароль **admin**. А все остальные аккаунты, создаваемые через регистрацию в приложении, будут автоматически считаться обычными посетителями.

## Про хранение данных
Данные о ресторане хранятся в файле, создаваемом с помощью встроенной сериализации Java. Там хранятся: данные для входа в созданные аккаунты, текущее состояние меню, информация обо всех заказах пользователей (при чем сохраняется прогресс каждого заказа, то есть приготовление незавершенных заказов при запуске программы будет начинаться с того места, на котором прогресс был при завершении программы). Вся информация сохраняется автоматически при любом действии пользователя, меняющем состояние хранимых данных, а также при выходе из программы.

Путь к файлу, где хранятся данные, указывается через аргументы командной строки (программа тестировалась при значении **restaurant.dat**). Если при запуске указать путь, по которому не лежит никакая информация о ресторане (например, указать пока не существующий файл), то автоматически создастся ресторан "по умолчанию" - в нем будет только 1 аккаунт админа со стандартным логином и паролем. Все последующие действия в этом ресторане, естественно, будут сохраняться в указанный файл и при новом запуске успешно загрузятся.

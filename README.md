## Как войти в админку
В любом созданном ресторане всегда есть только один аккаунт админа, он создается автоматически. Чтобы войти в него, надо ввести логин **admin** и пароль **admin**. А все остальные аккаунты, создаваемые через регистрацию в приложении, будут автоматически считаться обычными посетителями.

## Про хранение данных
Данные о ресторане хранятся в файле, создаваемом с помощью встроенной сериализации Java. Там хранятся: данные для входа в созданные аккаунты (все пароли хранятся с использованием шифрования AES), текущее состояние меню, статистика ресторана, информация обо всех заказах пользователей (причем сохраняется прогресс каждого заказа, то есть приготовление незавершенных заказов при запуске программы будет начинаться с того места, на котором прогресс был при завершении программы). Вся информация сохраняется автоматически при любом действии пользователя, меняющем состояние хранимых данных, а также при выходе из программы.

Путь к файлу, где хранятся данные, указывается через аргументы командной строки (программа тестировалась при значении **restaurant.dat**). Если при запуске указать путь, по которому не лежит никакая информация о ресторане (например, если указан пока не существующий файл), то автоматически создастся ресторан "по умолчанию" - в нем будет только 1 аккаунт админа со стандартным логином и паролем. Все последующие действия в этом ресторане, естественно, будут сохраняться в указанный файл, и при новом запуске они успешно загрузятся.

## Про обработку заказов
Заказы обрабатываются в многопоточном режиме, симулируя работу 3 поваров. То есть одновременно в ресторане могут готовиться только 3 заказа (даже если они созданы в разных аккаунтах). Если все 3 повара заняты, то заказ сохраняет статус "В обработке", пока кто-то не освободится. У готовящегося заказа статус "Готовится", а у уже приготовленного - "Готов".

## Паттерны

#### 1. State.
В программе есть разные типы пользовательских меню: меню аутентификации, меню авторизированного посетителя и меню авторизированного админа. В каждом из этих меню должен быть метод printMenu(), а его поведение, очевидно, зависит от типа самого меню. Было принято решение использовать паттерн State: все перечисленные виды меню представляются одним классом - Menu, но у каждого экземпляра в зависимости от типа хранится разный MenuPrinter - интерфейс, в котором как раз есть метод printMenu(). При создании очередного экзмепляра класса Menu у него устанавливается нужный принтер, чтобы иметь возможность печатать разные виды меню одинаковым методом.

#### 2. Observer.
Каждый заказ в программе является экземплятор класса Order. При этом все заказы конкретного пользователя хранятся у самого пользователя в соответствующем arrayList<Order>. Чтобы иметь возможность удалить из списка готовящихся заказов у пользователя уже приготовленный заказ из самого класса Order, в самом заказе хранится поле client, хранящее ссылку на клиента, оформившего заказ. Значение этого поля устанавливается через специальный метод setClient.

#### 3. Builder.
Блюда в программе хранятся как экземпляры класса Dish, в котором довольно много полей. При этом при создании блюда значение каждого поля еще и надо спросить у пользователя, а затем провалидировать. Поэтому создание блюд происходит через методы для установки значения очередного поля полученным значением с помощью встроенного в lombok Билдера. 

## Дополнительный функционал
В приложении помимо обязательного функционала реализован еще и дополнительный, а именно:
1. После оплаты посетители могут оценивать заказы: ставить оценку от 1 до 5 и оставлять текстовый комментарий.
2. Админ может просматривать статистику ресторана: общую прибыль (за оплаченные заказы), среднюю прибыль за заказ (считаются только уже оплаченные заказы), среднюю оценку за заказы (считаются только уже оцененные заказы).
3. Система приоритетов при обработке заказов: если в обработке несколько заказов, то сначала принимается тот, у которого максимальная сумма цен блюд; если таких "самых дорогих" заказов несколько, то принимается тот, который был создан первым.

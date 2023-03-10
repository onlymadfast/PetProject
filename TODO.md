# PetProject - Internet Magazine - Backend

## Назначение
Backend часть приложения

## Описание
Проект представляет возможность работать с полноценным UI, 
выполняя различные операции по приобретению товара в интернет магазине.

### Основные функции
Описание основных функций приложения

### Локальная сборка всего приложения
Сделать два сервиса, чтобы собиралось в докере все по клику

## Ссылки
*
*

## Контакты
Вася, телеграм


--------------------------------------
помни: 
- angular-app лежит в корне user'a
- л/п postgres/admin - Postgresql

нужно сделать: 

- сформулировать свое приложение на бумаге
- разработать архитектуру БД
- сделать зеркало БД в сущностях приложения
- определить возможности своего приложения
- разработать слой бизнес логики
- подкрутить Angular


/////////////////////////////

1. Как происходит покупка товаров в интернет-магазине:
   
    а. Зарегистрирован:
        а.1: Выбор товара в корзину (Формирование заказа - *(1))
        а.2: Создается заказ, показывается клиенту
        а.3: Оплата товара -> 
                если адреса у клиента до сих пор нет -> [AddressService.isUserHaveAddress],
                (тогда вызываем метод создания адреса AddressService.createOrUpdateAddressForUser)
                если адрес есть, то оплачиваем заказ - *(2)
        а.4: Выдача окна где все подтверждается, если что-то не так, 
                отправляется на доработку(вызываются соответствующие методы update), 
                иначе вызывается метод последней проверки, что все окей *(3)
   
        б.1: Клиент может заказывать любое количество товара, которое допустимо на складе

    б. Не зарегистрирован:
        а.1: При желании заказать товар, клиент отправляется на регистрацию (смотри пункт выше)

2. Работа в личном кабинете:
    
    а. Создание/Редактирование профиля:
        а.1: Смена любого доступного, для редактирования поля у клиента
        а.2: Замена пароля
        а.3: Создание/Редактирование адреса
   
    б. Если это админ:
        а.1: Отображение заказов клиента, пагинация, поиск
        а.2: Возможность руками удалить заказ клиента  ??? (если заказ был удален, то отправляется пуш клиенту в личный кабинет)
        а.3: Создание/Редактирование товаров в магазине
        а.4: Регулирование количества товара на складе [ItemService.increaseOrDecreaseItemQuantityOnWarehouse]



Проработать непонятные моменты:
- что если на каком-то из этапов оборвется связь с клиентом;
- может, стоит соединить 3 метода в один и сделать его CompletableFuture(?);
- продумать вычитание/прибавление кол-ва товара ...
 ... вычитать то число, сколько заказали при checkOrderAndCommit,
  ...  А прибавлять если вдруг надо УДАЛИТЬ заказ админу, но тут надо аккуратно.
 - updateOrder метод, продумать статусную модель, какие статусы можно менять и когда


*(1) 
Клиент имеет id, он проставляется в заказ из сессии
мы отправляем информацию на метод OrderService.createOrder(userId, Order)
- внутри мы проверяем, есть ли адрес доставки? 
    если есть проставляем:
        StatusOrder - PENDING_PAYMENT,
    если нет проставляем:
        StatusOrder - WITHOUT_ADDRESS
- проставляем другие статусы:
    HowPay - в зависимости от того, что пришло
    HowDeliver - в зависимости от того, что пришло
    StatusPay - NOT_PAID - [***] в будущем сделать доработку чтобы можно было оплачивать сразу
  
*(2)
Вызывается метод оплаты товара OrderService.payOrder(userId, Order, Address(?)),
где проставляются статусы:
    StatusPay - PAID
    StatusOrder - ON_THE_WAY
ВАЖНО! Если у клиента вдруг нет адреса, то сюда придет адрес, 
        и тогда надо будет сохранить адрес клиенту.

*(3)
Метод проверки всего заказа OrderService.checkOrderAndCommit(userId, Order)
тут все проверяется, если:
            - StatusPay - PAID
        тогда -> approvedFlag - true и уменьшается кол-во каждого предмета на складе
                затем заказ становится реальным.


Статусная модель:

HowDeliver.DELIVERY - доставка
HowDeliver.POINT_OF_DELIVERY - пункт доставки, пока без реализации

HowPay.CARD - оплата картой
HowPay.CASH - оплата наличными при доставке, будет зависеть от выбора HowDeliver

Role.USER - роль зарегистрированного клиента, доступен функционал магазина(выбор, покупка, оплата товара, просмотр личного кабинета)
Role.ADMIN - не доступен заказ товара, но доступно просмотр и изменение заказов клиента + создание товаров
Role.DEVELOPER - ?
Role.ANONYMOUS - роль присеваемая всем кто не зарегистрирован в системе

StatusOrder.PENDING_PAYMENT - статус если есть адрес и нет оплаты
StatusOrder.WITHOUT_ADDRESS - статус, когда нет адреса
StatusOrder.ON_THE_WAY - когда уже имеем адрес и StatusPay.PAID
StatusOrder.DELIVERED - когда заказ доставлен

StatusPay.NOT_PAID - заказ не оплачен, по умолчанию
StatusPay.PAID - оплачен только если есть адрес для доставки ?? Продумать с пунктами доставки !!!

ApprovedFlag - если заказ существует и оплачен

Вычитание и прибавление товара:
- товар вычитается/прибавляется(кол-во на складе) админом, меняется кол-во товара на складе Item.quantityOnWarehouse
- товар вычитается/прибавляется(кол-во самого товара):
    - при удачной оплате заказа кол-во товара обновляется у каждой заказанной позиции;
    - при удалении заказа админом, товар возвращается на склад;

Controllers & Permissions

AddressController:
    - user/address/{userId} - GET - getAddressFromUser            - ROLE_USER, ROLE_ADMIN
    - user/address/{userId} - POST - createOrUpdateAddressForUser - ROLE_USER, ROLE_ADMIN
    - user/address/{userId} - DELETE - deleteUserAddress          - ROLE_USER, ROLE_ADMIN

AuthController: 
    - security/auth/authenticate - POST - authUser - permitAll -> возможно стоит настроить только на фронт обращение?

ItemController: 
    - /items/view/list - GET - getAllItems                           - ROLE_USER, ROLE_ADMIN, ROLE_ANONYMOUS
    - /items/view/{itemId} - GET - getItemById                       - ROLE_USER, ROLE_ADMIN, ROLE_ANONYMOUS
    - /items/view/item/category/{category} - GET - getItemByCategory - ROLE_USER, ROLE_ADMIN, ROLE_ANONYMOUS
    - /items/view/popular - GET - getTopItems                        - ROLE_USER, ROLE_ADMIN, ROLE_ANONYMOUS
    - /items/edit - POST - createItem                                - ROLE_ADMIN
    - /items/edit/{itemId} - PUT - updateItem                        - ROLE_ADMIN
    - /items/edit/{itemId} - DELETE - deleteItem                     - ROLE_ADMIN

OrderController:
    - /order/orders/{userId} - GET - getOrdersByUserId            - ROLE_USER, ROLE_ADMIN
    - /order/make/{userId} - POST - createOrder                   - ROLE_USER
    - /order/edit/{orderId}/{userId} - DELETE - deleteOrder       - ROLE_ADMIN

UserController:
    - /user/access/list - GET - getAllUsers                      - ROLE_ADMIN
    - /user/access/{id} - GET - getUserById                      - ROLE_ADMIN
    - /user/access/create - POST - createUser                    - ROLE_USER, ROLE_ADMIN, ROLE_ANONYMOUS
    - /user/access/edit/{id} - PUT - updateUser                  - ROLE_ADMIN, ROLE_USER
    - /user/access/edit/pass/{id} - PUT - updatePassword         - ROLE_USER
    - /user/access/edit/{id} - DELETE - deleteUserById           - ROLE_ADMIN

RoleController:
    - /role/access/list - GET - getAllRoles                       - ROLE_ADMIN
    - /role/access/edit - POST - createRole                       - ROLE_ADMIN
    - /role/access/edit/{roleId} - DELETE - deleteRole            - ROLE_ADMIN
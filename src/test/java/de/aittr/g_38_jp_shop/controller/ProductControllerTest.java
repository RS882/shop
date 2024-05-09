package de.aittr.g_38_jp_shop.controller;


import de.aittr.g_38_jp_shop.domain.dto.ProductDto;
import de.aittr.g_38_jp_shop.domain.entity.Role;
import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.ProductRepository;
import de.aittr.g_38_jp_shop.repository.RoleRepository;
import de.aittr.g_38_jp_shop.repository.UserRepository;
import de.aittr.g_38_jp_shop.security.sec_dto.TokenResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//чтобы приложение запускалось в полноценном режиме - устанвлениваем случайны порт для того чтобы не занять основной
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// анотация чтобы тесты оперделенные в этом классе заупскать в опередленном порядке
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // аргумент указывает что мы будет писать
// на методах анатоция order которая оперделяет порядок
class ProductControllerTest {

    // записывется порт не которм стартует приложение
    @LocalServerPort
    private int port;

    // подключаем все репозитории
    @Autowired // коструктор в тестарх работат по другому. тут используем autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    // обект чтобі отправлять http запросы на наше приложение. обращаемся к обекту ,
    //вызываем нужные матоды и отпералям http запросы
    private TestRestTemplate template;
    //для описание заголовков в запросах
    private HttpHeaders headers;
    //тестовый продукт
    private ProductDto testProduct;

    // токены получениые после авторизации
    private String adminAccessToken;
    private String userAccessToken;

    //Литералы выносим в константы
    //тестовые продукты
    private final String TEST_PRODUCT_TITLE = "Test product";
    private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(777);
    // тестовые данные для пользователей
    private final String ADMIN_NAME = "Test Admin";
    private final String USER_NAME = "Test User";
    private final String TEST_PASSWORD = "Test password";
    // роли в БД
    private final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    private final String USER_ROLE_NAME = "ROLE_USER";

    // контснты для соствеления http запросов
    private final String URL_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/auth";
    private final String PRODUCTS_RESOURCE_NAME = "/products";
    private final String LOGIN_ENDPOINT = "/login";
    private final String ACCESS_ENDPOINT = "/access";
    private final String ALL_ENDPOINT = "/all";

    // префикс для токена
    private final String BEARER_PREFIX = "Bearer ";

    // метод чтобы привести тестовую среду к определенному состоянию
    @BeforeEach // чтобы заупскался перед каждым тестом
    public void setUp() {
        template = new TestRestTemplate();
        headers = new HttpHeaders();

        testProduct = new ProductDto();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(TEST_PRODUCT_PRICE);

        BCryptPasswordEncoder encoder = null;
        Role roleAdmin;
        Role roleUser = null;

        // этот и следующе пунте для того чтобы у нас были данные в базе для возможности тестирования
        // получаем значение из базы тестовых поользователей -
        User admin = userRepository.findByUsername(ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(USER_NAME).orElse(null);


        // если тестовый admin не найдет - мы его создаем в БД
        if (admin == null) {
            //инициализируем encoder
            encoder = new BCryptPasswordEncoder();

            // получем роли из БД
            // у Admin обе роли- получем обе
            roleAdmin = roleRepository.findByTitle(ADMIN_ROLE_NAME);
            roleUser = roleRepository.findByTitle(USER_ROLE_NAME);

            // создаем тестового админа
            admin = User.builder()
                    .username(ADMIN_NAME)
                    .password(encoder.encode(TEST_PASSWORD))
                    .roles(Set.of(roleAdmin, roleUser))
                    .build();

            // вариант без builder
//            admin = new User();
//            admin.setUsername(ADMIN_NAME);
//            admin.setPassword(encoder.encode(TEST_PASSWORD));
//            admin.setRoles(Set.of(roleAdmin, roleUser));

            // сохраняем тестового юзера в базу
            userRepository.save(admin);
        }
        // если тестовый user не найдет - мы его создаем в БД
        if (user == null) {
            // проверяем было ли инициализирован  encoder в предыдущем if
            encoder = encoder == null ? new BCryptPasswordEncoder() : encoder;

            // создаем тестового юзера
            user = User.builder()
                    .username(USER_NAME)
                    .password(encoder.encode(TEST_PASSWORD))
                    // проверяем было ли присвоение roleUser в предыдущем if
                    .roles(Set.of(roleUser == null ?
                            roleRepository.findByTitle(USER_ROLE_NAME) : roleUser))
                    .build();

            // вариант без builder
//            user = new User();
//            user.setUsername(USER_NAME);
//            user.setPassword(encoder.encode(TEST_PASSWORD));
//            user.setRoles(Set.of(roleUser == null ?
//                    roleRepository.findByTitle(USER_ROLE_NAME) : roleUser));

            // сохраняем тестового юзера в базу
            userRepository.save(user);
        }

// создаем обекты для осуществелняи запросов к ДБ
// т.е. приводим к состоянию если бы данные пришли с извне
        admin.setPassword(TEST_PASSWORD);// звписывем сырой пароль
        admin.setRoles(null);// обнуляем роли

        user.setPassword(TEST_PASSWORD);
        user.setRoles(null);

        // формируем строку запроса
        String url = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;

        //создаем обект запроса
        HttpEntity<User> request = new HttpEntity<>(admin, headers);

        // создаем обект ответа
        ResponseEntity<TokenResponseDto> response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);// отправляем запрос
        // вызвав метод exchange. 1- url кудв отпраляем. 2- тип метода 3 - запрос. 4 - тип ожидаемого ответа

        // проверяем что ответ не пустой. если ответ не пришел - в консоль уходит сообщение
        assertNotNull(response.getBody(), "Authorization response body is null");

        // получаем AccessToken принормальном ответе и добавляем префикс
        adminAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

        // все повторяем для user
        request = new HttpEntity<>(user, headers);

        response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

        assertNotNull(response.getBody(), "Authorization response body is null");
        userAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();
    }
// в имени теста пишем -позиьтивній или негативній и подробно в назанянеии указваем что он делает
    @Test
    public void positiveGettingAllProductsWithoutAuthorization() {
        // собираем url для http запроса
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + ALL_ENDPOINT;
        //создаем обект запроса
        HttpEntity<Object> request = new HttpEntity<>(headers);//указіваем Object тк на не важно обект ответ
        // а важен сам факт наличия в ответе тела ответа
        // создаем обект ответа
        ResponseEntity<Object> response = template
                .exchange(url, HttpMethod.GET, request, Object.class);
        // проверят что статус ответа 200(ОК)
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        // проверяем наличия тела ответа
        assertTrue(response.hasBody(), "Response does not contain body");
    }
}
INSERT into category (id, name) VALUES (1, "Круасани");
INSERT into category (id, name) VALUES (2, "Напої");

INSERT INTO products (id, name, description, price, photo_url, category_id, created_at, updated_at)
VALUES
(1, 'Круасан з шоколадом', 'Смачний круасан з шоколадною начинкою', 35.00, 'https://krendel.od.ua/wp-content/uploads/2020/07/DSC_6189-800x769.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Круасан з малиновим джемом', 'Повітряний круасан з малиновим джемом', 40.00, 'https://lukas.ua/wp-content/uploads/2022/12/kruasany-lukas-11.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Круасан з сиром', 'Круасан з ніжним сиром та зеленню', 38.00, 'https://yaris-catering.com.ua/wp-content/uploads/2023/02/%D0%A4%D0%BE%D1%82%D0%BE-17.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Круасан з вишнею', 'Круасан з вишневим джемом', 42.00, 'https://biscotti.com.ua/storage/products/photo_87944.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Круасан з горіхами', 'Круасан з подрібненими горіхами та карамеллю', 43.00, 'https://gudwork.com.ua/storage/uploads/menu_images/JH6KaLmmAd9K4pCtMETqLEsSV4v45RIDyh2BGg1I.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Круасан з медом', 'Круасан з натуральним медом', 36.00, 'https://vse-recepty.com/wp-content/uploads/2024/02/recept-12727-1240x825-630x517.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Круасан з карамеллю', 'Круасан з карамельною начинкою', 44.00, 'https://ris.od.ua/wp-content/uploads/dar_9721.jpg.webp', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

(11, 'Капучіно', 'Ароматний капучіно зі свіжомеленої кави', 50.00, 'https://fata-morgana.in.ua/images/menu/soft-drinks/cappuccino/cappuccino_1.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Лате', 'Ніжний лате з молочною піною', 55.00, 'https://www.nescafe.com/ua/sites/default/files/2023-04/1066_970%202_12.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Еспресо', 'Класичний еспресо з насиченим смаком', 30.00, 'https://westcupgroup.com/wp-content/uploads/2020/06/1_4FzJWow3qJOV_O-3iKgBOw.jpeg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'Мокачіно', 'Мокачіно з шоколадним сиропом та вершками', 60.00, 'https://coffee-time.com.ua/wp-content/uploads/2020/05/kamil-s-bbNssNJlsrk-unsplash-min.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
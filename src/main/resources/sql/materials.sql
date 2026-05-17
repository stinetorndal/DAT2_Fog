INSERT INTO public.material_categories (category_name)
VALUES ('ROOF'),
       ('STRUCTURE'),
       ('SIDING'),
       ('ASSEMBLY');

INSERT INTO public.materials (name, length, description, unit, price_per_unit)
VALUES ('25x200 mm. trykimp. Brædt', 360, 'understernbrædder til for & bag ende', 'Stk', 120.00),
       ('25x200 mm. trykimp. Brædt', 540, 'understernbrædder til siderne', 'Stk', 10.00),
       ('25x125 mm. trykimp. Brædt', 360, 'oversternbrædder til forenden', 'Stk', 10.00),
       ('25x125 mm. trykimp. Brædt', 540, 'oversternbrædder til siderne', 'Stk', 10.00),
       ('38x73 mm. Lægte ubh.', 420, 'til z på bagside af dør', 'stk', 10.00),
       ('45x95 mm. Reglar ub.', 270, 'løsholter til skur gavle', 'stk', 10.00),
       ('45x95 mm. Reglar ub.', 240, 'løsholter til skur sider', 'stk', 10.00),
       ('45x195 mm. spærtræ ubh.', 600, 'Remme i sider, sadles ned i stolper', 'Stk', 10.00),
       ('45x195 mm. spærtræ ubh.', 480, 'Remme i sider (skur del)', 'Stk', 10.00),
       ('45x195 mm. spærtræ ubh.', 600, 'Spær, monteres på rem', 'Stk', 10.00),
       ('97x97 mm. trykimp. Stolpe', 300, 'Stolper nedgraves 90 cm. i jord', 'stk', 10.00),
       ('19x100 mm. trykimp. Brædt', 210, 'til beklædning af skur 1 på 2', 'stk', 10.00),
       ('19x100 mm. trykimp. Brædt', 540, 'vandbrædt på stern i sider', 'Stk', 10.00),
       ('19x100 mm. trykimp. Brædt', 360, 'vandbrædt på stern i forende', 'Stk', 10.00),
       ('Plastmo Ecolite blåtonet', 600, 'tagplader monteres på spær', 'Stk', 10.00),
       ('Plastmo Ecolite blåtonet', 360, 'tagplader monteres på spær', 'Stk', 10.00),
       ('plastmo bundskruer 200 stk.', NULL, 'Skruer til tagplader', 'pakke', 10.00),
       ('hulbånd 1x20 mm. 10 mtr.', NULL, 'Til vindkryds på spær', 'Rulle', 10.00),
       ('universal 190 mm højre', NULL, 'Til montering af spær på rem', 'Stk', 10.00),
       ('universal 190 mm venstre', NULL, 'Til montering af spær på rem', 'Stk', 10.00),
       ('4,5x60 mm. skruer 200 stk.', NULL, 'Til montering af stern & vandbrædt', 'Pakke', 10.00),
       ('4,0x50 mm. beslagskruer 250 stk.', NULL, 'Til universalbeslag + hulbånd', 'pakke', 10.00),
       ('bræddebolt 10x120 mm.', NULL, 'Til montering af rem på stolper', 'Stk', 10.00),
       ('firkantskiver 40x40x11mm', NULL, 'Til montering af rem på stolper', 'Stk', 10.00),
       ('4,5x70 mm. Skruer 400 stk.', NULL, 'til montering af yderste beklædning', 'pk.', 10.00),
       ('4,5x50 mm. Skruer 300 stk.', NULL, 'til montering af inderste beklædning', 'pk.', 10.00),
       ('stalddørsgreb 50x75', NULL, 'Til lås på dør i skur', 'Sæt', 185.00),
       ('t hængsel 390 mm', NULL, 'Til skurdør', 'Stk', 10.00),
       ('vinkelbeslag 35', NULL, 'Til montering af løsholter i skur', 'Stk', 10.00);

INSERT INTO public.material_category_link (material_id, category_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2),
       (9, 2),
       (10, 2),
       (11, 2),
       (13, 2),
       (14, 2);
INSERT INTO public.material_category_link (material_id, category_id)
VALUES (12, 3);
INSERT INTO public.material_category_link (material_id, category_id)
VALUES (15, 1),
       (16, 1);
INSERT INTO public.material_category_link (material_id, category_id)
VALUES (17, 4),
       (18, 4),
       (19, 4),
       (20, 4),
       (21, 4),
       (22, 4),
       (23, 4),
       (24, 4),
       (25, 4),
       (26, 4),
       (27, 4),
       (28, 4),
       (29, 4);

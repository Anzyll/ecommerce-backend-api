INSERT INTO categories (name) VALUES
                                  ('Clothing'),
                                  ('Gear');

INSERT INTO activities (name) VALUES
                                  ('Hiking'),
                                  ('Climbing'),
                                  ('Camping'),
                                  ('Biking'),
                                  ('Trekking');

INSERT INTO products (
    name, description, price, stock, image_url,
    category_id, activity_id
)
VALUES (
           'Women''s Hiking Jacket',
           'A lightweight, water-resistant hiking jacket designed for unpredictable weather.',
           10000.00,
           16,
           'https://eu.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dwcf015d05/images/hi-res/26685_COI.jpg',
           (SELECT category_id FROM categories WHERE name = 'Clothing'),
           (SELECT activity_id FROM activities WHERE name = 'Hiking')
       );

INSERT INTO products (
    name, price, stock, image_url,
    category_id, activity_id
)
VALUES (
           'Sling Bag 8L',
           5900.00,
           11,
           'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dw4f6f5fb1/images/hi-res/48262_SRPO.jpg',
           (SELECT category_id FROM categories WHERE name = 'Gear'),
           (SELECT activity_id FROM activities WHERE name = 'Hiking')
       );
INSERT INTO products (
    name, price, stock, image_url,
    category_id, activity_id
)
VALUES (
           'Sleeping Bag',
           7000.00,
           16,
           'https://trekkit.in/cdn/shop/files/Expedition100DownSleepingBag.png',
           (SELECT category_id FROM categories WHERE name = 'Gear'),
           (SELECT activity_id FROM activities WHERE name = 'Camping')
       );


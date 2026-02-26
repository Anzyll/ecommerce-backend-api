INSERT INTO products (
    name,
    price,
    stock,
    image_url,
    description,
    category_id,
    activity_id
)
VALUES
-- Hiking / Jackets
( 'Women''s Hiking Jacket', 10000, 16,
 'https://eu.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dwcf015d05/images/hi-res/26685_COI.jpg?sw=1400&sh=1400&sfrm=png&q=90&bgcolor=f3f4ef',
 'A lightweight, water-resistant hiking jacket designed for unpredictable weather.', 1, 1),

( 'Sling Bag 8L', 5900, 11,
 'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dw4f6f5fb1/images/hi-res/48262_SRPO.jpg?sw=1920&sh=1920&sfrm=png&q=90&bgcolor=f3f4ef',
 NULL, 2, 1),

( 'Balaclava', 3800, 7,
 'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dw869e5c8f/images/hi-res/22501_BLK.jpg?sw=1920&sh=1920&sfrm=png&q=90&bgcolor=f3f4ef',
 NULL, 1, 2),

( 'Alpine Suit', 11500, 22,
 'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dw39c1e395/images/hi-res/85745_NUVG.jpg?sw=768&sh=768&sfrm=png&q=95&bgcolor=f3f4ef',
 NULL, 1, 2),

('Mountain Sky Suspenders', 5600, 12,
 'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dwba790f79/images/hi-res/59166_FZBK.jpg?sw=1920&sh=1920&sfrm=png&q=90&bgcolor=f3f4ef',
 NULL, 2, 2),

( 'Mens Bibs', 3900, 9,
 'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dw3f64e7f1/images/hi-res/85660_CLMB.jpg?sw=768&sh=768&sfrm=png&q=95&bgcolor=f3f4ef',
 NULL, 1, 2),

( 'Grade VII Parka', 10200, 6,
 'https://www.patagonia.com/dw/image/v2/BDJB_PRD/on/demandware.static/-/Sites-patagonia-master/default/dwd5bac28e/images/hi-res/84847_SZRD.jpg?sw=768&sh=768&sfrm=png&q=95&bgcolor=f3f4ef',
 NULL, 1, 1),

-- Gear
( 'Sleeping Bag', 7000, 16,
 'https://trekkit.in/cdn/shop/files/Expedition100DownSleepingBag.png?v=1756448601&width=1800',
 NULL, 2, 3),

( '3 Person Tent', 12000, 6,
 'https://trekkit.in/cdn/shop/files/Litetrek_III_Tent-1.png?v=1757341031&width=1800',
 NULL, 2, 3),

( '35 L Backpack', 7000, 20,
 'https://trekkit.in/cdn/shop/files/Protium_35_DarkPewter_Anthracite-1.png?v=1756448553&width=1800',
 NULL, 2, 1),

( 'Camping Stove', 5400, 20,
 'https://trekkit.in/cdn/shop/files/P321988_1.png?v=1756448889&width=1800',
 NULL, 2, 3),

('Hiking Pole', 6000, 29,
 'https://trekkit.in/cdn/shop/files/11.png?v=1756448533&width=1800',
 NULL, 2, 1),

( 'Ice Axe', 3500, 10,
 'https://trekkit.in/cdn/shop/files/THE_NORTH_MACHINE_Carbon_ice_vario.png?v=1756448538&width=1800',
 NULL, 2, 2),

( 'Climbing Harness', 5000, 33,
 'https://trekkit.in/cdn/shop/files/1_e6ff2b84-b572-43e6-8a2b-2862e37de52c.png?v=1756448499&width=1080',
 NULL, 2, 2);
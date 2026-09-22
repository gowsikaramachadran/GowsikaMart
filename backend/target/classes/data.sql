-- =========================================================
-- GowsikaMart Seed Data
-- Runs automatically on startup (spring.sql.init.mode=always)
-- Safe to re-run: uses INSERT IGNORE / ON DUPLICATE where needed
-- =========================================================

-- ---------- Categories ----------
INSERT IGNORE INTO categories (id, name, slug, icon_url, description) VALUES
(1, 'Groceries', 'groceries', '/images/categories/groceries.png', 'Daily grocery essentials'),
(2, 'Fruits & Vegetables', 'fruits-vegetables', '/images/categories/fruits.png', 'Fresh fruits and vegetables'),
(3, 'Electronics', 'electronics', '/images/categories/electronics.png', 'Gadgets and electronics'),
(4, 'Mobiles & Accessories', 'mobiles-accessories', '/images/categories/mobiles.png', 'Mobile phones and accessories'),
(5, 'Home Appliances', 'home-appliances', '/images/categories/appliances.png', 'Appliances for your home'),
(6, 'Fashion', 'fashion', '/images/categories/fashion.png', 'Clothing and fashion accessories'),
(7, 'Beauty & Personal Care', 'beauty-personal-care', '/images/categories/beauty.png', 'Beauty and personal care products'),
(8, 'Toys', 'toys', '/images/categories/toys.png', 'Toys and games for kids'),
(9, 'Sports', 'sports', '/images/categories/sports.png', 'Sports and fitness gear'),
(10, 'Home & Kitchen', 'home-kitchen', '/images/categories/kitchen.png', 'Home and kitchen essentials'),
(11, 'Books', 'books', '/images/categories/books.png', 'Books across genres'),
(12, 'Household', 'household', '/images/categories/household.png', 'Household and cleaning supplies'),
(13, 'Other Products', 'other-products', '/images/categories/other.png', 'Miscellaneous products');

-- ---------- Admin user ----------
-- NOTE: Passwords must be BCrypt-hashed (Spring Security requirement), so no
-- admin account is pre-seeded here with a guessed hash. Instead:
--   1. Register a normal account from the Register page, e.g. with
--      email = admin@gowsikamart.com
--   2. Then promote it to admin by running:
--      UPDATE users SET role = 'ADMIN' WHERE email = 'admin@gowsikamart.com';
-- See README.md "Creating an Admin account" for the full steps.

-- ---------- Products ----------
-- GROCERIES (category_id = 1)
INSERT IGNORE INTO products (id, name, brand, category_id, price, discount_percent, final_price, rating, rating_count, stock_quantity, description, image_url, created_at, best_seller, new_arrival, trending, today_deal) VALUES
(1, 'Aashirvaad Sharbati Atta 5kg', 'Aashirvaad', 1, 320.00, 10, 288.00, 4.5, 1200, 150, 'Premium whole wheat atta made from Sharbati wheat, soft and fresh rotis every time.', '/images/products/atta.jpg', NOW(), true, false, false, true),
(2, 'India Gate Basmati Rice 5kg', 'India Gate', 1, 650.00, 12, 572.00, 4.6, 980, 100, 'Extra long grain aged basmati rice with rich aroma.', '/images/products/rice.jpg', NOW(), true, false, false, false),
(3, 'Tata Salt 1kg', 'Tata', 1, 28.00, 0, 28.00, 4.7, 2100, 500, 'Iodized vacuum evaporated salt for everyday cooking.', '/images/products/salt.jpg', NOW(), false, false, false, false),
(4, 'Fortune Sunflower Oil 1L', 'Fortune', 1, 165.00, 8, 151.80, 4.4, 850, 200, 'Refined sunflower oil, light and healthy for daily cooking.', '/images/products/oil.jpg', NOW(), false, false, true, false),
(5, 'Bru Instant Coffee 100g', 'Bru', 1, 210.00, 15, 178.50, 4.3, 640, 180, 'Rich and aromatic instant coffee granules.', '/images/products/coffee.jpg', NOW(), false, true, false, false),
(6, 'Aashirvaad Toor Dal 1kg', 'Aashirvaad', 1, 155.00, 5, 147.25, 4.2, 320, 220, 'High quality unpolished toor dal, rich in protein.', '/images/products/dal.jpg', NOW(), false, false, false, false),
(7, 'Amul Butter 500g', 'Amul', 1, 260.00, 0, 260.00, 4.8, 1500, 160, 'Creamy and delicious table butter, made from fresh cream.', '/images/products/butter.jpg', NOW(), true, false, false, false),

-- FRUITS & VEGETABLES (category_id = 2)
(8, 'Fresh Red Apples 1kg', 'Farm Fresh', 2, 180.00, 5, 171.00, 4.3, 410, 300, 'Crisp and juicy red apples, sourced fresh from the farm.', '/images/products/apples.jpg', NOW(), false, false, true, false),
(9, 'Fresh Bananas 1 Dozen', 'Farm Fresh', 2, 60.00, 0, 60.00, 4.2, 500, 400, 'Naturally ripened fresh bananas.', '/images/products/bananas.jpg', NOW(), true, false, false, false),
(10, 'Fresh Oranges 1kg', 'Farm Fresh', 2, 90.00, 0, 90.00, 4.1, 260, 250, 'Juicy and tangy fresh oranges packed with Vitamin C.', '/images/products/oranges.jpg', NOW(), false, false, false, false),
(11, 'Fresh Tomatoes 1kg', 'Farm Fresh', 2, 40.00, 0, 40.00, 4.0, 320, 350, 'Farm-fresh ripe tomatoes for everyday cooking.', '/images/products/tomatoes.jpg', NOW(), false, false, false, true),
(12, 'Fresh Potatoes 1kg', 'Farm Fresh', 2, 35.00, 0, 35.00, 4.1, 300, 400, 'Fresh farm potatoes, perfect for all recipes.', '/images/products/potatoes.jpg', NOW(), false, false, false, false),
(13, 'Fresh Onions 1kg', 'Farm Fresh', 2, 45.00, 0, 45.00, 4.0, 280, 380, 'Fresh quality onions sourced directly from farms.', '/images/products/onions.jpg', NOW(), false, false, false, false),

-- ELECTRONICS (category_id = 3)
(14, 'Wireless Bluetooth Speaker', 'BoomBeat', 3, 1999.00, 20, 1599.20, 4.4, 720, 90, 'Portable Bluetooth speaker with deep bass and 12-hour battery life.', '/images/products/speaker.jpg', NOW(), true, false, true, true),
(15, 'TWS Bluetooth Earbuds', 'SoundPods', 3, 1499.00, 25, 1124.25, 4.3, 950, 140, 'True wireless earbuds with noise cancellation and touch controls.', '/images/products/earbuds.jpg', NOW(), true, true, true, false),
(16, 'USB Fast Charging Cable', 'ChargeFast', 3, 249.00, 10, 224.10, 4.2, 610, 300, 'Durable braided USB-C fast charging cable, 1.5 meters.', '/images/products/cable.jpg', NOW(), false, false, false, false),
(17, 'Wireless Keyboard', 'TypeMaster', 3, 899.00, 15, 764.15, 4.1, 210, 100, 'Slim wireless keyboard with long battery backup.', '/images/products/keyboard.jpg', NOW(), false, false, false, false),
(18, 'Wireless Mouse', 'ClickPro', 3, 599.00, 10, 539.10, 4.3, 380, 220, 'Ergonomic wireless mouse with adjustable DPI.', '/images/products/mouse.jpg', NOW(), false, false, false, false),
(19, 'Smart LED TV 43-inch', 'VisionMax', 3, 24999.00, 18, 20499.18, 4.5, 340, 40, 'Full HD Smart LED TV with built-in apps and voice remote.', '/images/products/tv.jpg', NOW(), true, false, false, true),

-- MOBILES & ACCESSORIES (category_id = 4)
(20, 'Smartphone 6.5-inch 128GB', 'NovaTech', 4, 14999.00, 12, 13199.12, 4.4, 890, 60, 'Feature-packed smartphone with 128GB storage and triple camera.', '/images/products/phone.jpg', NOW(), true, true, true, false),
(21, 'Tempered Glass Screen Protector', 'ShieldGuard', 4, 199.00, 0, 199.00, 4.0, 430, 500, 'Anti-scratch tempered glass for smartphone screens.', '/images/products/screenguard.jpg', NOW(), false, false, false, false),
(22, 'Mobile Back Cover', 'CoverCraft', 4, 299.00, 20, 239.20, 4.1, 260, 350, 'Shockproof mobile back cover with slim fit design.', '/images/products/cover.jpg', NOW(), false, false, false, false),

-- HOME APPLIANCES (category_id = 5)
(23, 'Electric Kettle', 'HomeHeat', 5, 799.00, 15, 679.15, 4.3, 420, 130, 'Stainless steel electric kettle with auto shut-off, 1.5L capacity.', '/images/products/kettle.jpg', NOW(), false, false, false, false),
(24, 'Mixer Grinder', 'KitchenPro', 5, 2599.00, 10, 2339.10, 4.5, 560, 80, '750-watt mixer grinder with 3 stainless steel jars.', '/images/products/mixer.jpg', NOW(), true, false, false, false),
(25, 'LED Table Lamp', 'BrightGlow', 5, 449.00, 5, 426.55, 4.2, 190, 200, 'Adjustable LED table lamp with 3 brightness modes.', '/images/products/lamp.jpg', NOW(), false, false, false, false),
(26, 'Portable Room Heater', 'WarmAir', 5, 1299.00, 20, 1039.20, 4.1, 150, 90, 'Compact and efficient room heater for cold winters.', '/images/products/heater.jpg', NOW(), false, false, false, true),
(27, 'Digital Kitchen Weighing Scale', 'MeasureRight', 5, 399.00, 0, 399.00, 4.3, 310, 250, 'Precise digital kitchen scale with LCD display, up to 10kg.', '/images/products/scale.jpg', NOW(), false, true, false, false),

-- FASHION (category_id = 6)
(28, 'Men''s Casual Cotton Shirt', 'UrbanFit', 6, 799.00, 25, 599.25, 4.2, 340, 180, '100% cotton casual shirt, breathable and comfortable fit.', '/images/products/shirt.jpg', NOW(), false, false, true, false),
(29, 'Women''s Casual Kurti', 'EthnicVogue', 6, 649.00, 20, 519.20, 4.3, 290, 200, 'Printed casual kurti made from soft rayon fabric.', '/images/products/kurti.jpg', NOW(), false, true, false, false),
(30, 'Men''s Running Shoes', 'SprintFlex', 6, 1799.00, 30, 1259.30, 4.4, 610, 120, 'Lightweight running shoes with cushioned sole for all-day comfort.', '/images/products/shoes.jpg', NOW(), true, false, true, true),
(31, 'Women''s Handbag', 'ChicCarry', 6, 999.00, 15, 849.15, 4.1, 220, 140, 'Stylish handbag with spacious compartments and durable strap.', '/images/products/handbag.jpg', NOW(), false, false, false, false),
(32, 'Cotton Casual T-Shirt', 'ComfyWear', 6, 399.00, 10, 359.10, 4.0, 480, 350, 'Soft cotton round-neck t-shirt available in multiple colors.', '/images/products/tshirt.jpg', NOW(), false, false, false, false),

-- BEAUTY & PERSONAL CARE (category_id = 7)
(33, 'Herbal Face Wash 100ml', 'GlowNatural', 7, 199.00, 10, 179.10, 4.2, 380, 260, 'Gentle herbal face wash for daily use, suits all skin types.', '/images/products/facewash.jpg', NOW(), false, false, false, false),
(34, 'Moisturizing Body Lotion 400ml', 'SoftSkin', 7, 349.00, 15, 296.65, 4.3, 290, 220, 'Long-lasting hydration for soft and smooth skin.', '/images/products/lotion.jpg', NOW(), false, false, false, false),
(35, 'Hair Growth Shampoo 340ml', 'RootCare', 7, 299.00, 5, 284.05, 4.1, 210, 190, 'Nourishing shampoo that strengthens hair from the roots.', '/images/products/shampoo.jpg', NOW(), false, true, false, false),

-- TOYS (category_id = 8)
(36, 'Remote Control Racing Car', 'ZoomToys', 8, 899.00, 20, 719.20, 4.4, 260, 100, 'High-speed RC racing car with rechargeable battery.', '/images/products/rccar.jpg', NOW(), true, false, false, false),
(37, 'Building Blocks Set', 'BrickWorld', 8, 599.00, 10, 539.10, 4.5, 340, 150, '150-piece creative building blocks set for kids.', '/images/products/blocks.jpg', NOW(), false, false, true, false),
(38, 'Educational Learning Kit', 'BrainyKids', 8, 449.00, 5, 426.55, 4.3, 180, 130, 'Fun and interactive learning kit for early childhood development.', '/images/products/learningkit.jpg', NOW(), false, true, false, false),
(39, 'Teddy Bear Soft Toy', 'CuddleBuddy', 8, 349.00, 0, 349.00, 4.6, 420, 200, 'Super soft huggable teddy bear, 2 feet tall.', '/images/products/teddy.jpg', NOW(), true, false, false, false),
(40, 'Kids Musical Keyboard', 'MelodyPlay', 8, 999.00, 15, 849.15, 4.2, 150, 90, 'Mini musical keyboard with fun sounds and lights for kids.', '/images/products/keyboardtoy.jpg', NOW(), false, false, false, true),

-- SPORTS (category_id = 9)
(41, 'Yoga Mat 6mm', 'FlexFit', 9, 549.00, 10, 494.10, 4.4, 310, 200, 'Anti-slip yoga mat with extra cushioning for comfort.', '/images/products/yogamat.jpg', NOW(), false, false, false, false),
(42, 'Adjustable Dumbbell Set', 'PowerLift', 9, 1999.00, 12, 1759.12, 4.3, 180, 70, 'Adjustable dumbbell set for home workouts, 2 to 10kg per hand.', '/images/products/dumbbell.jpg', NOW(), false, false, false, false),

-- HOME & KITCHEN (category_id = 10)
(43, 'Non-Stick Cookware Set', 'ChefsChoice', 10, 1899.00, 20, 1519.20, 4.5, 420, 100, '3-piece non-stick cookware set, ideal for everyday cooking.', '/images/products/cookware.jpg', NOW(), true, false, false, false),
(44, 'Stainless Steel Water Bottle 1L', 'HydroFlask', 10, 399.00, 5, 379.05, 4.4, 350, 260, 'Double-wall insulated bottle that keeps drinks cold and hot.', '/images/products/bottle.jpg', NOW(), false, true, false, false),

-- BOOKS (category_id = 11)
(45, 'The Power of Habit (Paperback)', 'Penguin', 11, 399.00, 15, 339.15, 4.6, 520, 150, 'Bestselling book on how habits work and how to change them.', '/images/products/book1.jpg', NOW(), false, false, false, false),
(46, 'Rich Dad Poor Dad (Paperback)', 'Plata Publishing', 11, 349.00, 10, 314.10, 4.7, 780, 160, 'Personal finance classic on building wealth and financial literacy.', '/images/products/book2.jpg', NOW(), true, false, false, false),

-- HOUSEHOLD (category_id = 12)
(47, 'Floor Cleaner 1L', 'ShineHome', 12, 149.00, 5, 141.55, 4.1, 240, 300, 'Disinfectant floor cleaner with long-lasting fragrance.', '/images/products/floorcleaner.jpg', NOW(), false, false, false, false),
(48, 'Dishwash Liquid 750ml', 'SparkleClean', 12, 129.00, 0, 129.00, 4.2, 310, 350, 'Grease-cutting dishwash liquid, gentle on hands.', '/images/products/dishwash.jpg', NOW(), false, false, false, false),

-- OTHER PRODUCTS (category_id = 13)
(49, 'Umbrella (Windproof)', 'RainGuard', 13, 449.00, 10, 404.10, 4.0, 160, 180, 'Windproof umbrella with sturdy frame and comfortable grip.', '/images/products/umbrella.jpg', NOW(), false, false, false, false),
(50, 'Travel Backpack 35L', 'WanderPack', 13, 1299.00, 20, 1039.20, 4.3, 280, 110, 'Durable and spacious travel backpack with multiple compartments.', '/images/products/backpack.jpg', NOW(), false, true, false, false);

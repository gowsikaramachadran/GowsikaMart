# GowsikaMart — Full-Stack E-Commerce Web Application

A complete online shopping marketplace built with **HTML/CSS/JavaScript** (frontend),
**Java Spring Boot** (backend REST API), and **MySQL** (database).

---

## 📁 Project Structure

```
GowsikaMart/
├── backend/                          Spring Boot REST API (Maven project)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/gowsika/gowsikamart/
│       │   ├── GowsikaMartApplication.java
│       │   ├── controller/           AuthController, ProductController, CategoryController,
│       │   │                         CartController, OrderController, UserController, AdminController
│       │   ├── service/              Business logic layer
│       │   ├── repository/           Spring Data JPA repositories
│       │   ├── entity/               User, Category, Product, CartItem, Order, OrderItem, Address, Review
│       │   ├── dto/                  Request/response objects
│       │   ├── config/               SecurityConfig, CorsConfig
│       │   ├── exception/            Global exception handling
│       │   └── security/             JWT utilities & filter
│       └── resources/
│           ├── application.properties
│           └── data.sql              Seed data: 13 categories + 50 realistic products
│
├── frontend/                         Plain HTML/CSS/JS (no build step needed)
│   ├── index.html                    Home page
│   ├── login.html / register.html
│   ├── shop.html                     Listing + filters + sorting
│   ├── product.html                  Product details
│   ├── cart.html / checkout.html / order-confirmation.html
│   ├── account.html / account-orders.html
│   ├── admin-dashboard.html / admin-products.html / admin-orders.html / admin-users.html
│   ├── css/style.css
│   └── js/api.js, common.js
│
└── README.md
```

---

## 🛠️ Prerequisites

- **Java 17+** and **Maven** (or use your IDE's built-in Maven)
- **MySQL 8+** running locally
- Any modern browser
- (Optional) VS Code "Live Server" extension, or Python's `http.server`, to serve the frontend

---

## 🚀 Running the Backend

1. **Create the database** (or let it auto-create — see `application.properties`):
   ```sql
   CREATE DATABASE gowsikamart_db;
   ```

2. **Update DB credentials** in
   `backend/src/main/resources/application.properties` if your MySQL
   username/password isn't `root` / `root`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=root
   ```

3. **Run the app:**
   ```bash
   cd backend
   mvn spring-boot:run
   ```
   The API starts at **http://localhost:8080/api**.

   On first run, Hibernate creates all tables, then `data.sql` automatically
   seeds **13 categories** and **50 realistic products** across groceries,
   fruits & vegetables, electronics, mobiles, appliances, fashion, beauty,
   toys, sports, home & kitchen, books, and household.

### Creating an Admin account

Passwords are stored BCrypt-hashed, so no admin password is pre-seeded in
`data.sql`. To get an admin account:

1. Register normally from the **Register** page (e.g. with email
   `admin@gowsikamart.com`).
2. Promote that account to admin by running this SQL once:
   ```sql
   UPDATE users SET role = 'ADMIN' WHERE email = 'admin@gowsikamart.com';
   ```
3. Log out and log back in — you'll be redirected to the Admin Dashboard,
   and `/api/admin/**` endpoints will now accept your token.

---

## 🌐 Running the Frontend

The frontend is plain static HTML/CSS/JS — no npm install needed. Just serve
the `frontend/` folder with any static server, for example:

```bash
cd frontend
python3 -m http.server 5500
```
Then open **http://localhost:5500/index.html**.

(Or use VS Code's "Live Server" extension — right-click `index.html` → "Open with Live Server".)

> The frontend calls the backend at `http://localhost:8080/api`
> (see `frontend/js/api.js` → `API_BASE`). Change this constant if you deploy
> the backend elsewhere.

---

## 🗄️ Database Schema

| Table          | Purpose                                             |
|----------------|------------------------------------------------------|
| `users`        | Customer & admin accounts (role: CUSTOMER / ADMIN)   |
| `categories`   | Product categories                                   |
| `products`     | Product catalog (price, discount, stock, images...)  |
| `cart_items`   | Items in each user's cart                            |
| `orders`       | Placed orders with shipping & payment info           |
| `order_items`  | Line items belonging to an order                     |
| `addresses`    | Saved delivery addresses                             |
| `reviews`      | Product reviews (entity ready; UI can be extended)   |

Relationships:
```
users → cart_items → products → categories
users → orders → order_items → products
users → addresses
products → reviews ← users
```

---

## 🔌 REST API Reference

**Auth**
```
POST /api/auth/register
POST /api/auth/login
POST /api/auth/logout
```

**Products**
```
GET    /api/products
GET    /api/products/{id}
GET    /api/products/search?keyword=
GET    /api/products/category/{categoryId}
GET    /api/products/filter?categoryId=&brand=&minPrice=&maxPrice=&minRating=&inStock=&sortBy=
GET    /api/products/best-sellers
GET    /api/products/new-arrivals
GET    /api/products/trending
GET    /api/products/today-deals
```

**Categories**
```
GET /api/categories
GET /api/categories/{id}
```

**Cart** (requires `Authorization: Bearer <token>`)
```
GET    /api/cart
POST   /api/cart/items          { productId, quantity }
PUT    /api/cart/items/{id}     { quantity }
DELETE /api/cart/items/{id}
```

**Orders** (requires auth)
```
POST /api/orders     { fullName, email, phone, address, city, state, pincode, paymentMethod }
GET  /api/orders
GET  /api/orders/{id}
```

**User** (requires auth)
```
GET /api/users/profile
PUT /api/users/profile          { fullName, phone }
PUT /api/users/change-password  { currentPassword, newPassword }
```

**Admin** (requires auth + ROLE_ADMIN)
```
GET    /api/admin/dashboard
GET    /api/admin/products
POST   /api/admin/products
PUT    /api/admin/products/{id}
PUT    /api/admin/products/{id}/stock
DELETE /api/admin/products/{id}
GET    /api/admin/orders
GET    /api/admin/orders/{id}
PUT    /api/admin/orders/{id}/status
GET    /api/admin/users
PUT    /api/admin/users/{id}/status
```

---

## 🔄 Frontend ↔ Backend Flow (example: Login)

```
login.html (JS fetch)
   → POST /api/auth/login
   → AuthController → AuthService → UserRepository → MySQL
   → JSON { token, userId, fullName, email, role }
   → JS stores token in localStorage
   → redirect to index.html (or admin-dashboard.html if role = ADMIN)
```

Every authenticated request afterward sends
`Authorization: Bearer <token>`, verified by `JwtAuthFilter` on the backend.

---

## 🖼️ Product Images

Products ship with an `imageUrl` column. If you don't yet have real photos,
the frontend automatically shows a clean placeholder image instead of a
broken-image icon (see `placeholderImage()` in `js/common.js`).

**To add your own images later:**
1. Drop image files into `frontend/images/products/`.
2. Update each product's `image_url` in `data.sql` (or via the Admin →
   Manage Products screen, which has an "Image URL" field) to point at
   `/images/products/<filename>.jpg`.

---

## ✅ What's Implemented

- Full customer flow: browse → search/filter/sort → product details → cart → checkout → order confirmation → order history
- JWT-based authentication (BCrypt password hashing, stateless sessions)
- Admin dashboard: stats, product CRUD, order status management, user enable/disable
- Clean layered Spring Boot architecture (Controller → Service → Repository → Entity)
- Responsive design (mobile, tablet, desktop)
- 13 categories, 50 realistic seeded products

## 🧩 Natural Next Steps (not built yet, easy to add on this foundation)

- Product review submission UI (backend `Review` entity/repository already exist)
- Saved addresses management screen (backend `Address` entity/repository already exist)
- Real payment gateway integration (Razorpay/Stripe) in place of the mock COD/UPI/Card options
- Image upload from the Admin panel instead of pasting a URL
- Pagination for large product catalogs

---

Enjoy building on **GowsikaMart**! 🛍️

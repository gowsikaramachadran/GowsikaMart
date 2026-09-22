/* =========================================================
   GowsikaMart — Common helpers shared across all pages
   ========================================================= */

const CATEGORY_ICONS = {
  "Groceries": "🛒",
  "Fruits & Vegetables": "🍎",
  "Electronics": "🔌",
  "Mobiles & Accessories": "📱",
  "Home Appliances": "🏠",
  "Fashion": "👗",
  "Beauty & Personal Care": "💄",
  "Toys": "🧸",
  "Sports": "🏋️",
  "Home & Kitchen": "🍳",
  "Books": "📚",
  "Household": "🧹",
  "Other Products": "📦"
};

function formatCurrency(amount) {
  return "₹" + Number(amount).toLocaleString("en-IN", { maximumFractionDigits: 2 });
}

function placeholderImage(name) {
  // Uses a lightweight placeholder so the UI never shows a broken image icon.
  const label = encodeURIComponent((name || "Product").slice(0, 20));
  return `https://placehold.co/400x400/eef1f6/1a4d8f?text=${label}`;
}

function productImgSrc(product) {
  return product.imageUrl ? product.imageUrl : placeholderImage(product.name);
}

function starString(rating) {
  const full = Math.round(rating);
  return "★".repeat(full) + "☆".repeat(5 - full);
}

function renderHeader() {
  const headerEl = document.getElementById("bm-header");
  if (!headerEl) return;

  const user = getUser();
  const cartCountPromise = isLoggedIn() ? Api.getCart().then(c => c.length).catch(() => 0) : Promise.resolve(0);

  headerEl.innerHTML = `
    <div class="topbar">
      <div class="container">
        <span>Free delivery on orders above ₹499</span>
        <span>Help &amp; Support</span>
      </div>
    </div>
    <header class="main-header">
      <div class="header-inner container">
        <a href="index.html" class="logo">Gowsika<span>Mart</span></a>
        <form class="search-bar" id="global-search-form">
          <input type="text" id="global-search-input" placeholder="Search for products, brands and more" />
          <button type="submit">🔍</button>
        </form>
        <div class="header-actions">
          ${user ? `
            <a href="account.html">
              <span class="icon">👤</span>
              <span>Hi, ${user.fullName.split(" ")[0]}</span>
            </a>
            ${user.role === 'ADMIN' ? `<a href="admin-dashboard.html"><span class="icon">🛠️</span><span>Admin</span></a>` : ``}
            <a href="#" id="logout-link"><span class="icon">🚪</span><span>Logout</span></a>
          ` : `
            <a href="login.html">
              <span class="icon">👤</span>
              <span>Login / Register</span>
            </a>
          `}
          <a href="cart.html" style="position:relative;">
            <span class="icon">🛒</span>
            <span>Cart</span>
            <span class="cart-badge" id="cart-badge" style="display:none;">0</span>
          </a>
        </div>
      </div>
      <nav class="category-nav">
        <ul id="category-nav-list"></ul>
      </nav>
    </header>
  `;

  document.getElementById("global-search-form").addEventListener("submit", (e) => {
    e.preventDefault();
    const kw = document.getElementById("global-search-input").value.trim();
    if (kw) window.location.href = `shop.html?search=${encodeURIComponent(kw)}`;
  });

  const logoutLink = document.getElementById("logout-link");
  if (logoutLink) {
    logoutLink.addEventListener("click", (e) => {
      e.preventDefault();
      clearSession();
      window.location.href = "index.html";
    });
  }

  cartCountPromise.then(count => {
    const badge = document.getElementById("cart-badge");
    if (badge && count > 0) {
      badge.style.display = "flex";
      badge.textContent = count;
    }
  });

  Api.getCategories().then(categories => {
    const list = document.getElementById("category-nav-list");
    list.innerHTML = categories.map(c =>
      `<li><a href="shop.html?category=${c.id}">${CATEGORY_ICONS[c.name] || "🏷️"} ${c.name}</a></li>`
    ).join("");
  }).catch(() => {});
}

function renderFooter() {
  const footerEl = document.getElementById("bm-footer");
  if (!footerEl) return;
  footerEl.innerHTML = `
    <footer>
      <div class="container">
        <div class="footer-grid">
          <div>
            <h4>GowsikaMart</h4>
            <ul>
              <li><a href="#">About Us</a></li>
              <li><a href="#">Careers</a></li>
              <li><a href="#">Press</a></li>
            </ul>
          </div>
          <div>
            <h4>Help</h4>
            <ul>
              <li><a href="#">Payments</a></li>
              <li><a href="#">Shipping</a></li>
              <li><a href="#">Cancellation &amp; Returns</a></li>
              <li><a href="#">FAQ</a></li>
            </ul>
          </div>
          <div>
            <h4>Policy</h4>
            <ul>
              <li><a href="#">Return Policy</a></li>
              <li><a href="#">Terms of Use</a></li>
              <li><a href="#">Security</a></li>
              <li><a href="#">Privacy</a></li>
            </ul>
          </div>
          <div>
            <h4>Shop by Category</h4>
            <ul>
              <li><a href="shop.html?category=1">Groceries</a></li>
              <li><a href="shop.html?category=3">Electronics</a></li>
              <li><a href="shop.html?category=6">Fashion</a></li>
              <li><a href="shop.html?category=8">Toys</a></li>
            </ul>
          </div>
        </div>
        <div class="footer-bottom">© 2026 GowsikaMart. All rights reserved.</div>
      </div>
    </footer>
  `;
}

function productCardHtml(p) {
  const badge = p.todayDeal ? '<span class="badge">DEAL</span>' :
                p.newArrival ? '<span class="badge">NEW</span>' :
                p.bestSeller ? '<span class="badge">BESTSELLER</span>' : '';
  return `
    <div class="product-card" onclick="window.location.href='product.html?id=${p.id}'">
      ${badge}
      <div class="product-img"><img src="${productImgSrc(p)}" alt="${p.name}" onerror="this.src='${placeholderImage(p.name)}'"/></div>
      <div class="product-info">
        <div class="product-brand">${p.brand || ""}</div>
        <div class="product-name">${p.name}</div>
        <div class="product-rating">${p.rating.toFixed(1)} ★</div>
        <div class="product-price-row">
          <span class="price-final">${formatCurrency(p.finalPrice)}</span>
          ${p.discountPercent > 0 ? `<span class="price-original">${formatCurrency(p.price)}</span><span class="price-discount">${p.discountPercent}% off</span>` : ""}
        </div>
        <div class="product-actions">
          <button class="btn btn-primary btn-sm" style="flex:1" onclick="event.stopPropagation(); quickAddToCart(${p.id})">Add to Cart</button>
        </div>
      </div>
    </div>
  `;
}

function quickAddToCart(productId) {
  if (!isLoggedIn()) {
    window.location.href = "login.html?redirect=shop.html";
    return;
  }
  Api.addToCart(productId, 1)
    .then(() => {
      alert("Added to cart!");
      renderHeader();
    })
    .catch(err => alert(err.message));
}

function requireLogin() {
  if (!isLoggedIn()) {
    window.location.href = "login.html";
    return false;
  }
  return true;
}

function requireAdmin() {
  if (!isLoggedIn() || !isAdmin()) {
    window.location.href = "login.html";
    return false;
  }
  return true;
}

document.addEventListener("DOMContentLoaded", () => {
  renderHeader();
  renderFooter();
});

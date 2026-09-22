/* =========================================================
   GowsikaMart — API helper
   Centralizes all fetch() calls to the Spring Boot backend.
   ========================================================= */

const API_BASE = "http://localhost:8080/api";

function getToken() {
  return localStorage.getItem("bm_token");
}

function getUser() {
  const raw = localStorage.getItem("bm_user");
  return raw ? JSON.parse(raw) : null;
}

function saveSession(authResponse) {
  localStorage.setItem("bm_token", authResponse.token);
  localStorage.setItem("bm_user", JSON.stringify({
    id: authResponse.userId,
    fullName: authResponse.fullName,
    email: authResponse.email,
    role: authResponse.role
  }));
}

function clearSession() {
  localStorage.removeItem("bm_token");
  localStorage.removeItem("bm_user");
}

function isLoggedIn() {
  return !!getToken();
}

function isAdmin() {
  const u = getUser();
  return u && u.role === "ADMIN";
}

/**
 * Generic API request wrapper.
 * @param {string} path - e.g. "/products"
 * @param {object} options - { method, body, auth }
 */
async function apiRequest(path, options = {}) {
  const { method = "GET", body, auth = false } = options;

  const headers = { "Content-Type": "application/json" };
  if (auth) {
    const token = getToken();
    if (token) headers["Authorization"] = "Bearer " + token;
  }

  let response;
  try {
    response = await fetch(API_BASE + path, {
      method,
      headers,
      body: body ? JSON.stringify(body) : undefined
    });
  } catch (networkErr) {
    throw new Error("Unable to reach the GowsikaMart server. Please make sure the backend is running on http://localhost:8080");
  }

  let data = null;
  const text = await response.text();
  if (text) {
    try { data = JSON.parse(text); } catch (e) { data = text; }
  }

  if (!response.ok) {
    const message = (data && data.message) ? data.message : "Something went wrong. Please try again.";
    throw new Error(message);
  }

  return data;
}

const Api = {
  // ---- Auth ----
  register: (payload) => apiRequest("/auth/register", { method: "POST", body: payload }),
  login: (payload) => apiRequest("/auth/login", { method: "POST", body: payload }),

  // ---- Categories ----
  getCategories: () => apiRequest("/categories"),

  // ---- Products ----
  getAllProducts: () => apiRequest("/products"),
  getProduct: (id) => apiRequest(`/products/${id}`),
  searchProducts: (keyword) => apiRequest(`/products/search?keyword=${encodeURIComponent(keyword)}`),
  getByCategory: (categoryId) => apiRequest(`/products/category/${categoryId}`),
  filterProducts: (params) => {
    const qs = new URLSearchParams(params).toString();
    return apiRequest(`/products/filter?${qs}`);
  },
  getBestSellers: () => apiRequest("/products/best-sellers"),
  getNewArrivals: () => apiRequest("/products/new-arrivals"),
  getTrending: () => apiRequest("/products/trending"),
  getTodayDeals: () => apiRequest("/products/today-deals"),

  // ---- Cart ----
  getCart: () => apiRequest("/cart", { auth: true }),
  addToCart: (productId, quantity = 1) => apiRequest("/cart/items", { method: "POST", auth: true, body: { productId, quantity } }),
  updateCartItem: (id, quantity) => apiRequest(`/cart/items/${id}`, { method: "PUT", auth: true, body: { quantity } }),
  removeCartItem: (id) => apiRequest(`/cart/items/${id}`, { method: "DELETE", auth: true }),

  // ---- Orders ----
  placeOrder: (payload) => apiRequest("/orders", { method: "POST", auth: true, body: payload }),
  getMyOrders: () => apiRequest("/orders", { auth: true }),
  getOrder: (id) => apiRequest(`/orders/${id}`, { auth: true }),

  // ---- User ----
  getProfile: () => apiRequest("/users/profile", { auth: true }),
  updateProfile: (payload) => apiRequest("/users/profile", { method: "PUT", auth: true, body: payload }),
  changePassword: (payload) => apiRequest("/users/change-password", { method: "PUT", auth: true, body: payload }),

  // ---- Admin ----
  adminDashboard: () => apiRequest("/admin/dashboard", { auth: true }),
  adminGetProducts: () => apiRequest("/admin/products", { auth: true }),
  adminAddProduct: (payload) => apiRequest("/admin/products", { method: "POST", auth: true, body: payload }),
  adminUpdateProduct: (id, payload) => apiRequest(`/admin/products/${id}`, { method: "PUT", auth: true, body: payload }),
  adminUpdateStock: (id, quantity) => apiRequest(`/admin/products/${id}/stock`, { method: "PUT", auth: true, body: { quantity } }),
  adminDeleteProduct: (id) => apiRequest(`/admin/products/${id}`, { method: "DELETE", auth: true }),
  adminGetOrders: () => apiRequest("/admin/orders", { auth: true }),
  adminGetOrder: (id) => apiRequest(`/admin/orders/${id}`, { auth: true }),
  adminUpdateOrderStatus: (id, status) => apiRequest(`/admin/orders/${id}/status`, { method: "PUT", auth: true, body: { status } }),
  adminGetUsers: () => apiRequest("/admin/users", { auth: true }),
  adminSetUserStatus: (id, active) => apiRequest(`/admin/users/${id}/status`, { method: "PUT", auth: true, body: { active } }),
};

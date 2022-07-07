const TOKEN_KEY = "user";

export const token = {
  storeToken,
  getAccessToken,
  getRefreshToken,
  getAccessData,
  removeToken
};

function storeToken(token) {
  localStorage.setItem(TOKEN_KEY, JSON.stringify(token));
}

function removeToken() {
  localStorage.removeItem(TOKEN_KEY);
}

function getAccessToken() {
  const user = getAccessData();
  return user.token;
}

function getRefreshToken() {
  const user = getAccessData();
  return user.refreshToken;
}

function getAccessData() {
  const parsedToken = JSON.parse(localStorage.getItem(TOKEN_KEY));
  return parsedToken ?? null;
}
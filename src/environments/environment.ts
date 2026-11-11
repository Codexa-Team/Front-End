const isLocal = typeof window !== 'undefined' && (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1');

export const environment = {
  production: true,
  // Base URL dynamically adapts to local docker container or cloud Render deployment
  BASE_URL: isLocal ? 'http://localhost:8082/api/v1' : 'https://back-end-open-source.onrender.com/api/v1',

  // API endpoints
  ENDPOINT_PATH_VEHICLES: '/vehicles',
  ENDPOINT_PATH_USERS: '/users',
  ENDPOINT_PATH_BOOKINGS: '/bookings',
  ENDPOINT_PATH_REVIEWS: '/reviews',
  ENDPOINT_PATH_TELEMETRY: '/telemetry'
};

# Multi-stage build para Angular Frontend
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration=production

FROM nginx:alpine
COPY --from=build /app/dist/renticar-frontend/browser/ /usr/share/nginx/html/

# Configuración SPA inline en default.conf para soportar rutas HTML5 sin archivo externo
RUN sed -i '/index.html index.htm;/a \        try_files $uri $uri/ /index.html;' /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

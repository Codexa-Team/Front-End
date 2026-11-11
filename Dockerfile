# Multi-stage build para Angular Frontend (Docker + Node puro, sin nginx)
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration=production

FROM node:20-alpine
WORKDIR /app
RUN npm install -g http-server
COPY --from=build /app/dist/renticar-frontend/browser/ ./dist
EXPOSE 80
CMD ["http-server", "dist", "-p", "80", "--spa", "-c-1"]

FROM node:16-alpine
WORKDIR /usr
COPY package.json ./
COPY tsconfig.json ./
COPY source ./source
COPY invoice-template.odt ./
RUN npm install
RUN npm run build

# second stage
FROM bcgovimages/alpine-node-libreoffice
WORKDIR /usr
COPY package.json ./
COPY invoice-template.odt ./
RUN npm install --only=production
COPY --from=0 /usr/build ./build

EXPOSE 6060
CMD [ "npm", "run", "prod" ]

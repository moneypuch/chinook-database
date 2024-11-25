# Use the Node.js image as the base image
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the frontend project files to the working directory
COPY . .

# Install the dependencies and build the frontend application
RUN npm install
RUN npm run build

# Specify the command to run the frontend application
CMD ["npx", "serve", "-s", "build"]
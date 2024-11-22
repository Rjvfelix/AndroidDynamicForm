#!/bin/bash

# Create main project directory
mkdir -p DynamicForm

# Create Android project structure
mkdir -p DynamicForm/android/app/src/main/{java/com/example/dynamicform,res,assets}

# Create backend structure
mkdir -p DynamicForm/backend/{src/{routes,database},forms}

# Copy Android files
cp -r app/* DynamicForm/android/app/

# Copy backend files
cp package.json DynamicForm/backend/
cp src/*.js DynamicForm/backend/src/
cp -r src/routes DynamicForm/backend/src/
cp -r src/database DynamicForm/backend/src/

# Create .env file
echo "PORT=3000" > DynamicForm/backend/.env

# Create README
echo "# Dynamic Form Application

## Project Structure
- \`android/\` - Android application
- \`backend/\` - Node.js backend server

## Setup Instructions

### Backend Setup
1. cd backend
2. npm install
3. npm run dev

### Android Setup
1. Open the android folder in Android Studio
2. Sync Gradle
3. Run the application" > DynamicForm/README.md

echo "Project setup complete! Check the README.md for setup instructions."
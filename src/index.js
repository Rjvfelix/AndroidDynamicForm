import express from 'express';
import cors from 'cors';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';
import formRoutes from './routes/forms.js';
import { initDatabase } from './database/db.js';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Serve static files from the forms directory
app.use('/forms', express.static(join(__dirname, '../forms')));

// Routes
app.use('/api/forms', formRoutes);

// Initialize database
initDatabase();

// Start server
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
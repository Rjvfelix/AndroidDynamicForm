import express from 'express';
import multer from 'multer';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';
import { saveForm, getForms, getFormById } from '../database/db.js';

const router = express.Router();
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

// Configure multer for form JSON file uploads
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, join(__dirname, '../../forms'));
  },
  filename: function (req, file, cb) {
    cb(null, Date.now() + '-' + file.originalname);
  }
});

const upload = multer({ storage: storage });

// Get all forms
router.get('/', async (req, res) => {
  try {
    const forms = await getForms();
    res.json(forms);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Get form by ID
router.get('/:id', async (req, res) => {
  try {
    const form = await getFormById(req.params.id);
    if (!form) {
      return res.status(404).json({ error: 'Form not found' });
    }
    res.json(form);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Upload new form
router.post('/upload', upload.single('form'), async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({ error: 'No form file uploaded' });
    }
    
    const formData = {
      filename: req.file.filename,
      path: req.file.path,
      uploadDate: new Date().toISOString()
    };
    
    const id = await saveForm(formData);
    res.status(201).json({ id, ...formData });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

export default router;
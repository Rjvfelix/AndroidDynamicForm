import sqlite3 from 'sqlite3';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const dbPath = join(__dirname, '../../forms.db');
const db = new sqlite3.Database(dbPath);

export function initDatabase() {
  db.run(`
    CREATE TABLE IF NOT EXISTS forms (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      filename TEXT NOT NULL,
      path TEXT NOT NULL,
      uploadDate TEXT NOT NULL
    )
  `);
}

export function saveForm(formData) {
  return new Promise((resolve, reject) => {
    const { filename, path, uploadDate } = formData;
    db.run(
      'INSERT INTO forms (filename, path, uploadDate) VALUES (?, ?, ?)',
      [filename, path, uploadDate],
      function(err) {
        if (err) reject(err);
        resolve(this.lastID);
      }
    );
  });
}

export function getForms() {
  return new Promise((resolve, reject) => {
    db.all('SELECT * FROM forms ORDER BY uploadDate DESC', (err, rows) => {
      if (err) reject(err);
      resolve(rows);
    });
  });
}

export function getFormById(id) {
  return new Promise((resolve, reject) => {
    db.get('SELECT * FROM forms WHERE id = ?', [id], (err, row) => {
      if (err) reject(err);
      resolve(row);
    });
  });
}
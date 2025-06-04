require('dotenv').config(); 

const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const sequelize = require('./config/db');
const models = require('./models'); 
const fs = require('fs');
const https = require('https');

const authRoutes = require('./routes/authRoutes');
const adminRoutes = require('./routes/adminRoutes');
const salesRepRoutes = require('./routes/salesRepRoutes');
const leadRoutes = require('./routes/leadRoutes');
const errorMiddleware = require('./middlewares/errorMiddleware');

const app = express();

// Middleware
app.use(cors());
app.use(helmet());
app.use(express.json());

// Root route - to fix "Cannot GET /"
app.get('/', (req, res) => {
  res.send('Welcome to the CRM Backend API');
});

// API Routes
app.use('/api/auth', authRoutes);
app.use('/api/admin', adminRoutes);
app.use('/api/salesrep', salesRepRoutes);
app.use('/api/leads', leadRoutes);


const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
  console.log(`HTTP Server running on port ${PORT}`);
});

app.use(errorMiddleware);

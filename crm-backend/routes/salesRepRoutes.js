const express = require('express');
const router = express.Router();
const { getDashboard } = require('../controllers/salesRepController');
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');

router.use(protect);
router.use(authorizeRoles('sales_rep'));

router.get('/dashboard', getDashboard);

module.exports = router;

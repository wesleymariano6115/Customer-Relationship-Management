const express = require('express');
const router = express.Router();
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');
const reportController = require('../controllers/reportController');
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');
const adminController = require('../controllers/adminController');

router.use(protect);
router.use(authorizeRoles('admin'));

router.get('/sales-stats', reportController.getSalesStats);
router.get('/team-performance', adminController.getTeamPerformance);

module.exports = router;

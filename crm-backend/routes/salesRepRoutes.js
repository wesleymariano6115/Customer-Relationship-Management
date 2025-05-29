const express = require('express');
const router = express.Router();
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');
const salesRepController = require('../controllers/salesRepController');

router.use(protect);
router.use(authorizeRoles('sales_rep'));

router.get('/leads', salesRepController.getAssignedLeads);
router.get('/leads/:id', salesRepController.getLeadById);

router.get('/sales-progress', salesRepController.getSalesProgress);
router.get('/notifications', salesRepController.getNotifications);

module.exports = router;

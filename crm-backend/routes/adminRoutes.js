const express = require('express');
const router = express.Router();
const { getAllUsers } = require('../controllers/adminController');
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');

router.use(protect);
router.use(authorizeRoles('admin'));

router.get('/users', getAllUsers);

module.exports = router;

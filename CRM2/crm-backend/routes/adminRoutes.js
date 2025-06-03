const express = require('express');
const router = express.Router();
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');
const adminController = require('../controllers/adminController');
const { validateUserRegistration } = require('../middlewares/validateUser');


router.use(protect);
router.use(authorizeRoles('admin'));
router.post('/users', validateUserRegistration, adminController.createUser);
router.post(
  '/users',
  validateUserRegistration,
  authorizeRoles(['admin']), 
  adminController.createUser
);

// Create user
router.post('/users', adminController.createUser);

// Get all users
router.get('/users', adminController.getAllUsers);

// Get user by ID
router.get('/users/:id', adminController.getUserById);

// Update user
router.put('/users/:id', adminController.updateUser);

// Delete user
router.delete('/users/:id', adminController.deleteUser);

module.exports = router;

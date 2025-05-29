const express = require('express');
const router = express.Router();
const { protect, authorizeRoles } = require('../middlewares/authMiddleware');
const Lead = require('../models/Lead');


router.get('/', protect, authorizeRoles('admin', 'sales_rep'), async (req, res, next) => {
  try {
    const leads = await Lead.findAll();
    res.json(leads);
  } catch (err) {
    next(err);
  }
});


router.post('/', protect, authorizeRoles('admin', 'sales_rep'), async (req, res, next) => {
  try {
    const lead = await Lead.create(req.body);
    res.status(201).json(lead);
  } catch (err) {
    next(err);
  }
});

module.exports = router;

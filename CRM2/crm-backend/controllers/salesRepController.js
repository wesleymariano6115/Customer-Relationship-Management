const Lead = require('../models/Lead');
const Customer = require('../models/Customer');
const Sales = require('../models/Sales');
const Notification = require('../models/Notification');

exports.getAssignedLeads = async (req, res, next) => {
  try {
    const limit = parseInt(req.query.limit) || 10;
    const offset = parseInt(req.query.offset) || 0;

    const leads = await Lead.findAndCountAll({
      where: { assignedTo: req.user.id },
      include: [{ model: Customer, as: 'customer' }],
      limit,
      offset,
      order: [['createdAt', 'DESC']],
    });

    res.json({
      total: leads.count,
      leads: leads.rows,
    });
  } catch (error) {
    next(error);
  }
};

exports.getLeadById = async (req, res, next) => {
  try {
    const lead = await Lead.findOne({
      where: { id: req.params.id, assignedTo: req.user.id },
      include: [{ model: Customer, as: 'customer' }],
    });
    if (!lead) return res.status(404).json({ message: 'Lead not found' });
    res.json(lead);
  } catch (error) {
    next(error);
  }
};

exports.getSalesProgress = async (req, res, next) => {
  try {
    const salesAmount = await Sales.sum('amount', {
      where: { status: 'won', assignedTo: req.user.id },
    });
    const goal = req.user.salesGoal || 0;
    res.json({
      salesAmount,
      goal,
      progressPercent: goal ? (salesAmount / goal) * 100 : 0,
    });
  } catch (error) {
    next(error);
  }
};

exports.getNotifications = async (req, res, next) => {
  try {
    const notifications = await Notification.findAll({
      where: { userId: req.user.id, read: false },
      order: [['createdAt', 'DESC']],
    });
    res.json(notifications);
  } catch (error) {
    next(error);
  }
};



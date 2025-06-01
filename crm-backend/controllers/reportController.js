const { Sales, Customer, Lead, User } = require('../models');
const { Op } = require('sequelize');

exports.getSalesStats = async (req, res, next) => {
  try {
    const totalSales = await Sales.sum('amount', { where: { status: 'won' } });
    const newCustomers = await Customer.count({
      where: { createdAt: { [Op.gte]: new Date(new Date() - 30 * 24 * 60 * 60 * 1000) } }, // last 30 days
    });
    res.json({ totalSales, newCustomers });
  } catch (error) {
    next(error);
  }
};

const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');

const Sales = sequelize.define('Sales', {
  id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
  leadId: { type: DataTypes.UUID, allowNull: false },
  amount: { type: DataTypes.FLOAT, allowNull: false },
  stage: { 
    type: DataTypes.ENUM('prospecting', 'qualified', 'proposal', 'won', 'lost'), 
    defaultValue: 'prospecting' 
  },
  status: { 
    type: DataTypes.ENUM('open', 'won', 'lost'), 
    defaultValue: 'open' 
  },
  probability: { 
    type: DataTypes.INTEGER, // 0-100%
    defaultValue: 0 
  },
  expectedCloseDate: { type: DataTypes.DATE },
  closedAt: { type: DataTypes.DATE },
}, { timestamps: true });

module.exports = Sales;

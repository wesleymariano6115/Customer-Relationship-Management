const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');

const Activity = sequelize.define('Activity', {
  id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
  type: {
    type: DataTypes.ENUM('call', 'email', 'meeting', 'task'),
    allowNull: false,
  },
  description: { type: DataTypes.STRING, allowNull: false },
  dueDate: { type: DataTypes.DATE },
  completed: { type: DataTypes.BOOLEAN, defaultValue: false },
  leadId: { type: DataTypes.UUID, allowNull: true },
  salesId: { type: DataTypes.UUID, allowNull: true },
  assignedTo: { type: DataTypes.UUID, allowNull: false },
}, { timestamps: true });

module.exports = Activity;

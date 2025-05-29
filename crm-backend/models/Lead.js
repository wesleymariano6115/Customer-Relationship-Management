const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');


const Lead = sequelize.define('Lead', {
  id: { type: DataTypes.UUID, defaultValue: DataTypes.UUIDV4, primaryKey: true },
  name: { type: DataTypes.STRING, allowNull: false },
  email: { type: DataTypes.STRING },
  phone: { type: DataTypes.STRING },
  status: {
    type: DataTypes.ENUM('new', 'contacted', 'qualified', 'lost', 'won'),
    defaultValue: 'new',
  },

  
  assignedTo: { type: DataTypes.UUID, allowNull: true }, 
  source: { type: DataTypes.STRING },
  value: { type: DataTypes.FLOAT },
  notes: { type: DataTypes.TEXT },

  
  nextFollowUpDate: { type: DataTypes.DATE, allowNull: true },
  lastContactedDate: { type: DataTypes.DATE, allowNull: true },
 
}, { timestamps: true });

module.exports = Lead;

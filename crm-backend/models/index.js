const User = require('./User');
const Lead = require('./Lead');
const Sales = require('./Sales');
const Activity = require('./Activity');
const Notification = require('./Notification');
const Customer = require('./Customer');

// Associations

// User - Lead
User.hasMany(Lead, { foreignKey: 'assignedTo', as: 'leads' });
Lead.belongsTo(User, { foreignKey: 'assignedTo', as: 'assignedUser' });

// User - Activity
User.hasMany(Activity, { foreignKey: 'assignedTo', as: 'activities' });
Activity.belongsTo(User, { foreignKey: 'assignedTo', as: 'assignedUser' });

// Lead - Activity
Lead.hasMany(Activity, { foreignKey: 'leadId', as: 'activities' });
Activity.belongsTo(Lead, { foreignKey: 'leadId', as: 'lead' });

// Sales - Activity
Sales.hasMany(Activity, { foreignKey: 'salesId', as: 'activities' });
Activity.belongsTo(Sales, { foreignKey: 'salesId', as: 'sale' });

// Lead - Sales
Lead.hasMany(Sales, { foreignKey: 'leadId', as: 'sales' });
Sales.belongsTo(Lead, { foreignKey: 'leadId', as: 'lead' });

// User - Sales
User.hasMany(Sales, { foreignKey: 'assignedTo', as: 'sales' });
Sales.belongsTo(User, { foreignKey: 'assignedTo', as: 'assignedUser' });

// User - Notification
User.hasMany(Notification, { foreignKey: 'userId', as: 'notifications' });
Notification.belongsTo(User, { foreignKey: 'userId', as: 'user' });

// Lead - Customer
Lead.belongsTo(Customer, { foreignKey: 'customerId', as: 'customer' });
Customer.hasMany(Lead, { foreignKey: 'customerId', as: 'leads' });

module.exports = {
  User,
  Lead,
  Sales,
  Activity,
  Notification,
  Customer,
};

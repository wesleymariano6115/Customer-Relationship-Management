const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');
const bcrypt = require('bcryptjs');

const User = sequelize.define('User', {
  id: {
    type: DataTypes.UUID,
    defaultValue: DataTypes.UUIDV4, // Automatically generate UUID v4
    primaryKey: true,
  },
  name: {
    type: DataTypes.STRING,
    allowNull: false, // Name is required
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false, // Email is required
    unique: true,     // Email must be unique
    validate: {
      isEmail: true,  // Validate email format
    },
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false, // Password is required
  },
  role: {
    type: DataTypes.ENUM('admin', 'sales_rep', 'support_agent'),
    allowNull: false, // Role is required
    defaultValue: 'sales_rep',
  },
}, {
  timestamps: true, // Adds createdAt and updatedAt timestamps

  hooks: {
    // Hash password before creating user
    beforeCreate: async (user) => {
      if (user.password) {
        const salt = await bcrypt.genSalt(10);
        user.password = await bcrypt.hash(user.password, salt);
      }
    },
    // Hash password before updating user if password changed
    beforeUpdate: async (user) => {
      if (user.changed('password')) {
        const salt = await bcrypt.genSalt(10);
        user.password = await bcrypt.hash(user.password, salt);
      }
    },
  },
});

// Instance method to compare entered password with hashed password
User.prototype.matchPassword = async function (enteredPassword) {
  return await bcrypt.compare(enteredPassword, this.password);
};

module.exports = User;

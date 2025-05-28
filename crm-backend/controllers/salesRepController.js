// Placeholder for sales rep controller
exports.getDashboard = async (req, res) => {
  res.json({ message: `Welcome Sales Rep ${req.user.name}` });
};


const Sales = require('../models/Sales');

async function generateSalesReport() {
  const sales = await Sales.findAll();
  
  return sales.map(sale => ({
    id: sale.id,
    amount: sale.amount,
    status: sale.status,
    closedAt: sale.closedAt
  }));
}

module.exports = { generateSalesReport };

const queryDatabase = require("../database/database.js")

const getListCategory = async () => {
  const queryCategory = "SELECT * FROM category"
  return await queryDatabase(queryCategory)
}

module.exports = {
  getListCategory
}
